/**
 * @author Lisa Mar 23, 2016 RepairSketchReplacer.java 
 */
package sketch.compiler.CandidateGenerator;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.stmts.StmtAssign;

public class RepairSketchReplacer extends FEReplacer {
	StmtAssign assign = null;

	public RepairSketchReplacer(StmtAssign assign) {
		this.assign = assign;
	}

	public Object visitStmtAssign(StmtAssign stmt) {
		if (stmt.getLHS().equals(assign.getLHS())) {
			stmt = assign;
		}
		return super.visitStmtAssign(stmt);
	}
}
