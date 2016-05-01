/**
 * @author Lisa Apr 27, 2016 SketchExpressionGenerator.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;
import java.util.List;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.exprs.ExprConstant;
import sketch.compiler.ast.core.exprs.ExprStar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.regens.ExprRegen;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtLoop;

public class SketchTypeLoopReplacer extends SketchTypeReplacer {

	public void putAfterDefine(FENode origin, StringBuilder sb,boolean isPrimitive) {

		if (!isPrimitive) {
			Expression lhs = new ExprRegen(origin, "{|" + sb.toString() + "|}");
			Expression rhs = new ExprRegen(origin, "{|" + sb.toString() + "|null|}");
			StmtAssign assign = new StmtAssign(origin, lhs, rhs);
			Expression exp = ExprConstant.createConstant(origin, "2");
			insertStmt.add(new StmtLoop(origin, exp, assign));
		} else {
			Expression lhs = new ExprRegen(origin, "{" + sb.toString() + "|}");
			Expression rhs = new ExprRegen(origin, "{" + sb.toString() + "|}");
			List<Statement> stmts = new ArrayList<Statement>();
			stmts.add(new StmtAssign(lhs, new ExprStar(origin), 1));
			stmts.add(new StmtAssign(lhs, rhs, 0));
			stmts.add(new StmtAssign(lhs, new ExprStar(origin), 2));
			StmtBlock block = new StmtBlock(origin, stmts);
			insertStmt.add(new StmtLoop(origin, ExprConstant.createConstant(origin, "2"), block));
		}
	}

}
