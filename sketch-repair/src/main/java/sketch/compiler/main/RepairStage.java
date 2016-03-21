/**
 * @author Lisa Mar 21, 2016 RepairStage.java 
 */
package sketch.compiler.main;

import java.util.HashMap;
import java.util.List;

import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.TempVarGen;
import sketch.compiler.bugLocator.RepairProgramUtility;
import sketch.compiler.main.cmdline.SketchOptions;
import sketch.compiler.main.passes.ParseProgramStage;
import sketch.util.exceptions.SketchException;
import sketch.util.exceptions.SketchNotResolvedException;

public class RepairStage extends ParseProgramStage {
	String se = "";
	RepairSketchOptions options = null;
	TempVarGen varGen;

	public RepairStage(TempVarGen varGen, RepairSketchOptions options) {
		super(varGen, options);
		this.options = options;
		this.varGen = varGen;
	}

	public RepairStage(TempVarGen varGen, RepairSketchOptions options, String se) {
		this(varGen, options);
		this.se = se;
	}

	@Override
	public Program visitProgramInner(Program prog) {
		// prog = super.visitProgram(prog);
		List<String> candFiles = startRepair(prog);
		for (String f : candFiles) {
			String[] new_arg = options.args;
			new_arg[0] = f;
			options = new RepairSketchOptions(new_arg);
			try {
			prog = new ParseProgramStage(varGen, options).visitProgram(null);
			} catch (SketchException e) {
				continue;
			}
		}
		return prog;
	}

	private List<String> startRepair(Program prog) {

		RepairProgramUtility utility = new RepairProgramUtility(prog, options);
		List<String> candFiles = utility.startRepair(se);
		// fileFixMap = utility.getFixPerFile();
		return candFiles;
	}
	//
	// public HashMap<String, String> getFixPerFile() {
	// return fileFixMap;
	// }
}
