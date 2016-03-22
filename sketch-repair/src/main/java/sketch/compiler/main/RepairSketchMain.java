/**
 * @author Lisa Mar 8, 2016 RepairSketchMain.java 
 */
package sketch.compiler.main;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import sketch.compiler.ast.core.Program;
import sketch.compiler.main.other.ErrorHandling;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.ParseProgramStage;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.util.exceptions.ProgramParseException;
import sketch.util.exceptions.SketchException;

public class RepairSketchMain extends SequentialSketchMain {
	// static Program prog = null;
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
		} catch (RuntimeException re) {
			throw new ProgramParseException("Sketch failed to parse: " + re.getMessage());
		}
		try {
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
					ErrorHandling.handleErr(e);
					exitCode = 1;
				} catch (InterruptedException e) {
					ErrorHandling.handleErr(e);
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
			ErrorHandling.handleErr(e);
			// necessary for unit tests, etc.
			if (isTest) {
				throw e;
			} else {
				exitCode = 1;
			}
		} catch (RuntimeException e) {
			ErrorHandling.handleErr(e);
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
		List<String> files = rStage.startRepair(prog, e);
		if (files == null || files.size() == 0)
			return;

		for (String f : files) {
			String[] new_arg = options.args;
			System.out.println("======DEBUG===" + new_arg[0] + "," + f);
			new_arg[0] = f;
			options = new RepairSketchOptions(new_arg);
			if (recurRun()) {
				System.out.println("======Repair End===" + f + "," + rStage.getFixPerFile(f));
				break;
			}
		}
	}
	 protected Program parseProgram() {
	        return (new ParseProgramStage(varGen, options)).visitProgram(null);
	    }
}

enum RPSTATUS {
	ONGOING, PASS, NULL
}
