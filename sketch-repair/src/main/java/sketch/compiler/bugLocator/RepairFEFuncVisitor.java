/**
 * @author Lisa Mar 17, 2016 SuspiciousTypeLocator.java 
 */
package sketch.compiler.bugLocator;

import java.util.ArrayList;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprNew;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtExpr;
import sketch.compiler.ast.core.stmts.StmtVarDecl;

public class RepairFEFuncVisitor extends FEReplacer {
	ArrayList<StmtAssert> asserts = new ArrayList<StmtAssert>();
	ArrayList<StmtVarDecl> varDecl = new ArrayList<StmtVarDecl>();
	ArrayList<ExprFunCall> funCall = new ArrayList<ExprFunCall>();
	ArrayList<StmtAssign> stmtAssign = new ArrayList<StmtAssign>();
	public Object visitStmtAssert(StmtAssert stmt) {
		asserts.add(stmt);
		return super.visitStmtAssert(stmt);
	}
	//

	public Object visitStmtVarDecl(StmtVarDecl stmt) {
		varDecl.add(stmt);
		return super.visitStmtVarDecl(stmt);
	}

	public Object visitExprNew(ExprNew expNew) {
		return super.visitExprNew(expNew);
	}

	public Object visitStmtAssign(StmtAssign stmt) {
		stmtAssign.add(stmt);
		return super.visitStmtAssign(stmt);
	}

//	public Object visitFunction(Function var) {
//		
//		asserts.clear();
//		varDecl.clear();
//		return super.visitFunction(var);
//	}

	public Object visitStmtExpr(StmtExpr stmt) {
		System.out.println("===StmtExpr ==="+stmt+","+stmt.getOrigin());
		return super.visitStmtExpr(stmt);
	}
	

	
	public Object visitExprFunCall(ExprFunCall exp) {
		System.out.println("===ExprFuncCall ==="+exp+","+exp.getOrigin());
		funCall.add(exp);
		return super.visitExprFunCall(exp);
	}

	public ArrayList<StmtAssert> getAsserts() {
//		System.out.println("============StmtAssert=======" + asserts.size());
		return asserts;
	}

	public ArrayList<StmtVarDecl> getVarDecl() {
		return varDecl;
	}

	public ArrayList<ExprFunCall> getFunCall() {
		return funCall;
	}
	public ArrayList<StmtAssign> getStmtAssign() {
		return stmtAssign;
	}
	
	
}
