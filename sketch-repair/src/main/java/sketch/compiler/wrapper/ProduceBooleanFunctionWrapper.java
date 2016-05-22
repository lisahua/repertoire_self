/**
 * @author Lisa May 19, 2016 ProduceBooleanFunctionWrapper.java 
 */
package sketch.compiler.wrapper;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import sketch.compiler.ast.core.TempVarGen;
import sketch.compiler.dataflow.nodesToSB.ProduceBooleanFunctions;
import sketch.compiler.dataflow.recursionCtrl.RecursionControl;
import sketch.compiler.solvers.constructs.ValueOracle;

public class ProduceBooleanFunctionWrapper extends ProduceBooleanFunctions {

	public ProduceBooleanFunctionWrapper(TempVarGen varGen, ValueOracle oracle, PrintStream out, int maxUnroll,
			int maxArrSize, RecursionControl rcontrol, boolean tracing) {
		super(varGen, oracle, out, maxUnroll, maxArrSize, rcontrol, tracing);

	}

}
