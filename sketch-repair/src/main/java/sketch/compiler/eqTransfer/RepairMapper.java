/**
 * @author Lisa Jun 6, 2016 RepairMapper.java 
 */
package sketch.compiler.eqTransfer;

import java.util.HashMap;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;

public class RepairMapper extends FEReplacer {
	private static Program origin = null;
	private static HashMap<String, Function> originMap = new HashMap<String, Function>();

	public static void setOriginProgram(Program prog) {
		origin = prog;
	}

	public static Program checkMapping(Program prog) {
		parseOriginProg();
		parseUpdateProg(prog);
		return prog;
	}

	private static void parseOriginProg() {
		for (sketch.compiler.ast.core.Package pkg : origin.getPackages()) {
			for (Function func : pkg.getFuncs()) {
				String fName = func.getName();
				int id = fName.indexOf("@");
				if (id > 0)
					fName = fName.substring(0, id);
				originMap.put(fName, func);
			}
		}
	}

	// Why I don't save atomic model: because may multiple, and change other
	// place.
	private static Program parseUpdateProg(Program prog) {
		for (sketch.compiler.ast.core.Package pkg : prog.getPackages()) {
			for (Function func : pkg.getFuncs()) {
				String fName = func.getName();
				int id = fName.indexOf("@");
				if (id > 0)
					fName = fName.substring(0, id);
				if (!originMap.containsKey(fName)) continue;
				
			}

		}
		return prog;
	}

}
