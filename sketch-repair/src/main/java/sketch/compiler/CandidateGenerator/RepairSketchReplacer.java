/**
 * @author Lisa Mar 23, 2016 RepairSketchReplacer.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.HashMap;
import java.util.List;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.StmtAssign;

public class RepairSketchReplacer extends FEReplacer {
	HashMap<Expression, StmtAssign> repMap = new HashMap<Expression, StmtAssign>();

	public RepairSketchReplacer(List<StmtAssign> assign) {
		for (StmtAssign ass : assign)
			repMap.put(ass.getLHS(), ass);
	}

	public Object visitStmtAssign(StmtAssign stmt) {
		Expression lhs = stmt.getLHS();
		if (repMap.containsKey(lhs)) {
			System.out.println("=====RepairSketchReplacer ===find assign stmt " + stmt + "," + repMap.get(lhs));
			stmt = repMap.get(lhs);
		}
		return super.visitStmtAssign(stmt);
	}
}
