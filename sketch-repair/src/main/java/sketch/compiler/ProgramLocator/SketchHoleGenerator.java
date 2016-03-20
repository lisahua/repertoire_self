/**
 * @author Lisa Mar 20, 2016 RepairCandidateGenerator.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.HashMap;
import java.util.List;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.bugLocator.RepairProgramUtility;

public class SketchHoleGenerator {
	RepairProgramUtility utility = null;

	public SketchHoleGenerator(RepairProgramUtility repairProgramUtility) {
		utility = repairProgramUtility;
	}

	public void createCandidate(HashMap<Function, List<StmtAssign>> bugAssign) {
		// TODO based on schema
		
		for (Function func : bugAssign.keySet()) {
			for (StmtAssign assign : bugAssign.get(func)) {
				Type candType = utility.resolveFieldType(func, assign.getLHS().toString());
				if (candType != null) {
					System.out.println("Generate Hole " + func.getName()+","+assign.getLHS() + " = " + "$(" + candType.toString() + ");");
			
				}
			}
		}

	}
}
