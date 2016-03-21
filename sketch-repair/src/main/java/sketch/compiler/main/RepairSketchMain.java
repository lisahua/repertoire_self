/**
 * @author Lisa Mar 8, 2016 RepairSketchMain.java 
 */
package sketch.compiler.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import sketch.compiler.ast.core.Program;
import sketch.compiler.main.cmdline.SketchOptions;
import sketch.compiler.main.other.ErrorHandling;
import sketch.compiler.main.passes.CleanupFinalCode;
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
		Program prog = null;
		try {
			prog = parseProgram();
		} catch (RuntimeException re) {
			throw new ProgramParseException("Sketch failed to parse: " + re.getMessage());
		}
		prog = this.preprocAndSemanticCheck(prog);
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
		} catch (SketchException se) {
			System.out.println("========Repair starts: " + se.getMessage() + "==========");
			parseProgram(prog,se.getMessage());
			System.out.println("========Repair End: ==========");
		}
		this.log(1, "[SKETCH] DONE");
	}
	public static void main(String[] args) {
        System.out.println("SKETCH version " +
                PlatformLocalization.getLocalization().version);
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
        }  catch (java.lang.Error e) {
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
	//
	// public void generateCode(Program prog) {
	// // rename main function so it's not the C main
	// Map<String, String> rm = new HashMap<String, String>();
	// rm.put("main", "_main");
	// prog = (Program) prog.accept(new MethodRename(rm));
	// prog = (Program) prog.accept(new EliminateAliasesInRefParams(varGen));
	//
	// (new OutputSketchCode(varGen, options)).visitProgram(prog);
	//
	// // new SimpleSketchFilePrinter(file).visitProgram(prog)
	// }

	// TODO recursion starts here
	// if (status == REPAIR_STATUS.NULL) {
	// status = REPAIR_STATUS.ONGOING;
	// RepairGenerator repair = new RepairGenerator(prog,options);
	// List<String> files = repair.startRepair(e);
	// for (String f : files) {
	// fix = repair.getFixPerFile().get(f);
	// String file = (options.repairOptions.outputRepair == null)
	// ? f.substring(0, f.indexOf(".")) + ".sk2" :
	// options.repairOptions.outputRepair;
	//
	// String[] arg_re = args;
	// arg_re[0] = f;
	// if (status == REPAIR_STATUS.ONGOING) {
	// try {
	// System.out.println("====Repair start: test " + fix + ", " +
	// options.sketchFile.getName()
	// + "=======");
	// new RepairSketchMain(arg_re).run();
	// System.out.println("====Repair End: repair file at : " + file +
	// "==============");
	// break;
	// } catch (Exception repair_e) {
	// try {
	// new File(f).delete();
	// } catch (Exception x) {
	// }
	// }
	//
	// }
	// }
	//
	// // exitCode = 1;
	// }

	protected Program parseProgram(Program prog, String e) {

		return (new RepairStage(varGen, options, e)).visitProgramInner(prog);

	}
}

enum RPSTATUS {
	ONGOING, PASS, NULL
}
