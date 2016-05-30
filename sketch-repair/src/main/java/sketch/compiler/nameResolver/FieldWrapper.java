/**
 * @author Lisa May 20, 2016 FieldWrapper.java 
 */
package sketch.compiler.nameResolver;

import sketch.compiler.ast.core.typs.Type;

public class FieldWrapper {

	Type origin;
	Type destination;
	String name;
	int degree = 0;

	public FieldWrapper(Type o, Type d, String n, int dg) {
		origin = o;
		destination = d;
		name = n;
		degree = dg;
	}

	public String getConcreteValue(String var) {
		return var + "." + name;
	}

	public String toString() {
		return origin + "," + destination + "," + name + "," + degree;
	}
}
