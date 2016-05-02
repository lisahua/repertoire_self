/**
 * @author Lisa Apr 30, 2016 CandidateStrategy.java 
 */
package sketch.compiler.CandidateGenerator.multi.candStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.CandidateGenerator.multi.AtomicRunModel;
import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.exprs.ExprStar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.regens.ExprRegen;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;

public abstract class CandidateStrategy {
	 RepairMultiController controller = null;

	public CandidateStrategy(RepairMultiController controller) {
		this.controller = controller;
	}

	public List<AtomicRunModel> getAtomicRunModel(FENode origin, String func, List<String> types) {
		// List<AtomicRunModel> models = new ArrayList<AtomicRunModel>();
		HashMap<String, Statement> typeInsertMap = new HashMap<String, Statement>();
		int bound = 0;
		try {
			bound = controller.getAssignMap().get(func).size();
		} catch (Exception e) {
		}
		AtomicRunModel model = new AtomicRunModel(func, types, null, bound);
		for (String type : controller.getAllStructNames()) {
			// for (String type : types) {
			StringBuilder sb = controller.genCandidateAllS(func, type);
			if (sb.charAt(0) == '|')
				sb = new StringBuilder(sb.substring(1));
			System.out.println("candidate strategy " + func + "," + types + "," + sb);
			// Statement stmt = createInsertStmt(origin, sb,
			// !controller.getAllStructNames().contains(type));
			Statement stmt = createInsertStmt(origin, sb, false);
			typeInsertMap.put(type, stmt);
		}
		String[] primitiveType = { "int", "bit" };
		for (String type : primitiveType) {
			StringBuilder sb = controller.genCandidateAllS(func, type);
			if (sb.length() < 1)
				continue;
			if (sb.charAt(0) == '|')
				sb = new StringBuilder(sb.substring(1));
			Statement stmt = createInsertStmt(origin, sb, true);
			typeInsertMap.put(type, stmt);
		}

		return generateModel(model, types, typeInsertMap);
	}

	abstract List<AtomicRunModel> generateModel(AtomicRunModel model, List<String> types,
			HashMap<String, Statement> typeInsertMap);

	private Statement createInsertStmt(FENode origin, StringBuilder sb, boolean isPrimitive) {
		List<Statement> stmt = new ArrayList<Statement>();
		if (!isPrimitive) {
			Expression lhs = new ExprRegen(origin, "{| " + sb.toString() + "|}");
			Expression rhs = new ExprRegen(origin, "{| " + sb.toString() + "|null|}");
			stmt.add(new StmtAssign(origin, lhs, rhs));
		} else {
			Expression lhs = new ExprRegen(origin, "{| " + sb.toString() + "|}");
			Expression rhs = new ExprRegen(origin, "{| " + sb.toString() + "|}");
			List<Statement> stmts = new ArrayList<Statement>();
			stmts.add(new StmtAssign(lhs, rhs, 0));
			stmts.add(new StmtAssign(lhs, new ExprStar(origin), 1));
			stmts.add(new StmtAssign(lhs, new ExprStar(origin), 2));
			stmt.add(new StmtBlock(origin, stmts));
		}

		return new StmtBlock(origin, stmt);
	}

}
