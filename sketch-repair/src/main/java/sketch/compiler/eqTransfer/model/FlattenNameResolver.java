/**
 * @author Lisa Jun 10, 2016 FlattenNameResolver.java 
 */
package sketch.compiler.eqTransfer.model;

import java.util.HashMap;

public class FlattenNameResolver {
	static HashMap<String, String> varValue = new HashMap<String, String>();
	static HashMap<String, String> originValue = new HashMap<String, String>();

	public static void addUpdate(String var, String value) {
		varValue.put(var, value);
	}

	public static void addOrigin(String var, String value) {
		originValue.put(var, value);
	}

	public static String getUpdate(String var) {
		return varValue.get(var);
	}
	public static String getOrigin(String var) {
		return originValue.get(var);
	}
	
	
}
