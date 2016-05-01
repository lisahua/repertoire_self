/**
 * @author Lisa Apr 30, 2016 SketchAssertFilter.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.stmts.StmtWhile;

public class SketchAtomRunner extends FEReplacer {
	private AtomicRunModel model;
	private RepairMultiController controller;
	private HashSet<String> existVar = new HashSet<String>();
	private HashSet<String> definedVar = new HashSet<String>();
	private HashSet<String> allVars = new HashSet<String>();
	private int stmtCount = 0;
	private boolean hasInsert = false;

	public SketchAtomRunner(RepairMultiController controller) {
		this.controller = controller;
	}

	public void runEvent(AtomicRunModel model) {
		this.model = model;
	}

	public Object visitFunction(Function func) {
		if (!func.getName().equals(model.func))
			return super.visitFunction(func);
		allVars = getVarInStmt(model.insertStmt.toString());
		List<Parameter> params = func.getParams();
		existVar = new HashSet<String>();
		definedVar = new HashSet<String>();
		for (Parameter p : params) {
			existVar.add(p.getName());
		}
		StmtBlock block = new StmtBlock(func.getOrigin(), insertRecur(func.getBody()));
		func = func.creator().body(block).create();
		return func;
	}

	private Statement insertRecur(Statement origin) {

		if (origin instanceof StmtBlock) {
			return insertBlock((StmtBlock) origin);
		} else if (origin instanceof StmtIfThen) {
			return insertIfThen((StmtIfThen) origin);
		} else if (origin instanceof StmtWhile) {
			return insertLoop((StmtWhile) origin);
		} else if (origin instanceof StmtVarDecl) {
			definedVar.add(((StmtVarDecl) origin).getName(0));
		} else if (origin instanceof StmtAssign) {
			StmtAssign assign = (StmtAssign) origin;
			HashSet<String> vars = getVarInStmt(assign.getLHS().toString());
			for (String var : vars) {
				if (definedVar.contains(var)) {
					definedVar.remove(var);
					existVar.add(var);
				}
			}
			String type = controller.resolveFieldType(model.func, assign.getLHS().toString());
			stmtCount++;
			if (model.type.contains(type)) {
				// isBuggyTypeStmt = true;
				return origin;
			} else {
				// System.out.println("replacer tell me why not loop " + assign
				// + "," + buggyType + ","
				// + resolveLHS(existVar, definedVar));
			}
		}
		return origin;
	}

	private StmtIfThen insertIfThen(StmtIfThen stmt) {
		Statement cons = insertRecur(stmt.getCons());
		Statement alt = insertRecur(stmt.getAlt());
		return new StmtIfThen(stmt.getOrigin(), stmt.getCond(), cons, alt);
	}

	private StmtWhile insertLoop(StmtWhile loop) {
		model.location = model.location+2;
		Statement lps = insertRecur(loop.getBody());
		return new StmtWhile(loop.getOrigin(), loop.getCond(), lps);
	}

	private StmtBlock insertBlock(StmtBlock block) {
		List<Statement> allSList = new ArrayList<Statement>();
		List<Statement> list = ((StmtBlock) block).getStmts();
//		boolean resolved = resolveLHS(existVar, definedVar, allVars);
		for (int i = 0; i < list.size(); i++) {
			Statement rtn = insertRecur(list.get(i));
			allSList.add(rtn);
			if (!hasInsert && stmtCount == model.location ) {
				allSList.add(model.insertStmt);
				hasInsert = true;
			}
		}
		return new StmtBlock(allSList);
	}

	private HashSet<String> getVarInStmt(String str) {
		// System.out.println("type loop generate getvarin stmt1 " + str);
		HashSet<String> vars = new HashSet<String>();
		HashSet<String> specialChar = new HashSet<String>(
				Arrays.asList("|", "(", ")", "{", "}", "=", "+", "-", "\n", "??"));

		for (String splt : specialChar)
			str = str.replace(splt, " ");

		String[] tokens = str.split(" ");
		for (String s : tokens) {
			if (s.contains("/"))
				continue;
			else if (s.contains(".")) {
				String v = s.substring(0, s.indexOf("."));
				vars.add(v);
			} else if (s.length() > 1)
				vars.add(s);
		}
		// System.out.println("type loop generate getvarin stmt2 " +
		// vars.toString());
		// }
		return vars;
	}

	private boolean allResolved(HashSet<String> exist, HashSet<String> var) {
		if (var.size() == 0)
			return false;
		for (String s : var) {
			if (!exist.contains(s))
				return false;
		}
		return true;
	}

	// for lhs
	private boolean resolveLHS(HashSet<String> exist, HashSet<String> defined, HashSet<String> allVars) {
		if (allVars.size() == 0)
			return true;
		for (String s : allVars) {
			if (!exist.contains(s)) {
				System.out.println("resolve LHS false " + exist + "," + defined + "," + allVars);
				return false;
			}
		}
		return true;
	}

}
