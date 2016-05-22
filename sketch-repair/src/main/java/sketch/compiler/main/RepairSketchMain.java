/**
 * @author Lisa Mar 8, 2016 RepairSketchMain.java 
 */
package sketch.compiler.main;

import static sketch.util.DebugOut.printError;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import sketch.compiler.ast.core.Program;
import sketch.compiler.main.other.ErrorHandling;
import sketch.compiler.main.other.OutputSketchCode;
import sketch.compiler.main.other.RepairSketchOptions;
import sketch.compiler.main.other.RepairStage;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.ParseProgramStage;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.compiler.main.seq.SequentialSketchMain.SketchLoweringResult;
import sketch.compiler.main.seq.SequentialSketchMain.SynthesisResult;
import sketch.compiler.solvers.SATBackend;
import sketch.compiler.solvers.parallel.StrategicalBackend;
import sketch.util.exceptions.ProgramParseException;
import sketch.util.exceptions.SketchException;

public class RepairSketchMain extends SequentialSketchMain {
	public RepairSketchOptions options;
	static RPSTATUS status = RPSTATUS.NULL;

	// static String file = "";
	public RepairSketchMain(String[] args) {
		super(new RepairSketchOptions(args));
		options = new RepairSketchOptions(args);
	}

	/** for subclasses */
	public RepairSketchMain(RepairSketchOptions options) {
		super(options);
		this.options = options;
	}

	public void run() {
		this.log(1, "Benchmark = " + this.benchmarkName());
		recurRun();
		this.log(1, "[SKETCH] DONE");
	}

	private boolean recurRun() {
		Program prog = null;
		try {
			prog = parseProgram();
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
		} catch (SketchException se) {
			System.out.println("========Repair starts: " + se.getMessage() + "==========");
			if (status == RPSTATUS.NULL) {
				status = RPSTATUS.ONGOING;
				parseProgram(prog, se.getMessage());
			}
//			se.printStackTrace();
		} catch (RuntimeException re) {
			re.printStackTrace();
//			throw new ProgramParseException("Sketch failed to parse: " + re.getMessage());
		}
	
		return false;
	}

	public static void main(String[] args) {
		System.out.println("SKETCH version " + PlatformLocalization.getLocalization().version);
		long beg = System.currentTimeMillis();
		ErrorHandling.checkJavaVersion(1, 6);
		// TODO -- change class names so this is clear
		final RepairSketchMain sketchmain = new RepairSketchMain(args);
		PlatformLocalization.getLocalization().setTempDirs();
		int exitCode = 0;
		try {
			RepairSketchOptions options = RepairSketchOptions.getSingleton();
			if (options.feOpts.timeout > 0) {
				ExecutorService executor = Executors.newSingleThreadExecutor();
				Future<?> f = executor.submit(sketchmain);
				try {
					f.get((long) options.feOpts.timeout, TimeUnit.MINUTES);
				} catch (TimeoutException e) {
					System.out.println("Sketch front-end timed out");
					exitCode = 1;
				} catch (ExecutionException e) {
					e.printStackTrace();
					// ErrorHandling.handleErr(e);
					exitCode = 1;
				} catch (InterruptedException e) {
					e.printStackTrace();
					// ErrorHandling.handleErr(e);
					exitCode = 1;
				} finally {
					executor.shutdown();
				}
			} else { // normal run
				// System.out.println("Running");
				sketchmain.run();
				// System.out.println("End run");
			}
		} catch (java.lang.Error e) {
			e.printStackTrace();
			// ErrorHandling.handleErr(e);
			// necessary for unit tests, etc.
			if (isTest) {
				throw e;
			} else {
				exitCode = 1;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			// ErrorHandling.handleErr(e);
			if (isTest) {
				throw e;
			} else {
				if (sketchmain.options.debugOpts.verbosity > 3) {
					e.printStackTrace();
				}
				exitCode = 1;
			}
		} finally {
			System.out.println("Total time = " + (System.currentTimeMillis() - beg));
		}
		if (exitCode != 0) {
			System.exit(exitCode);
		}
	}

	protected void parseProgram(Program prog, String e) {
		RepairStage rStage = new RepairStage(options);
		boolean result = rStage.startRepair(prog, e);
	}

	protected Program parseProgram() {
		return (new ParseProgramStage(varGen, options)).visitProgram(null);
	}

	protected void outputCCode(Program prog) {
		if (prog == null) {
			printError("Final code generation encountered error, skipping output");
			return;
		}

		(new OutputSketchCode(varGen, options)).visitProgram(prog);
	}
	
}

enum RPSTATUS {
	ONGOING, PASS, NULL
}
