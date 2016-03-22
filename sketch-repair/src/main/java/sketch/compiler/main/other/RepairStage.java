/**
 * @author Lisa Mar 21, 2016 RepairStage.java 
 */
package sketch.compiler.main.other;

import java.util.HashMap;
import java.util.List;

import sketch.compiler.ast.core.Program;
import sketch.compiler.bugLocator.RepairProgramController;

public class RepairStage {
	static String se = "";
	RepairSketchOptions options = null;
	HashMap<String, String> map = null;

	public RepairStage(RepairSketchOptions options) {
		this.options = options;
	}

	public List<String> startRepair(Program prog, String err) {
		if (err.equals(se)) {
			System.out.println("=========repair error " + err + "," + se);
			se = err;
			return null;
		}
		RepairProgramController utility = new RepairProgramController(prog, options);
		List<String> candFiles = utility.startRepair(err);
		map = utility.getFixPerFile();

		return candFiles;
	}

	public String getFixPerFile(String file) {
		return map.get(file);
	}

}
