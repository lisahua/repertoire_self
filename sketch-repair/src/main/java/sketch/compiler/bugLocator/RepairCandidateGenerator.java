/**
 * @author Lisa Mar 9, 2016 RepairCandidateGenerator.java 
 */
package sketch.compiler.bugLocator;

import java.io.File;

import sketch.compiler.ast.core.Program;
import sketch.util.exceptions.SketchException;
import sketch.util.exceptions.SketchNotResolvedException;

public class RepairCandidateGenerator {

	public void generateCandidaite(Program prog, SketchException se, File file) {
		if (!(se instanceof SketchNotResolvedException)) {
			System.out.println("Repair-Sketch requires a SketchNotResolvedException to start with");
			return;
		}
		System.out.println("====================Repair starts ======================");
		
		RepairProgramUtility repairUtility = new RepairProgramUtility(prog, se.getMessage());
		repairUtility.startRepair(se.getMessage());

		System.out.println("====================Repair Ends ======================");

	}


	

}
