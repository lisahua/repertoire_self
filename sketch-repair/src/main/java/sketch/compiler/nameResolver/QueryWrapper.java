/**
 * @author Lisa May 29, 2016 QueryWrapper.java 
 */
package sketch.compiler.nameResolver;

import java.util.HashMap;
import java.util.HashSet;

import sketch.compiler.ast.core.typs.Type;

public class QueryWrapper {
	String func;
	int loc;
	HashMap<Type, HashSet<String>> deviatedNames;

	public QueryWrapper(String func2, int loc2, HashMap<Type, HashSet<String>> deviatedNames2) {
		func = func2;
		loc = loc2;
		deviatedNames = deviatedNames2;
	}

	public HashMap<Type, HashSet<String>> existNames(String func, int loc) {
		if (!this.func.equals(func))
			return null;
		if (Math.abs(loc - this.loc) < 2)
			return deviatedNames;
		return null;
	}

}
