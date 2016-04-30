/**
 * @author Lisa Mar 21, 2016 RepairStage.java 
 */
package sketch.compiler.main.other;

import java.util.HashMap;

import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.ast.core.Program;

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
		
		RepairMultiController utility = new RepairMultiController(prog, options);
//		RepairProgramController utility = new RepairProgramController(prog, options);
		if(  utility.startRepair(err)) {
			String fix = RepairStageRunner.getFix();
			if (fix ==null) return true;
			options.args[0] = fix;
//			System.out.println("====Partial fix ===="+fix);
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
