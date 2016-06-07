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
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Program;

public abstract class FixStrategy {
	RepairMultiController controller = null;
	Program updatedProg = null;
	// List<String> types = null;
	// List<String> funcs = null;
	List<CandidateStrategy> candidates = null;
	Program originProg = null;
	

	public FixStrategy(RepairMultiController controller) {
		this.controller = controller;
		// types = controller.getFailureHandler().getBuggyTypeS();
		// funcs = controller.getFailureHandler().getSuspFunctions();
		candidates = new ArrayList<CandidateStrategy>(
				Arrays.asList(new SingleTypeStmtStrategy(controller), new SameTypeDoubleStmtStrategy(controller),
						new DiffTypeDoubleStmtStrategy(controller), new ConditionStrategy(controller)));
		// candidates = new ArrayList<CandidateStrategy>(Arrays.asList(new
		// SingleTypeStmtStrategy(controller)));
		originProg = controller.getProgram();
		updatedProg = controller.getProgram();

	}

	protected String runAtomicModel(AtomicRunModel md) {
		SketchAtomRunner worker = new SketchAtomRunner();
		worker.runEvent(md);
		// updatedProg = (Program) worker.visitProgram(updatedProg);
		String res = controller.solveSketch((Program) worker.visitProgram(updatedProg));
		// updatedProg = controller.getParsedProg();
		return res;
	}

	public abstract String generateAtomicRunModel();

	protected String generateFix(CandidateStrategy cand) {
		String message = "";
		// controller = new RepairMultiController(updatedProg,
		// controller.getOptions());
		List<String> types = controller.getFailureHandler().getBuggyTypeS();
		List<String> funcs = controller.getFailureHandler().getSuspFunctions();
		for (int j = funcs.size() - 1; j >= 0; j--) {
			FENode origin = controller.getFuncMap(funcs.get(j)).getOrigin();
			List<AtomicRunModel> models = cand.getAtomicRunModel(origin, funcs.get(j), types);
			for (AtomicRunModel md : models) {
				message = runAtomicModel(md);
				if (message.equals("")) {
					SketchAtomRunner worker = new SketchAtomRunner();
					worker.runEvent(md);
					// updatedProg = (Program) worker.visitProgram(updatedProg);
					originProg = (Program) worker.visitProgram(originProg);

					return message;
				}
			}
		}
		return message;
	}

	public Program getAtomicRepairProgram() {
		return originProg;
	}
}
