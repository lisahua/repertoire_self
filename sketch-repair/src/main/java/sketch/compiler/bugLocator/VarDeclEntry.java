/**
 * @author Lisa Mar 22, 2016 VarDeclEntry.java 
 */
package sketch.compiler.bugLocator;

import sketch.compiler.ast.core.typs.StructDef;

public class VarDeclEntry {

	private String name;
	private String func;
	private StructDef type;
	private int bound=0;
	private String origin;

	public VarDeclEntry(String name, StructDef type, String func) {
		this(name,name,type,func);
		
	}

	public VarDeclEntry(String name, String origin, StructDef type, String func) {
		this.name = name;
		this.func = func;
		this.type = type;
		this.origin = origin;
		for (char a: origin.toCharArray()) {
			if (a=='.')
				bound++;
		}
	}

	public String getOrigin() {
		return origin;
	}

	public int getBound() {
		return bound;
	}

	public String getName() {
		return name;
	}

	public String getFunc() {
		return func;
	}

	public StructDef getType() {
		return type;
	}

	public String getTypeS() {
		return type.getName();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public void setType(StructDef type) {
		this.type = type;
	}

	public void setBound(int bound) {
		this.bound = bound;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
		bound=0;
		for (char a: origin.toCharArray()) {
			if (a=='.')
				bound++;
		}
	}

	public String toString() {
		return "[" + func + "] [" + type + "] [" + origin + "] [" + name + "]  [" + bound + "]";
	}

	public VarDeclEntry clone() {
		VarDeclEntry entry = new VarDeclEntry(name,origin, type,func);
		entry.bound = bound;
		return entry;
		
	}
}
