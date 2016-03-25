/**
 * @author Lisa Mar 21, 2016 RepairStage.java 
 */
package sketch.compiler.main.other;

import java.util.HashMap;

import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.TempVarGen;
import sketch.compiler.main.RepairSketchMain;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.compiler.passes.printers.SimpleCodePrinter;

public class RepairStageRunner extends RepairSketchMain {
	static String serr = "";
	RepairSketchOptions options = null;
	HashMap<String, String> map = null;
	TempVarGen varGen = new TempVarGen();
//	RPSTATUS status = RPSTATUS.NULL;
	static int index = 0;
//	static String path = "/Users/lisahua/Documents/lisa/project/spr/git-repo/repo/sketch/doublyll/" ;

	public RepairStageRunner(RepairSketchOptions options) {
		super(options);
		this.options = options;
	}

	

	protected void repairProgram(Program prog, String e) {
		RepairStage rStage = new RepairStage(options);
		 rStage.startRepair(prog, e);
	}

	private void clean(SynthesisResult synthResult) {
		Program finalCleaned = synthResult.lowered.highLevelC;
		Program substituted;
		if (synthResult.solution != null) {
			substituted = (new SubstituteSolution(varGen, options, synthResult.solution)).visitProgram(finalCleaned);
		} else {
			substituted = finalCleaned;
		}
		Program substitutedCleaned = (new CleanupFinalCode(varGen, options, visibleRControl(finalCleaned)))
				.visitProgram(substituted);
		generateCode(substitutedCleaned);
		new SimpleCodePrinter().visitProgram(substitutedCleaned);
	}
}
