/**
 * @author Lisa Mar 8, 2016 RepairSketchMain.java 
 */
package sketch.compiler.main;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import sketch.compiler.main.cmdline.SketchOptions;
import sketch.compiler.main.other.ErrorHandling;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.util.exceptions.SketchException;

public class RepairSketchMain extends SequentialSketchMain {

	public RepairSketchMain(SketchOptions options) {
		super(options);
	}

	public void run() {
		super.run();
	}

	public static void main(String[] args) {
		System.out.println("Repair Sketch Main");
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
				sketchmain.run();
				// System.out.println("End run");
			}
		} catch (SketchException e) {
			e.print();
			File tmp = RepairBugLocalizer.handleSketchException(e, options.sketchFile);
			if (tmp != null) {
				options.sketchFile = tmp;
				if (!prevError.equals(e.getMessage())) {
					prevError = e.getMessage();
					System.out.println("First Repair try:");
					try {
						sketchmain.run();
					} catch (SketchException e2) {

					}
				}
			}
			if (isTest) {
				throw e;
			} else {
				// e.printStackTrace();
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
