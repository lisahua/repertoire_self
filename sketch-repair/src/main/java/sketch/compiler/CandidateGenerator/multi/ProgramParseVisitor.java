/**
 * @author Lisa Mar 17, 2016 SuspiciousTypeLocator.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprNew;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtExpr;
import sketch.compiler.ast.core.stmts.StmtVarDecl;

public class ProgramParseVisitor extends FEReplacer {
	ArrayList<StmtAssert> asserts = new ArrayList<StmtAssert>();
	ArrayList<StmtVarDecl> varDecl = new ArrayList<StmtVarDecl>();
	// ArrayList<ExprFunCall> funCall = new ArrayList<ExprFunCall>();
	ArrayList<String> funCall = new ArrayList<String>();
	ArrayList<StmtAssign> stmtAssign = new ArrayList<StmtAssign>();

	ArrayList<Parameter> parameter = new ArrayList<Parameter>();

	public Object visitStmtAssert(StmtAssert stmt) {
		asserts.add(stmt);
		return super.visitStmtAssert(stmt);
	}

	public Object visitStmtVarDecl(StmtVarDecl stmt) {
		varDecl.add(stmt);
		return super.visitStmtVarDecl(stmt);
	}

	public Object visitExprNew(ExprNew expNew) {
		return super.visitExprNew(expNew);
	}

	public Object visitParameter(Parameter par) {
		parameter.add(par);
		return super.visitParameter(par);
	}

	public Object visitStmtAssign(StmtAssign stmt) {
		// System.out.println("=====RepairSketchReplacer ===find assign stmt " +
		// stmt+","+stmt.getRHS()+"," +stmt.getRHS().getClass());

		stmtAssign.add(stmt);
		return super.visitStmtAssign(stmt);
	}

	public Object visitStmtExpr(StmtExpr stmt) {
		return super.visitStmtExpr(stmt);
	}

	public Object visitExprFunCall(ExprFunCall exp) {

		if (!funCall.contains(exp.getName()))
			funCall.add(exp.getName());
		// System.out.println("visitor function call "+exp.getName());
		return super.visitExprFunCall(exp);
	}

	public ArrayList<StmtAssert> getAsserts() {
		return asserts;
	}

	public ArrayList<StmtVarDecl> getVarDecl() {
		return varDecl;
	}

	// public ArrayList<ExprFunCall> getFunCall() {
	// return funCall;
	// }
	public ArrayList<String> getFunCallS() {
		return funCall;
	}

	public ArrayList<StmtAssign> getStmtAssign() {
		return stmtAssign;
	}

	public ArrayList<Parameter> getParameter() {
		return parameter;
	}
}
