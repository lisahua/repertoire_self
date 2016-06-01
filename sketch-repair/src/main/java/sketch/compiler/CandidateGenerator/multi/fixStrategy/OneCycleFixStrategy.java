/**
 * @author Lisa May 31, 2016 OneCycleFixStrategy.java 
 */
package sketch.compiler.CandidateGenerator.multi.fixStrategy;

import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.CandidateGenerator.multi.candStrategy.CandidateStrategy;

public class OneCycleFixStrategy extends FixStrategy {
	public OneCycleFixStrategy(RepairMultiController controller) {
		super(controller);
	}

	public String generateAtomicRunModel() {
		String message = "";
		for (CandidateStrategy cand : candidates) {
			message = generateFix(cand);
			if (message.equals(""))
				return "";
			}
		return message;
	}
}
