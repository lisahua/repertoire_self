/**
 * @author Lisa Mar 18, 2016 FailAssertRecord.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.List;

import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public abstract class SuspiciousStmtLocator {
	protected RepairProgramController utility = null;

	public SuspiciousStmtLocator(RepairProgramController utility) {
		this.utility = utility;
	}

	public abstract List<StmtAssign> findSuspiciousStmtInMethod(List<VarDeclEntry> sField, String func);

}
