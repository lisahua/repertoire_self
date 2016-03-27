/**
 * @author Lisa Mar 18, 2016 FailAssertRecord.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.List;

import sketch.compiler.CandidateGenerator.RepairSketchReplacer;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public abstract class SuspiciousStmtLocator {
	protected RepairProgramController utility = null;

	public SuspiciousStmtLocator(RepairProgramController utility) {
		this.utility = utility;
	}

	public abstract List<StmtAssign> findSuspiciousStmtInMethod(List<VarDeclEntry> sField, String func);

	public boolean runSketch(List<StmtAssign> bugAssign, Program prog) {
		RepairSketchReplacer replGen = new RepairSketchReplacer((List<sketch.compiler.ast.core.stmts.StmtAssign>) bugAssign);
		 prog = (Program) replGen.visitProgram(prog);
		if (utility.solveSketch(prog)) {
			System.out.println("====SketchExprGenerator === successful solve");
			return true;
		}
		return false;
	}
}
