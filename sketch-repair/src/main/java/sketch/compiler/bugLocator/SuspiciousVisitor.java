/**
 * @author Lisa Mar 17, 2016 SuspiciousTypeLocator.java 
 */
package sketch.compiler.bugLocator;

import java.util.ArrayList;

import sketch.compiler.ast.core.FENullVisitor;
import sketch.compiler.ast.core.exprs.ExprLocalVariables;
import sketch.compiler.ast.core.exprs.ExprNew;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtVarDecl;

public class SuspiciousVisitor extends FENullVisitor {
	ArrayList<StmtAssert> asserts = new ArrayList<StmtAssert>();
	ArrayList<StmtVarDecl> varDecl = new ArrayList<StmtVarDecl>();
	public Object visitStmtAssert(StmtAssert stmt) {
		System.out.println(stmt.getMsg());
		asserts.add(stmt);
		return null;
		
	}
	
	public Object visitStmtVarDecl(StmtVarDecl stmt) {
		System.out.println(stmt.getName(0));
		varDecl.add(stmt);
		return null;
	}

	public Object visitExprNew(ExprNew expNew)  {
		System.out.println(expNew.getTypeToConstruct());
		return null;
	}
	
	public Object visitStmtAssign(StmtAssign stmt)  {
		System.out.println(stmt.getLHS()+" and "+stmt.getRHS());
		return null;
	}
	
	public Object visitExprLocalVariables(ExprLocalVariables var) {
		System.out.println(var);
		return null;
	}
	
	public ArrayList<StmtAssert> getAsserts() {
		System.out.println(asserts.size());
		return asserts;
	}
	public ArrayList<StmtVarDecl> getVars() {
		System.out.println(varDecl.size());
		return varDecl;
	}
}
