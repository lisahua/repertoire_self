/**
 * @author Lisa Mar 17, 2016 SuspiciousTypeLocator.java 
 */
package sketch.compiler.bugLocator;

import java.util.ArrayList;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.exprs.ExprNew;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.typs.StructDef;

public class RepairFEVisitor extends FEReplacer {
	ArrayList<StmtAssert> asserts = new ArrayList<StmtAssert>();
	ArrayList<StmtVarDecl> varDecl = new ArrayList<StmtVarDecl>();
	ArrayList<StructDef> structDef = new ArrayList<StructDef>();

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
		return super.visitStmtAssign(stmt);
	}

	public Object visitStructDef(StructDef ts) {
		structDef.add(ts);
		return super.visitStructDef(ts);
	}

	public Object visitFunction(Function var) {
		asserts.clear();
		varDecl.clear();
		return super.visitFunction(var);
	}

	// public Object visitExprLocalVariables(ExprLocalVariables var) {
	// localVar.add(var);
	// return super.visitExprLocalVariables(var);
	// }

	public ArrayList<StmtAssert> getAsserts() {
		System.out.println("============StmtAssert=======" + asserts.size());
		return asserts;
	}

	public ArrayList<StmtVarDecl> getVars() {
		System.out.println("============StmtVarDecl=======" + varDecl.size());
		return varDecl;
	}

	public ArrayList<StructDef> getStructDef() {
		System.out.println("============StructDef=======" + structDef.size());
		return structDef;
	}
	//
}
