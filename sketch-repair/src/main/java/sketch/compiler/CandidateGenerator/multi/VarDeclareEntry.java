/**
 * @author Lisa Mar 22, 2016 VarDeclEntry.java 
 */
package sketch.compiler.CandidateGenerator.multi;

public class VarDeclareEntry {

	private String name;
	private String func;
	private String type;
	private int bound=0;
	private String origin;

	public VarDeclareEntry(String name, String type, String func) {
		this(name,name,type,func);
		
	}

	public VarDeclareEntry(String name, String origin, String type, String func) {
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

	public String getType() {
		return type;
	}

	public String getTypeS() {
//		if (type==null) return "int";
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFunc(String func) {
		this.func = func;
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

	public VarDeclareEntry clone() {
		VarDeclareEntry entry = new VarDeclareEntry(name,origin, type,func);
		entry.bound = bound;
		return entry;
		
	}
}
