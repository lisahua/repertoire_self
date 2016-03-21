/**
 * @author Lisa Mar 9, 2016 RepairCandidateGenerator.java 
 */
package sketch.compiler.bugLocator;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.ast.core.Program;
import sketch.util.exceptions.SketchException;
import sketch.util.exceptions.SketchNotResolvedException;

public class RepairGenerator {
	HashMap<String,String> fileFixMap = null;
	
	public List<String> startRepair(Program prog, SketchException se, File file) {
		if (!(se instanceof SketchNotResolvedException)) {
			System.out.println("Repair-Sketch requires a SketchNotResolvedException to start with");
			return null;
		}
		// System.out.println("====================Repair starts
		// ======================");

		RepairProgramUtility utility = new RepairProgramUtility(prog, se.getMessage());
		List<String> candFiles = utility.startRepair(se.getMessage(), file);
		fileFixMap = utility.getFixPerFile();
		return candFiles;
		// System.out.println("====================Repair Ends
		// ======================");

	}
	public HashMap<String,String> getFixPerFile() {
		return fileFixMap;
	}
}
