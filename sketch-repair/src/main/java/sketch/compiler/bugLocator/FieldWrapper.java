/**
 * @author Lisa Mar 20, 2016 FieldWrapper.java 
 */
package sketch.compiler.bugLocator;

import sketch.compiler.ast.core.typs.Type;

public class FieldWrapper {

	String fieldS = "";
	Type type=null;
	
	public FieldWrapper(String fieldS,Type type) {
		this.fieldS = fieldS;
		this.type = type;
	}

	public String getFieldS() {
		return fieldS;
	}

	public Type getType() {
		return type;
	}
	
	
}
