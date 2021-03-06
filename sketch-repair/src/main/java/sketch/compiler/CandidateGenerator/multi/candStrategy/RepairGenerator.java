/**
 * @author Lisa Apr 30, 2016 RepairGenerator.java 
 */
package sketch.compiler.CandidateGenerator.multi.candStrategy;

import java.util.HashSet;

import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.CandidateGenerator.multi.fixStrategy.FixStrategy;
import sketch.compiler.CandidateGenerator.multi.fixStrategy.IncrementalFixStrategy;
import sketch.compiler.ast.core.Program;

public class RepairGenerator {
	RepairMultiController controller = null;
	Program updatedProg = null;
	HashSet<String> changedFunc;

	public RepairGenerator(RepairMultiController controller) {
		this.controller = controller;
		// prog = controller.getProgram();
	}

	public String generateAtomicRunModel() {
		// FixStrategy strategy = new OneCycleFixStrategy(controller);
		// String message = strategy.generateAtomicRunModel();
		// if (message.equals("")) return "";
		FixStrategy strategy = new IncrementalFixStrategy(controller);
		String message = strategy.generateAtomicRunModel();
		changedFunc = strategy.getChangedFunc();
		updatedProg = strategy.getAtomicRepairProgram();
		return message;
	}

	public HashSet<String> getChangedFunc() {
		return changedFunc;
	}
	public Program getProgram() {
		return updatedProg;
	}
	// private String runAtomicModelWithTimeOut(AtomicRunModel md) {
	// final SketchAtomRunner worker = new SketchAtomRunner(controller);
	// worker.runEvent(md);
	//
	// final Duration timeout = Duration.ofSeconds(controller.getTimeOut());
	// ExecutorService executor = Executors.newSingleThreadExecutor();
	// String result = "NONE";
	// @SuppressWarnings("unchecked")
	// final Future<String> handler = executor.submit(new Callable() {
	// @Override
	// public String call() throws Exception {
	// updatedProg = (Program) worker.visitProgram(controller.getProgram());
	// return controller.solveSketch(updatedProg);
	// }
	// });
	// try {
	// result = handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
	// } catch (SketchException se) {
	// result = se.getMessage();
	// } catch (Exception e) {
	// result = e.getMessage();
	// // FIXME hide exceptions
	// // e.printStackTrace();
	// }
	// executor.shutdownNow();
	// return result;
	// }

}
