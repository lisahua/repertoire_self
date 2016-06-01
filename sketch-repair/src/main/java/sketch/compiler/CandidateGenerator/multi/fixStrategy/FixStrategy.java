/**
 * @author Lisa May 31, 2016 FixStrategy.java 
 */
package sketch.compiler.CandidateGenerator.multi.fixStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sketch.compiler.CandidateGenerator.multi.AtomicRunModel;
import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.CandidateGenerator.multi.SketchAtomRunner;
import sketch.compiler.CandidateGenerator.multi.candStrategy.CandidateStrategy;
import sketch.compiler.CandidateGenerator.multi.candStrategy.ConditionStrategy;
import sketch.compiler.CandidateGenerator.multi.candStrategy.DiffTypeDoubleStmtStrategy;
import sketch.compiler.CandidateGenerator.multi.candStrategy.SameTypeDoubleStmtStrategy;
import sketch.compiler.CandidateGenerator.multi.candStrategy.SingleTypeStmtStrategy;
import sketch.compiler.ast.core.Program;

public abstract class FixStrategy {
	RepairMultiController controller = null;
	Program updatedProg = null;
	List<String> types = null;
	List<String> funcs = null;
	List<CandidateStrategy> candidates = null;

	public FixStrategy(RepairMultiController controller) {
		this.controller = controller;
		types = controller.getFailureHandler().getBuggyTypeS();
		funcs = controller.getFailureHandler().getSuspFunctions();
		// candidates = new ArrayList<CandidateStrategy>(
		// Arrays.asList(new SingleTypeStmtStrategy(controller), new
		// SameTypeDoubleStmtStrategy(controller),
		// new DiffTypeDoubleStmtStrategy(controller), new
		// ConditionStrategy(controller)));
		candidates = new ArrayList<CandidateStrategy>(Arrays.asList(new SingleTypeStmtStrategy(controller)));

	}

	protected String runAtomicModel(AtomicRunModel md) {
		final SketchAtomRunner worker = new SketchAtomRunner(controller);
		worker.runEvent(md);
		updatedProg = (Program) worker.visitProgram(controller.getProgram());
		String res = controller.solveSketch(updatedProg);
		updatedProg = controller.getParsedProg();
		return res;
	}

	public abstract String generateAtomicRunModel();
}
