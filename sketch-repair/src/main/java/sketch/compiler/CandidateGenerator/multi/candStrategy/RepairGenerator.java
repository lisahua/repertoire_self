/**
 * @author Lisa Apr 30, 2016 RepairGenerator.java 
 */
package sketch.compiler.CandidateGenerator.multi.candStrategy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import sketch.compiler.CandidateGenerator.multi.AtomicRunModel;
import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.CandidateGenerator.multi.SketchAtomRunner;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Program;

public class RepairGenerator {
	RepairMultiController controller = null;

	public RepairGenerator(RepairMultiController controller) {
		this.controller = controller;
	}

	public boolean generateAtomicRunModel() {
		List<String> types = controller.getFailureHandler().getBuggyTypeS();
		List<String> funcs = controller.getFailureHandler().getSuspFunctions();
		List<CandidateStrategy> candidates = new ArrayList<CandidateStrategy>(
				Arrays.asList(new SingleTypeStmtStrategy(controller), new SameTypeDoubleStmtStrategy(controller),
						new DiffTypeDoubleStmtStrategy(controller)));
		for (CandidateStrategy cand : candidates) {
			for (int j = funcs.size() - 1; j >= 0; j--) {
				FENode origin = controller.getFuncMap(funcs.get(j)).getOrigin();
				List<AtomicRunModel> models = cand.getAtomicRunModel(origin, funcs.get(j), types);
				for (AtomicRunModel md : models) {
					// SketchAtomRunner worker = new
					// SketchAtomRunner(controller);
					// worker.runEvent(md);
					// String message = controller.solveSketch((Program)
					// worker.visitProgram(controller.getProgram()));
					System.out.println("Atomic Run Model runner " + md);
					String message = runAtomicModelWithTimeOut(md);
					if (message.equals(""))
						return true;
//					else if (message.contains("Timeout"))
//						//FIXME if one try in one function times out, jump to next function
//						break;
				}
			}
		}
		return false;
	}

	private String runAtomicModelWithTimeOut(AtomicRunModel md) {
		final SketchAtomRunner worker = new SketchAtomRunner(controller);
		worker.runEvent(md);

		final Duration timeout = Duration.ofSeconds(controller.getTimeOut());
		ExecutorService executor = Executors.newSingleThreadExecutor();
		String result = "NONE";
		@SuppressWarnings("unchecked")
		final Future<String> handler = executor.submit(new Callable() {
			@Override
			public String call() throws Exception {
				return controller.solveSketch((Program) worker.visitProgram(controller.getProgram()));
			}
		});
		try {
			result = handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			System.out.println("[Timeout]");
			e.printStackTrace();
		}
		executor.shutdownNow();
		return result;
	}
}
