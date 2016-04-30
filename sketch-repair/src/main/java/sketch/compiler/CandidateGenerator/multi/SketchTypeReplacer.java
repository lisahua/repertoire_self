/**
 * @author Lisa Apr 30, 2016 SketchTypeReplacer.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.stmts.StmtWhile;

public abstract class SketchTypeReplacer extends FEReplacer {
	Statement insertStmt = null;
	String buggyType = null;
	RepairMultiController controller = null;
	String funcName = "";
	List<String> bugFunc = null;
	boolean isPrimitive = false;
	HashSet<String> allVars;
	boolean isBuggyTypeStmt = false;

	public void generateCandidate(RepairMultiController controller, String buggyType) {
		System.out.println("init replacer " + buggyType);
		this.controller = controller;
		this.buggyType = buggyType;
		bugFunc = controller.getFailureHandler().getSuspFunctions();
		System.out.println("replacer check primitive " + controller.getAllStructNames() + ";" + buggyType);
		if (!controller.getAllStructNames().contains(buggyType))
			isPrimitive = true;
	}

	public void generateCandidate(RepairMultiController controller, String buggyType, String func) {
		generateCandidate(controller, buggyType);
		bugFunc = new ArrayList<String>(Arrays.asList(func));
	}

	public Object visitFunction(Function func) {
		funcName = func.getName();
		if (!bugFunc.contains(funcName))
			return super.visitFunction(func);

		isBuggyTypeStmt = false;
		StringBuilder sb = controller.genCandidateAllS(func.getName(), buggyType);
		if (sb.length() < 2)
			return func;
		if (sb.charAt(0) == '|')
			sb = new StringBuilder(sb.substring(1));
		System.out.println("Sketch type replacer " + func.getName() + "," + buggyType + "," + sb);
		putAfterDefine(func.getOrigin(), sb);
		allVars = getVarInStmt(insertStmt.toString());
		// Statement body = func.getBody();
		List<Parameter> params = func.getParams();
		HashSet<String> existVar = new HashSet<String>();
		HashSet<String> definedVar = new HashSet<String>();
		for (Parameter p : params) {
			existVar.add(p.getName());
		}
		StmtBlock block = new StmtBlock(func.getOrigin(), insertRecur(func.getBody(), existVar, definedVar));
		func = func.creator().body(block).create();
		// System.out.println("replacer func, " + func.getBody());
		return super.visitFunction(func);
	}

	public abstract void putAfterDefine(FENode origin, StringBuilder sb);

	Statement insertRecur(Statement origin, HashSet<String> existVar, HashSet<String> definedVar) {

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

	StmtIfThen insertIfThen(StmtIfThen stmt, HashSet<String> existVar, HashSet<String> definedVar) {
		Statement cons = insertRecur(stmt.getCons(), existVar, definedVar);
		Statement alt = insertRecur(stmt.getAlt(), existVar, definedVar);
		return new StmtIfThen(stmt.getOrigin(), stmt.getCond(), cons, alt);
	}

	StmtWhile insertLoop(StmtWhile loop, HashSet<String> existVar, HashSet<String> definedVar) {
		Statement lps = insertRecur(loop.getBody(), existVar, definedVar);
		return new StmtWhile(loop.getOrigin(), loop.getCond(), lps);
	}

	StmtBlock insertBlock(StmtBlock block, HashSet<String> existVar, HashSet<String> definedVar) {
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
		if (list.size() > 3) {
			// if (!isBuggyTypeStmt) {
			allSList.add(insertStmt);
			// isBuggyTypeStmt = true;
		}
		Statement rtn = insertRecur(list.get(list.size() - 1), existVar, definedVar);
		allSList.add(rtn);
		// loopFlag = false;
		// isBuggyTypeStmt = false;

		return new StmtBlock(allSList);
	}

	HashSet<String> getVarInStmt(String str) {
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

	boolean allResolved(HashSet<String> exist, HashSet<String> var) {
		if (var.size() == 0)
			return false;
		for (String s : var) {
			if (!exist.contains(s))
				return false;
		}
		return true;
	}

	// for lhs
	boolean resolveLHS(HashSet<String> exist, HashSet<String> defined) {

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
