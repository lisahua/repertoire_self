/**
 * @author Lisa Apr 30, 2016 RepairGenerator.java 
 */
package sketch.compiler.CandidateGenerator.multi.candStrategy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import sketch.compiler.CandidateGenerator.multi.AtomicRunModel;
import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.CandidateGenerator.multi.SketchAtomRunner;
import sketch.compiler.assertionLocator.AssertIdentifier;
import sketch.compiler.assertionLocator.AssertIsolator;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.util.exceptions.SketchException;

public class RepairGenerator {
	RepairMultiController controller = null;
	Program updatedProg = null;

	public RepairGenerator(RepairMultiController controller) {
		this.controller = controller;
		// prog = controller.getProgram();
	}

	public String generateAtomicRunModel(String filePath) {
		List<String> types = controller.getFailureHandler().getBuggyTypeS();
		List<String> funcs = controller.getFailureHandler().getSuspFunctions();
		List<CandidateStrategy> candidates = new ArrayList<CandidateStrategy>(
				Arrays.asList(new SingleTypeStmtStrategy(controller), new SameTypeDoubleStmtStrategy(controller),
						new DiffTypeDoubleStmtStrategy(controller), new ConditionStrategy(controller)));
		String message = "";
		AssertIdentifier assertIdentifier = new AssertIdentifier();
		StmtAssert prevFailAssert = null;
		AssertIsolator assIsolator = new AssertIsolator(controller.getOptions());
		assIsolator.checkEachAssert(controller.getProgram());
		HashMap<String, StmtAssert> failAsserts = assIsolator.getFailAsserts();

		for (CandidateStrategy cand : candidates) {
			for (int j = funcs.size() - 1; j >= 0; j--) {
				FENode origin = controller.getFuncMap(funcs.get(j)).getOrigin();
				List<AtomicRunModel> models = cand.getAtomicRunModel(origin, funcs.get(j), types);
				for (AtomicRunModel md : models) {
					// message = runAtomicModelWithTimeOut(md);
					message = runAtomicModel(md);
					if (message.equals(""))
						return "";
					assertIdentifier.visitProgram(updatedProg);
					StmtAssert failAssert = assertIdentifier.getAssert(message);
//					System.out.println("[asserts] " + failAssert+","+ prevFailAssert);
					if (prevFailAssert == null) {
						prevFailAssert = failAssert;
						continue;
					} else if (failAssert ==null) continue;
					else if (prevFailAssert.toString().equals(failAssert.toString())) {
						System.out.println("[Same assert] " + failAssert);
						continue;
					}
				else {
						// is it a partial fix?
						assIsolator.visitProgram(updatedProg);
						assIsolator.checkEachAssert(updatedProg);
						HashMap<String, StmtAssert> fails = assIsolator.getFailAsserts();
						if (fails.size() >= failAsserts.size()) {
							System.out.println("[Same error] " + fails.size());
							continue;
						}
						if (failAsserts.keySet().contains(fails.keySet())) {
							System.out.println("[Partial Fix] " + fails.size());
							return "[Partial] " + message;
						}
						
					}

				}
			}
		}
		return message;
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
				updatedProg = (Program) worker.visitProgram(controller.getProgram());
				return controller.solveSketch(updatedProg);
			}
		});
		try {
			result = handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
		} catch (SketchException se) {
			result = se.getMessage();
		} catch (Exception e) {
			result = e.getMessage();
			// FIXME hide exceptions
			// e.printStackTrace();
		}
		executor.shutdownNow();
		return result;
	}

	private String runAtomicModel(AtomicRunModel md) {
		final SketchAtomRunner worker = new SketchAtomRunner(controller);
		worker.runEvent(md);
		updatedProg = (Program) worker.visitProgram(controller.getProgram());
		String res = controller.solveSketch(updatedProg);
		updatedProg = controller.getParsedProg();
		return res;
	}
}
