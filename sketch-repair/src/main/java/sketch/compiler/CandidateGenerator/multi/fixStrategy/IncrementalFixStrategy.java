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

	List<AtomicRunModel> repairs = new ArrayList<AtomicRunModel>();
	AssertIdentifier assertIdentifier = new AssertIdentifier();
	AssertReplacer assertRemover = new AssertReplacer(controller.getOptions());
	Program origin = null;

	public IncrementalFixStrategy(RepairMultiController controller) {
		super(controller);
		origin = controller.getProgram();
	}

	@Override
	public String generateAtomicRunModel() {
		String message = "";
		LinkedList<String> buggyField = assertIdentifier.getAllAssertField(origin);
		HashSet<String> remainFailField = new HashSet<String>();
		controller = new RepairMultiController(originProg, controller.getOptions());
		message = controller.solveSketch(originProg);
		while (!buggyField.isEmpty()) {
			if (message.equals(""))
				return "";
			updatedProg = originProg;
			controller = new RepairMultiController(updatedProg, controller.getOptions(), message);
			String field = buggyField.removeFirst();
			message = isolateField(field);
			if (message.equals("")) {
				System.out.println("[no error on field " + field + "]");
			} else {
				message = generateFix(new SingleTypeStmtStrategy(controller));
				if (!message.equals("")) {
					remainFailField.add(field);
					System.out.println("[fail to fix field " + field + "]");
				} else {
					System.out.println("[partial fixed field " + field + "]");
					// TODO record the fix here
					}
			}
			controller = new RepairMultiController(originProg, controller.getOptions());
			message = controller.solveSketch(originProg);
		}

		return message;
	}

	// FIXME assume that the failing asserts with the same field can be fixed by
	// a single stmt
	private String isolateField(String field) {
		String msg = assertRemover.removeStmtAsserts(field, updatedProg);
		updatedProg = assertRemover.getUpdatedProg();
		controller = new RepairMultiController(updatedProg, controller.getOptions(), msg);
		// System.out.println("[results fix] " + message + "," + failAssert);
		// }
		return msg;
	}

}
