/**
 * @author Lisa May 29, 2016 QueryWrapper.java 
 */
package sketch.compiler.nameResolver;

import sketch.compiler.ast.core.typs.Type;

public class QueryWrapper {
	String func;
	int loc;
	Type type;
	StringBuilder builder;

	public QueryWrapper(String func2, int loc2, Type type, StringBuilder builder) {
		func = func2;
		loc = loc2;
		this.type = type;
		this.builder = builder;
	}

	public StringBuilder existNames(String func, int loc, Type type) {
		if (!this.func.equals(func))
			return null;
		if (!this.type.equals(type))
			return null;
		if (Math.abs(loc - this.loc) < 2)
			return builder;
		return null;
	}

}
