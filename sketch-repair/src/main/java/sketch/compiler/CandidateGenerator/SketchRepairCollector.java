/**
 * @author Lisa Mar 23, 2016 SketchRepairCollector.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;

public class SketchRepairCollector {
	private List<SketchRepairGenerator> generatorList = new ArrayList<SketchRepairGenerator>();
	private HashMap<String, String> fixFiles = new HashMap<String, String>();
	RepairProgramController controller = null;

	public SketchRepairCollector(RepairProgramController controller) {
		this.controller = controller;
		generatorList.add(new SketchExprGenerator(controller));
		generatorList.add(new SketchPrimitiveGenerator(controller));
	}

	public List<List<StmtAssign>> createCandidate(String func, List<StmtAssign> bugAssign) {
		List<List<StmtAssign>> candidates = new ArrayList<List<StmtAssign>>();
		candidates = new SketchExprGenerator(controller).createCandidate(func, bugAssign);
		boolean isPrimitive = false;
		if (!bugAssign.isEmpty()) {
			isPrimitive = controller.isPrimitiveType(func, bugAssign.get(0).getLHS().toString());
		}
		if (isPrimitive) {
			System.out.println("Sketch repair Collector add primitive generator");
			candidates.addAll(new SketchPrimitiveGenerator(controller).createCandidate(func, bugAssign));
		}
		return candidates;
	}

	public HashMap<String, String> getFixPerFile() {
		return fixFiles;
	}
}
