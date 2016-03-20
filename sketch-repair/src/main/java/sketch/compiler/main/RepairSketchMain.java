/**
 * @author Lisa Mar 8, 2016 RepairSketchMain.java 
 */
package sketch.compiler.main;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.bugLocator.RepairGenerator;
import sketch.compiler.bugLocator.RepairFEFuncVisitor;
import sketch.compiler.main.cmdline.SketchOptions;
import sketch.compiler.main.other.ErrorHandling;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.util.exceptions.ProgramParseException;
import sketch.util.exceptions.SketchException;

public class RepairSketchMain extends SequentialSketchMain {
	static Program prog = null;
	 public RepairSketchMain(String[] args) {
	        super(new SketchOptions(args));
	    }
		/** for subclasses */
	    public RepairSketchMain(SketchOptions options) {
	        super(options);
	    }

	public void run() {
		this.log(1, "Benchmark = " + this.benchmarkName());
//		Program prog = null;
		try {
//			prog = (new RepairProgramStage(varGen, options)).visitProgram(null);
			prog = parseProgram();
//			RepairCandidateGenerator repair = new RepairCandidateGenerator();
//			repair.generateCandidaite(prog, e, options.sketchFile);
//			SuspiciousVisitor visitor = new SuspiciousVisitor();
//			prog.accept(visitor);
			
		} catch (SketchException se) {
			throw se;
		} catch (IllegalArgumentException ia) {
			throw ia;
		} catch (RuntimeException re) {
			throw new ProgramParseException("Sketch failed to parse: " + re.getMessage());
		}

		prog = this.preprocAndSemanticCheck(prog);
		
//		 System.out.println("=========After semantic check ======="+prog);
		SynthesisResult synthResult = this.partialEvalAndSolve(prog);
		prog = synthResult.lowered.result;
		
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
		this.log(1, "[SKETCH] DONE");
	}
	
	public static void main(String[] args) {
		
		System.out.println("SKETCH version " + PlatformLocalization.getLocalization().version);
		long beg = System.currentTimeMillis();
		String prevError = "";
		// ErrorHandling.checkJavaVersion(1, 6);
		// TODO -- change class names so this is clear
		final SequentialSketchMain sketchmain = new SequentialSketchMain(args);
		PlatformLocalization.getLocalization().setTempDirs();
		int exitCode = 0;
		SketchOptions options = SketchOptions.getSingleton();
		try {
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
				new RepairSketchMain(options).run();
				// System.out.println("End run");
			}
		} catch (SketchException e) {
			e.print();
			if (isTest) {
				throw e;
			} else {
				// e.printStackTrace();
				//TODO recursion starts here
				RepairGenerator repair = new RepairGenerator();
				repair.startRepair(prog, e, options.sketchFile);
				exitCode = 1;
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
}
