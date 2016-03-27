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

	public boolean startRepair(Program prog, String err) {
		if (err.equals(se)) {
			se = err;
			return false;
		}
		RepairProgramController utility = new RepairProgramController(prog, options);
		if(  utility.startRepair(err)) {
			String fix = RepairStageRunner.getFix();
			if (fix ==null) return true;
			options.args[0] = fix;
			System.out.println("====Partial fix ===="+fix);
//			utility = new RepairProgramController(RepairStageRunner.getFixProg(), options);
//			return  utility.startRepair(err);
		}
		return false;
//		map = utility.getFixPerFile();
//
//		return candFiles;
	}

	public String getFixPerFile(String file) {
		return map.get(file);
	}

}
