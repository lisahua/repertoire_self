/**
 * @author Lisa May 31, 2016 OneCycleFixStrategy.java 
 */
package sketch.compiler.CandidateGenerator.multi.fixStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import sketch.compiler.CandidateGenerator.multi.AtomicRunModel;
import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.CandidateGenerator.multi.candStrategy.SingleTypeStmtStrategy;
import sketch.compiler.assertionLocator.AssertIdentifier;
import sketch.compiler.assertionLocator.AssertReplacer;
import sketch.compiler.ast.core.Program;

public class IncrementalFixStrategy extends FixStrategy {
	Program origin = null;
	List<AtomicRunModel> repairs = new ArrayList<AtomicRunModel>();
	AssertIdentifier assertIdentifier = new AssertIdentifier();
	AssertReplacer assertRemover = new AssertReplacer(controller.getOptions());

	public IncrementalFixStrategy(RepairMultiController controller) {
		super(controller);
		origin = controller.getProgram();
	}

	@Override
	public String generateAtomicRunModel() {
		String message = "";

		// StmtAssert prevFail = null;
		// HashSet<StmtAssert> removeList = new HashSet<StmtAssert>();
		// HashSet<StmtAssert> fixedList = new HashSet<StmtAssert>();
		LinkedList<String> buggyField = assertIdentifier.getAllAssertField(origin);
		HashSet<String> remainFailField = new HashSet<String>();

		while (!buggyField.isEmpty()) {
			String field = buggyField.removeFirst();
			String msg = isolateField(field);
			if (msg.equals("")) {
				System.out.println("[no error on field " + field + "]");
			} else {
				msg = generateFix(new SingleTypeStmtStrategy(controller));
				if (!msg.equals("")) {
					remainFailField.add(field);
					System.out.println("[fail to fix field " + field + "]");
				} else {
					System.out.println("[partial fixed field " + field + "]");
					// TODO record the fix here
					updatedProg = origin;
					controller = new RepairMultiController(updatedProg, controller.getOptions(),msg);
				}
			}
		}

		// for (CandidateStrategy cand : candidates) {
		// for (int j = funcs.size() - 1; j >= 0; j--) {
		// FENode origin = controller.getFuncMap(funcs.get(j)).getOrigin();
		// List<AtomicRunModel> models = new
		// SingleTypeStmtStrategy(controller).getAtomicRunModel(origin,
		// funcs.get(j),
		// types);
		// for (AtomicRunModel md : models) {
		// message = runAtomicModel(md);
		// if (message.equals(""))
		// return "";
		// failAssert = assertIdentifier.getAssert(message,
		// updatedProg);
		// if (failAssert == null)
		// continue;
		// if (prevFail == null) {
		// prevFail = failAssert;
		// } else if
		// (failAssert.toString().trim().equals(prevFail.toString().trim()))
		// {
		// System.out.println("[Same error] " + failAssert.toString() +
		// "," + prevFail.toString());
		// continue;
		// }
		// removeList.addAll(assertIdentifier.getSimilarAsserts(failAssert));
		// message = assertRemover.removeStmtAsserts(removeList,
		// updatedProg);
		// FIXME a bit hacky, but good performance

		// if (message.equals("")) {
		// System.out.println("[Find fix]" + failAssert);
		// repairs.add(md);
		// fixedList.add(failAssert);
		// // removeList.remove(failAssert);
		// // String msg =
		// //
		// assertRemover.removeStmtAsserts(assertIdentifier.getSimilarAsserts(failAssert),
		// // updatedProg);
		// // if (msg.equals(""))
		// fixedList.addAll(assertIdentifier.getSimilarAsserts(failAssert));
		// }
		// }
		// }
		// }
		// message = assertRemover.removeStmtAssert(prevFail, updatedProg);
		// updatedProg = assertRemover.getUpdatedProg();

		return message;
	}

	// FIXME assume that the failing asserts with the same field can be fixed by
	// a single stmt
	private String isolateField(String field) {
		String msg = assertRemover.removeStmtAsserts(field, updatedProg);
		updatedProg = assertRemover.getUpdatedProg();
		// Fail-oriented repair, hard to terminate.
		// StmtAssert failAssert =
		// controller.getFailureHandler().getFailAssert();
		// String message = "";
		// if (failAssert != null) {
		// message =
		// assertRemover.removeStmtAsserts(assertIdentifier.getSimilarAsserts(failAssert),
		// updatedProg);
		controller = new RepairMultiController(updatedProg, controller.getOptions(),msg);
		// System.out.println("[results fix] " + message + "," + failAssert);
		// }
		return msg;
	}

}
