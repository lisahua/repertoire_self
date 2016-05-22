/**
 * @author Lisa May 19, 2016 TypeNameResolverModel.java 
 */
package sketch.compiler.nameResolver;

import java.util.HashSet;

import sketch.compiler.ast.core.typs.Type;

@SuppressWarnings("rawtypes")
public class BlockNameResolverModel implements Comparable {

	String func = "";
	int start;
	int end;
	int loc;
	String typeS;
	Type type;
	String name;

	public BlockNameResolverModel(String func, String name, Type type, int start, int loc) {
		this.type = type;
		this.name = name;
		this.start = start;
		this.loc = loc;
		this.func = func;
	}

	@Override
	public int compareTo(Object o) {
		BlockNameResolverModel tmp = (BlockNameResolverModel) o;
		return tmp.start - start;
	}

	public String toString() {
		return "[" + func + "," + type.toString() + "," + name + "," + start + "," + loc + "," + end + "]";
	}
}
