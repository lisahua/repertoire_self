/**
 * @author Lisa Apr 27, 2016 SketchExpressionGenerator.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;
import java.util.List;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.exprs.ExprStar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.regens.ExprRegen;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;

public class SketchTypeMultiExprReplacer extends SketchTypeReplacer {

	public void putAfterDefine(FENode origin, StringBuilder sb,boolean isPrimitive) {
		// System.out.println("put after define insert stmt1 " + sb);
		if (!isPrimitive) {
			Expression lhs = new ExprRegen(origin, "{| " + sb.toString() + "|}");
			Expression rhs = new ExprRegen(origin, "{| " + sb.toString() + "|null|}");
			insertStmt.add(new StmtAssign(origin, lhs, rhs));
		} else {
			Expression lhs = new ExprRegen(origin, "{| " + sb.toString() + "|}");
			Expression rhs = new ExprRegen(origin, "{| " + sb.toString() + "|}");
			// FIXME buggy!
			List<Statement> stmts = new ArrayList<Statement>();
			stmts.add(new StmtAssign(lhs, rhs, 0));
			stmts.add(new StmtAssign(lhs, new ExprStar(origin), 1));
			stmts.add(new StmtAssign(lhs, new ExprStar(origin), 2));
			insertStmt.add(new StmtBlock(origin, stmts));
			System.out.println("put after define insert stmt " + insertStmt);
		}
	}

}