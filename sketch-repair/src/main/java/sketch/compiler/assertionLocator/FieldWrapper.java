/**
 * @author Lisa Mar 20, 2016 FieldWrapper.java 
 */
package sketch.compiler.assertionLocator;

import sketch.compiler.ast.core.typs.Type;

public class FieldWrapper {

	private String fieldS = "";
	private Type type=null;
	
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
