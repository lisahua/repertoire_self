/**
 * @author Lisa May 19, 2016 SATBackEndAssertWrapper.java 
 */
package sketch.compiler.wrapper;

import java.io.OutputStream;
import java.io.PrintStream;

import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.TempVarGen;
import sketch.compiler.dataflow.recursionCtrl.RecursionControl;
import sketch.compiler.main.cmdline.SketchOptions;
import sketch.compiler.solvers.SATBackend;

public class SATBackEndAssertWrapper extends SATBackend {

	RecursionControl rcontrol;
	private boolean tracing = false;

	public void activateTracing() {
		tracing = true;
	}

	public SATBackEndAssertWrapper(SketchOptions options, RecursionControl rcontrol, TempVarGen varGen) {
		super(options, rcontrol, varGen);
		this.rcontrol = rcontrol;

	}

	protected void partialEval(Program prog, OutputStream outStream) {
		PrintStream pstream = new PrintStream(outStream, false);
//		 sketch.compiler.dataflow.nodesToSB.ProduceBooleanFunctions
//		 partialEval = new
//		 sketch.compiler.dataflow.nodesToSB.ProduceBooleanFunctions(
//		 varGen, oracle, pstream, options.bndOpts.unrollAmnt,
//		 options.bndOpts.arrSize, rcontrol, tracing);
		ProduceBooleanFunctionWrapper partialEval = new ProduceBooleanFunctionWrapper(varGen, oracle, pstream,
				options.bndOpts.unrollAmnt, options.bndOpts.arrSize, rcontrol, tracing);

		log("MAX LOOP UNROLLING = " + options.bndOpts.unrollAmnt);
		log("MAX FUNC INLINING  = " + options.bndOpts.inlineAmnt);
		prog.accept(partialEval);

		pstream.flush();
		log("After prog.accept(partialEval)");
	}

}
