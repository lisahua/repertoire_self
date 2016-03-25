/**
 * @author Lisa Mar 21, 2016 RepairStage.java 
 */
package sketch.compiler.main.other;

import java.util.HashMap;

import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.TempVarGen;
import sketch.compiler.main.RepairSketchMain;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.ParseProgramStage;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.compiler.main.seq.SequentialSketchMain.SynthesisResult;
import sketch.compiler.passes.printers.SimpleCodePrinter;
import sketch.util.exceptions.SketchException;

public class RepairStageRunner extends RepairSketchMain {
	static String serr = "";
	RepairSketchOptions options = null;
	HashMap<String, String> map = null;
	TempVarGen varGen = new TempVarGen();
	// RPSTATUS status = RPSTATUS.NULL;
	static int index = 0;
	// static String path =
	// "/Users/lisahua/Documents/lisa/project/spr/git-repo/repo/sketch/doublyll/"
	// ;

	public RepairStageRunner(RepairSketchOptions options) {
		super(options);
		this.options = options;
	}

	protected void repairProgram(Program prog, String e) {
		RepairStage rStage = new RepairStage(options);
		rStage.startRepair(prog, e);
	}

	public boolean solveSketch(String sketchF) {
		try {
			System.out.println("===repair stage runner solve sketch =====");
//			new SimpleCodePrinter().visitProgram(origin);
			options.args[0] = sketchF;
			Program prog = new ParseProgramStage(varGen, options).visitProgram(null);
			prog = this.preprocAndSemanticCheck(prog);
			SynthesisResult synthResult = this.partialEvalAndSolve(prog);
			prog = synthResult.lowered.result;
			Program finalCleaned = synthResult.lowered.highLevelC;
			Program substituted;
			if (synthResult.solution != null) {
				substituted = (new SubstituteSolution(varGen, options, synthResult.solution))
						.visitProgram(finalCleaned);
			} else {
				substituted = finalCleaned;
			}
			Program substitutedCleaned = (new CleanupFinalCode(varGen, options, visibleRControl(finalCleaned)))
					.visitProgram(substituted);
			generateCode(substitutedCleaned);
			return true;
		} catch (SketchException e) {
//			e.printStackTrace();
			System.out.println("===RepairStageRunner ===not solve");
			return false;
		}
	}
}
