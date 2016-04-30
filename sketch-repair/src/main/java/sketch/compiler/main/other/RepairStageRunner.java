/**
 * @author Lisa Mar 21, 2016 RepairStage.java 
 */
package sketch.compiler.main.other;

import java.io.File;
import java.util.HashMap;

import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.TempVarGen;
import sketch.compiler.main.RepairSketchMain;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.ParseProgramStage;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.util.exceptions.SketchException;

public class RepairStageRunner extends RepairSketchMain {
	static String serr = "";
	RepairSketchOptions options = null;
	HashMap<String, String> map = null;
	TempVarGen varGen = new TempVarGen();
	static int index = 0;
	static String fix = null;
	static Program fixProg = null;

	public RepairStageRunner(RepairSketchOptions options) {
		super(options);
		this.options = options.clone();
	}

	protected boolean repairProgram(Program prog, String e) {
		RepairStage rStage = new RepairStage(options);
		serr = parseErr(e);
		return rStage.startRepair(prog, e);

	}

	public boolean solveSketch(String sketchF) {
		Program prog = null;
		try {
			System.out.println("===repair stage runner solve sketch =====");
			// new SimpleCodePrinter().visitProgram(origin);
			options.args[0] = sketchF;
			prog = new ParseProgramStage(varGen, options).visitProgram(null);
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

//			new CodeGenerator().testJavaWriter(substitutedCleaned);

			return true;
		} catch (SketchException e) {
			// e.printStackTrace();
			String err = parseErr(e.getMessage());
			// if (serr.equals("") || serr.equals(err)) {
			System.out.println("===RepairStageRunner ===not solve");
//			new File(sketchF).delete();
			serr = err;
			fix = sketchF;
			return false;
		}

	}

	public Program readSketch(String sketchF) {
		try {
			System.out.println("===repair stage runner read sketch =====");
			// new SimpleCodePrinter().visitProgram(origin);
			options.args[0] = sketchF;
			Program prog = new ParseProgramStage(varGen, options).visitProgram(null);
			prog = this.preprocAndSemanticCheck(prog);
			return prog;
		} catch (SketchException e) {
			// e.printStackTrace();
			System.out.println("===repair stage runner read sketch parse error");
			return null;
		}
	}

	public String parseErr(String err) {
		try {
			int index2 = err.indexOf(":");
			int index3 = err.indexOf("(");
			err = err.substring(index2 + 1, index3).trim();
		} catch (Exception e) {
			// ignore
		}
		return err;
	}

	public static String getFix() {
		return fix;
	}

	public static Program getFixProg() {
		return fixProg;
	}
}
