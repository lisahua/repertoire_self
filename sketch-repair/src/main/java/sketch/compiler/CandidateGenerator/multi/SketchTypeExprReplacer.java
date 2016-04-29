/**
 * @author Lisa Apr 27, 2016 SketchExpressionGenerator.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;
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
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.stmts.StmtWhile;
import sketch.compiler.bugLocator.RepairProgramController;

public class SketchTypeExprReplacer extends FEReplacer {
	private StmtLoop loop = null;
	private String buggyType = null;
	private RepairProgramController controller = null;
	private String funcName = "";

	public SketchTypeExprReplacer(RepairProgramController controller) {
		buggyType = controller.getBuggyTypeS();
		this.controller = controller;
	}

	public Object visitFunction(Function func) {
		funcName = func.getName();
		StmtBlock block = new StmtBlock(func.getOrigin(), putAfterDefine(func));
		func = func.creator().body(block).create();
		return func;
	}

	private Statement putAfterDefine(Function func) {
		StringBuilder sb = controller.genCandidateAllS(func.getName(), buggyType);
		System.out.println("Sketch type replacer " + func.getName() + "," + buggyType + "," + sb);
		Expression lhs = new ExprRegen(func.getOrigin(), "{|" + sb.toString() + "|}");

		StmtAssign assign = new StmtAssign(func.getOrigin(), lhs, lhs);

		loop = new StmtLoop(func.getOrigin(), new ExprStar(func.getOrigin()), assign);
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
			HashSet<String> allVars = getVarInStmt(loop.getBody().toString());
			String type = controller.resolveFieldType(funcName, assign.getLHS().toString());
			if (type.equals(buggyType) && resolveLHS(existVar, definedVar, allVars))
				return loop;
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
		for (Statement stmt : list) {
			Statement rtn = insertRecur(stmt, existVar, definedVar);
			if (rtn.equals(loop)) {
				if (loopFlag) continue;
				else {
					loopFlag = true;
					continue;
				}
			}
			else if (loopFlag==true){
				allSList.add(loop);
			}
			 allSList.add(rtn);
		}
		return new StmtBlock(allSList);
	}

	private HashSet<String> getVarInStmt(String str) {
		System.out.println("type loop generate getvarin stmt1 "+str);
		HashSet<String> vars = new HashSet<String>();
		String terms = str.replace("|", " ").replace("(", " ").replace(")", " ").replace("{", "").replace("}", "");
		String[] tokens = terms.split(" ");
		for (String s : tokens) {
			if (s.contains(".")) {
				String v = s.substring(0, s.indexOf("."));
				vars.add(v);
			}
		}
		System.out.println("type loop generate getvarin stmt2 "+vars.toString());
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
	private boolean resolveLHS(HashSet<String> exist, HashSet<String> defined, HashSet<String> var) {
		if (var.size() == 0)
			return false;
		for (String s : var) {
			if (defined.contains(s)) {
				defined.remove(s);
				exist.add(s);
				return false;
			} else if (!exist.contains(s))
				return false;
		}

		return true;
	}
}
