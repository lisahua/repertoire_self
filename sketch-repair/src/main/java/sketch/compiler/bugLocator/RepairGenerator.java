/**
 * @author Lisa Mar 9, 2016 RepairCandidateGenerator.java 
 */
package sketch.compiler.bugLocator;

import java.util.HashMap;

import sketch.compiler.ast.core.Program;
import sketch.compiler.main.other.RepairSketchOptions;

public class RepairGenerator {
	HashMap<String, String> fileFixMap = null;
	Program prog;
	RepairSketchOptions options;

	public RepairGenerator(Program prog, RepairSketchOptions options) {
		this.prog = prog;
		this.options = options;
	}

	

	
}
