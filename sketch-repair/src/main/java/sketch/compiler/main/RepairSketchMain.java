/**
 * @author Lisa Mar 8, 2016 RepairSketchMain.java 
 */
package sketch.compiler.main;

import static sketch.util.DebugOut.printError;

import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import sketch.compiler.ast.core.Program;
import sketch.compiler.cmdline.SemanticsOptions.ArrayOobPolicy;
import sketch.compiler.cmdline.SolverOptions.SynthSolvers;
import sketch.compiler.cmdline.SolverOptions.VerifSolvers;
import sketch.compiler.eqTransfer.RepairMapper;
import sketch.compiler.main.other.ErrorHandling;
import sketch.compiler.main.other.OutputSketchCode;
import sketch.compiler.main.other.RepairSketchOptions;
import sketch.compiler.main.other.RepairStage;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.ParseProgramStage;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.compiler.solvers.SATBackend;
import sketch.compiler.solvers.parallel.StrategicalBackend;
import sketch.compiler.wrapper.SATBackEndAssertWrapper;
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
			RepairMapper.setOriginProgram(prog);
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
	/*
	//Overwrite below is for incremental assertion checking
	public SynthesisResult partialEvalAndSolve(Program prog) {
        SketchLoweringResult sketchProg = lowerToSketch(prog);
        // sketchProg.result.debugDump("");
        if (prog.hasFunctions()) {

            SATBackend solver;
            if (options.solverOpts.parallel) {
                solver = new StrategicalBackend(options, internalRControl(), varGen);
            } else {
                solver = new SATBackend(options, internalRControl(), varGen);
//                solver = new SATBackEndAssertWrapper(options, internalRControl(), varGen);
            }

            if (options.debugOpts.trace) {
                solver.activateTracing();
            }
            backendParameters();
            solver.partialEvalAndSolve(sketchProg.result);


            return new SynthesisResult(sketchProg, solver.getOracle(),
                    solver.getLastSolutionStats());
        } else {
            return new SynthesisResult(sketchProg, null, null);
        }
    }
	protected void backendParameters() {
        options.backendOptions = new Vector<String>();
        Vector<String> backendOptions = options.backendOptions;

        // pass all short-style arguments to the backend
        backendOptions.addAll(options.backendArgs);
        backendOptions.add("--bnd-inbits");
        backendOptions.add(""+ options.bndOpts.inbits);
        if (options.bndOpts.angelicbits > 0) {
            backendOptions.add("--bnd-angelicbits");
            backendOptions.add("" + options.bndOpts.angelicbits);
        }
        if (options.bndOpts.angelicArrsz > 0) {
            backendOptions.add("--bnd-angelic-arrsz");
            backendOptions.add("" + options.bndOpts.angelicArrsz);
        }

        backendOptions.add("--boundmode");
        backendOptions.add("" + options.bndOpts.boundMode);

        backendOptions.add("--verbosity");
        backendOptions.add(""+ options.debugOpts.verbosity);
        backendOptions.add("--print-version"); // run by default

        if (options.solverOpts.seed != 0) {
            // parallel running will use its own seeds systematically
            if (!options.solverOpts.parallel) {
                backendOptions.add("--seed");
                backendOptions.add("" + options.solverOpts.seed);
            }
        }
        if (options.solverOpts.simiters != 0) {
            backendOptions.add("-simiters");
            backendOptions.add("" + options.solverOpts.simiters);
        }
        if (options.solverOpts.randassign) {
            backendOptions.add("-randassign");
        }
        if (options.debugOpts.cex) {
            backendOptions.add("--print-cex");
        }
        if (options.solverOpts.synth != SynthSolvers.NOT_SET) {
            // assert false : "solver opts synth need to convert old style command line args";
            backendOptions.add("-synth");
            backendOptions.add("" + options.solverOpts.synth.toString());
        }
        if (options.solverOpts.verif != VerifSolvers.NOT_SET) {
            // assert false : "solver opts verif need to convert old style command line args";
            backendOptions.add("-verif");
            backendOptions.add("" + options.solverOpts.verif.toString());
        }
        if (options.semOpts.arrayOobPolicy == ArrayOobPolicy.assertions) {
            backendOptions.add("--assumebcheck");
        } else if (options.semOpts.arrayOobPolicy == ArrayOobPolicy.wrsilent_rdzero) {
            // backendOptions.add("--sem-array-OOB-policy=wrsilent_rdzero");
        }
        if(options.bndOpts.inlineAmnt > 0){
            backendOptions.add("--bnd-inline-amnt");
            backendOptions.add("" + options.bndOpts.inlineAmnt);
        }

        if (options.bndOpts.intRange > 0) {
            backendOptions.add("--bndwrand");
            backendOptions.add("" + options.bndOpts.intRange);
        }

        if (options.solverOpts.lightverif) {
            backendOptions.add("-lightverif");
        }

        if (options.debugOpts.showDag) {
            backendOptions.add("-showDAG");
        }

        if (options.debugOpts.outputDag != null) {
            backendOptions.add("-writeDAG");
            backendOptions.add(options.debugOpts.outputDag);
        }

        if (options.bndOpts.dagSize > 0) {
            backendOptions.add("--bnd-dag-size");
            backendOptions.add("" + options.bndOpts.dagSize);
        }

        if (options.solverOpts.olevel >= 0) {
            backendOptions.add("--olevel");
            backendOptions.add("" + options.solverOpts.olevel);
        }
        if (options.solverOpts.simpleInputs) {
            // assert false : "need to convert old style command line args";
            backendOptions.add("-nosim");
        }
        if (options.bndOpts.angTupleDepth > 0) {
            backendOptions.add("-angelictupledepth");
            backendOptions.add("" + options.bndOpts.angTupleDepth);
        }
        if (options.bndOpts.srcTupleDepth > 0) {
            backendOptions.add("-srctupledepth");
            backendOptions.add("" + options.bndOpts.srcTupleDepth);
        }
        if (options.solverOpts.onlySpRand) {
            backendOptions.add("-onlysprandassign");
        }
        if (options.solverOpts.spRandBias > 0) {
            backendOptions.add("-sprandbias");
            backendOptions.add("" + options.solverOpts.spRandBias);
        }
    }*/
	
}

enum RPSTATUS {
	ONGOING, PASS, NULL
}
