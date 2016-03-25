/**
 * @author Lisa Mar 23, 2016 SketchRepairGenerator.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.HashMap;
import java.util.List;

import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;

public abstract class SketchRepairGenerator {
	protected RepairProgramController utility = null;
	protected HashMap<String, String> fileFixMap = new HashMap<String, String>();

	public SketchRepairGenerator(RepairProgramController repairProgramUtility) {
		utility = repairProgramUtility;
	}
	public abstract List<String> runSketch(HashMap<String, List<StmtAssign>> bugAssign) ;
	
	public abstract List<List<StmtAssign>> createCandidate(HashMap<String, List<StmtAssign>> bugAssign) ;
	
	public HashMap<String, String> getFixPerFile() {
		return fileFixMap;
	}
}
