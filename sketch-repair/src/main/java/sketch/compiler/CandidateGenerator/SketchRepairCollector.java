/**
 * @author Lisa Mar 23, 2016 SketchRepairCollector.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;

public class SketchRepairCollector {
	private List<SketchRepairGenerator> generatorList = new ArrayList<SketchRepairGenerator>();
	private HashMap<String, String> fixFiles = new HashMap<String, String>();

	public SketchRepairCollector(RepairProgramController controller) {
		generatorList.add(new SketchExprGenerator(controller));
		generatorList.add(new SketchPrimitiveGenerator(controller));
	}

	public List<String> runSketch(HashMap<String, List<StmtAssign>> bugAssign) {
		List<String> candidate = new ArrayList<String>();
		for (SketchRepairGenerator gen : generatorList) {
			candidate.addAll(gen.runSketch(bugAssign));
			fixFiles.putAll(gen.getFixPerFile());
		}
		return candidate;
	}

	public HashMap<String, String> getFixPerFile() {
		return fixFiles;
	}
}
