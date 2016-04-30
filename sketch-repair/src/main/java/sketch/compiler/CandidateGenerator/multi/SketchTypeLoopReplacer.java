/**
 * @author Lisa Apr 27, 2016 SketchExpressionGenerator.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.exprs.ExprStar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.regens.ExprRegen;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.stmts.StmtLoop;
import sketch.compiler.ast.core.stmts.StmtReturn;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.stmts.StmtWhile;

public class SketchTypeLoopReplacer extends FEReplacer {
	private StmtLoop loop = null;
	private String buggyType = null;
	private RepairMultiController controller = null;
	private String funcName = "";
	private List<String> bugFunc = null;
	private boolean isPrimitive = false;
	private HashSet<String> allVars;
	private boolean isBuggyTypeStmt = false;

	public SketchTypeLoopReplacer(RepairMultiController controller, String buggyType, String func) {
		System.out.println("init replacer " + buggyType + "," + func);
		this.controller = controller;
		this.buggyType = buggyType;
		bugFunc = new ArrayList<String>(Arrays.asList(func));
		System.out.println("replacer check primitive " + controller.getAllStructNames() + ";" + buggyType);
		if (!controller.getAllStructNames().contains(buggyType))
			isPrimitive = true;

	}

	public SketchTypeLoopReplacer(RepairMultiController controller, String buggyType) {
		System.out.println("init replacer " + buggyType);
		this.controller = controller;
		this.buggyType = buggyType;
		bugFunc = controller.getFailureHandler().getSuspFunctions();
		System.out.println("replacer check primitive " + controller.getAllStructNames() + ";" + buggyType);
		if (!controller.getAllStructNames().contains(buggyType))
			isPrimitive = true;

	}

	public Object visitFunction(Function func) {
		funcName = func.getName();
		if (!bugFunc.contains(funcName))
			return super.visitFunction(func);
		StmtBlock block = new StmtBlock(func.getOrigin(), putAfterDefine(func));
		func = func.creator().body(block).create();
		System.out.println("replacer func, " + func.getBody());
		return super.visitFunction(func);
	}

	private Statement putAfterDefine(Function func) {
		isBuggyTypeStmt = false;
		StringBuilder sb = controller.genCandidateAllS(func.getName(), buggyType);
		if (sb.charAt(0) == '|')
			sb = new StringBuilder(sb.substring(1));
		// System.out.println("Sketch type replacer " + func.getName() + "," +
		// buggyType + "," + sb);

		if (!isPrimitive) {
			Expression lhs = new ExprRegen(func.getOrigin(), "{|" + sb.toString() + "|}");
			Expression rhs = new ExprRegen(func.getOrigin(), "{|" + sb.toString() + "|null|}");
			StmtAssign assign = new StmtAssign(func.getOrigin(), lhs, rhs);
			loop = new StmtLoop(func.getOrigin(), new ExprStar(func.getOrigin()), assign);
		} else {
			Expression lhs = new ExprRegen(func.getOrigin(), "{" + sb.toString() + "|}");
			Expression rhs = new ExprRegen(func.getOrigin(), "{" + sb.toString() + "|}");
			// FIXME buggy!
			List<Statement> stmts = new ArrayList<Statement>();
			stmts.add(new StmtAssign(lhs, new ExprStar(func.getOrigin()), 1));
			stmts.add(new StmtAssign(lhs, rhs, 0));
			stmts.add(new StmtAssign(lhs, new ExprStar(func.getOrigin()), 2));
			StmtBlock block = new StmtBlock(func.getOrigin(),stmts);
			loop = new StmtLoop(func.getOrigin(), new ExprStar(func.getOrigin()), block);
		}

		allVars = getVarInStmt(loop.getBody().toString());

		Statement body = func.getBody();
		List<Parameter> params = func.getParams();
		HashSet<String> existVar = new HashSet<String>();
		HashSet<String> definedVar = new HashSet<String>();
		for (Parameter p : params) {
			existVar.add(p.getName());
		}
		return insertRecur(body, existVar, definedVar);
	}

	private Statement insertRecur(Statement origin, HashSet<String> existVar, HashSet<String> definedVar) {

		if (origin instanceof StmtBlock) {
			return insertBlock((StmtBlock) origin, existVar, definedVar);
		} else if (origin instanceof StmtIfThen) {
			return insertIfThen((StmtIfThen) origin, existVar, definedVar);
		} else if (origin instanceof StmtWhile) {
			return insertLoop((StmtWhile) origin, existVar, definedVar);
		} else if (origin instanceof StmtVarDecl) {
			definedVar.add(((StmtVarDecl) origin).getName(0));
		} else if (origin instanceof StmtAssign) {
			StmtAssign assign = (StmtAssign) origin;
			String type = controller.resolveFieldType(funcName, assign.getLHS().toString());

			if (type.equals(buggyType) && resolveLHS(existVar, definedVar)) {
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

	private StmtIfThen insertIfThen(StmtIfThen stmt, HashSet<String> existVar, HashSet<String> definedVar) {
		Statement cons = insertRecur(stmt.getCons(), existVar, definedVar);
		Statement alt = insertRecur(stmt.getAlt(), existVar, definedVar);
		return new StmtIfThen(stmt.getOrigin(), stmt.getCond(), cons, alt);
	}

	private StmtWhile insertLoop(StmtWhile loop, HashSet<String> existVar, HashSet<String> definedVar) {
		Statement lps = insertRecur(loop.getBody(), existVar, definedVar);
		return new StmtWhile(loop.getOrigin(), loop.getCond(), lps);
	}

	private StmtBlock insertBlock(StmtBlock block, HashSet<String> existVar, HashSet<String> definedVar) {
		List<Statement> allSList = new ArrayList<Statement>();
		List<Statement> list = ((StmtBlock) block).getStmts();
		boolean loopFlag = false;

		// Approach 1: correct but not efficient
		// for (Statement stmt : list) {
		// Statement rtn = insertRecur(stmt, existVar, definedVar);
		//
		// if (rtn.equals(loop)) {
		// if (loopFlag)
		// continue;
		// else {
		// loopFlag = true;
		// continue;
		// }
		// } else if (loopFlag == true) {
		// System.out.println("replacer add loop " + loop);
		// allSList.add(loop);
		// loopFlag = false;
		// }
		// allSList.add(rtn);
		// }
		// // last stmt corner case
		// if (loopFlag)
		// allSList.add(loop);
		// Approach 2:
		for (int i = 0; i < list.size() - 1; i++) {
			Statement rtn = insertRecur(list.get(i), existVar, definedVar);
			allSList.add(rtn);
		}
		if (!isBuggyTypeStmt) {
			allSList.add(loop);
			isBuggyTypeStmt = true;
		}
		Statement rtn = insertRecur(list.get(list.size() - 1), existVar, definedVar);
		allSList.add(rtn);
		// loopFlag = false;
		// isBuggyTypeStmt = false;

		return new StmtBlock(allSList);
	}

	private HashSet<String> getVarInStmt(String str) {
		// System.out.println("type loop generate getvarin stmt1 " + str);
		HashSet<String> vars = new HashSet<String>();
		String terms = str.replace("|", " ").replace("(", " ").replace(")", " ").replace("{", "").replace("}", "");
		String[] tokens = terms.split(" ");
		for (String s : tokens) {
			if (s.contains(".")) {
				String v = s.substring(0, s.indexOf("."));
				vars.add(v);
			} else
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
	private boolean resolveLHS(HashSet<String> exist, HashSet<String> defined) {

		// FIXME
		// if (allVars.size() == 0)
		// return false;
		// for (String s : allVars) {
		// if (defined.contains(s)) {
		// defined.remove(s);
		// exist.add(s);
		// return false;
		// } else if (!exist.contains(s))
		// return false;
		// }

		return true;
	}
}
