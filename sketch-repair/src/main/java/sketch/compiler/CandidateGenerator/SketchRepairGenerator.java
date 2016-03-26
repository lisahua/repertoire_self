/**
 * @author Lisa Mar 23, 2016 SketchRepairGenerator.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;

public abstract class SketchRepairGenerator {
	protected RepairProgramController utility = null;
	protected HashMap<String, String> fileFixMap = new HashMap<String, String>();

	public SketchRepairGenerator(RepairProgramController repairProgramUtility) {
		utility = repairProgramUtility;
	}
//	public abstract List<String> runSketch(String func, List<StmtAssign> bugAssign) ;
	
	/**
	 * Use list instead of set because we want to consider stmt order in the future.
	 * @param func
	 * @param bugAssign
	 * @return
	 */
	public abstract List<List<StmtAssign>> createCandidate(String func, List<StmtAssign> bugAssign) ;
	
	public HashMap<String, String> getFixPerFile() {
		return fileFixMap;
	}
}
