/**
 * @author Lisa May 31, 2016 OneCycleFixStrategy.java 
 */
package sketch.compiler.CandidateGenerator.multi.fixStrategy;

import java.util.List;

import sketch.compiler.CandidateGenerator.multi.AtomicRunModel;
import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.CandidateGenerator.multi.candStrategy.CandidateStrategy;
import sketch.compiler.ast.core.FENode;

public class OneCycleFixStrategy extends FixStrategy {
	public OneCycleFixStrategy(RepairMultiController controller) {
		super(controller);
	}

	public String generateAtomicRunModel() {
		String message = "";
		for (CandidateStrategy cand : candidates) {
			for (int j = funcs.size() - 1; j >= 0; j--) {
				FENode origin = controller.getFuncMap(funcs.get(j)).getOrigin();
				List<AtomicRunModel> models = cand.getAtomicRunModel(origin, funcs.get(j), types);
				for (AtomicRunModel md : models) {
					// message = runAtomicModelWithTimeOut(md);
					message = runAtomicModel(md);
					if (message.equals(""))
						return "";
				}
			}
		}
		// message = incrementallyFix(message);
		return message;
	}
}
