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
	RepairProgramController controller = null;

	public SketchRepairCollector(RepairProgramController controller) {
		this.controller = controller;
		generatorList.add(new SketchExprGenerator(controller));
		generatorList.add(new SketchPrimitiveGenerator(controller));
	}

	public List<StmtAssign> createCandidate(String func, List<StmtAssign> bugAssign) {
		List<StmtAssign> candidates = new ArrayList<StmtAssign>();

		boolean isPrimitive = false;

		for (StmtAssign ass : bugAssign) {
			// FIXME hacky!
			if (ass.toString().trim().startsWith("__s"))
				continue;
			isPrimitive = controller.isPrimitiveType(func, ass.getLHS().toString());
//			System.out.println("Sketch repair Collector Is it primitive?" + isPrimitive);
			if (isPrimitive) {
				candidates.addAll(new SketchPrimitiveGenerator(controller).createCandidate(func, ass));
			} else {
				candidates.addAll(new SketchExprGenerator(controller).createCandidate(func, bugAssign));
			}
		}
		
		return candidates;
	}

	public HashMap<String, String> getFixPerFile() {
		return fixFiles;
	}
}
