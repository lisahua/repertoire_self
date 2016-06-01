/**
 * @author Lisa May 31, 2016 OneCycleFixStrategy.java 
 */
package sketch.compiler.CandidateGenerator.multi.fixStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.CandidateGenerator.multi.AtomicRunModel;
import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.CandidateGenerator.multi.candStrategy.CandidateStrategy;
import sketch.compiler.assertionLocator.AssertIdentifier;
import sketch.compiler.assertionLocator.AssertReplacer;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssert;

public class IncrementalFixStrategy extends FixStrategy {
	Program origin = null;
	List<AtomicRunModel> repairs = new ArrayList<AtomicRunModel>();

	public IncrementalFixStrategy(RepairMultiController controller) {
		super(controller);
		origin = controller.getProgram();
	}

	@Override
	public String generateAtomicRunModel() {
		String message = "";
		AssertIdentifier assertIdentifier = new AssertIdentifier();
		AssertReplacer assertRemover = new AssertReplacer(controller.getOptions());
		StmtAssert prevFail = null;
		StmtAssert failAssert = null;
		HashSet<StmtAssert> removeList = new HashSet<StmtAssert>();
		HashSet<StmtAssert> fixedList = new HashSet<StmtAssert>();
		for (CandidateStrategy cand : candidates) {
			for (int j = funcs.size() - 1; j >= 0; j--) {
				FENode origin = controller.getFuncMap(funcs.get(j)).getOrigin();
				List<AtomicRunModel> models = cand.getAtomicRunModel(origin, funcs.get(j), types);
				for (AtomicRunModel md : models) {
					message = runAtomicModel(md);
					if (message.equals(""))
						return "";
					failAssert = assertIdentifier.getAssert(message, updatedProg);
					if (failAssert==null) continue;
					if (prevFail == null) {
						prevFail = failAssert;
					} else if (failAssert.toString().trim().equals(prevFail.toString().trim())) {
						System.out.println("[Same error] " + failAssert.toString() + "," + prevFail.toString());
						continue;
					}
					removeList.add(failAssert);
					// message = assertRemover.removeStmtAsserts(removeList,
					// updatedProg);
					//FIXME a bit hacky, but good performance
					message = assertRemover.removeStmtAsserts(assertIdentifier.getSimilarAsserts(failAssert),
							updatedProg);
					if (message.equals("")) {
						repairs.add(md);
						fixedList.add(failAssert);
						// removeList.remove(failAssert);
						// String msg =
						// assertRemover.removeStmtAsserts(assertIdentifier.getSimilarAsserts(failAssert),
						// updatedProg);
						// if (msg.equals(""))
						fixedList.addAll(assertIdentifier.getSimilarAsserts(failAssert));
					}
				}
			}
		}
		// message = assertRemover.removeStmtAssert(prevFail, updatedProg);
		// updatedProg = assertRemover.getUpdatedProg();

		return message;
	}

	public List<AtomicRunModel> verifyRepairs() {

		return repairs;
	}

}
