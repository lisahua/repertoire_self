/**
 * @author Lisa Mar 21, 2016 LocalVariableResolver.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import sketch.compiler.ast.core.NameResolver;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.bugLocator.VarDeclEntry;
import sketch.util.datastructures.ImmutableTypedHashMap;

public class LocalVariableResolver extends NameResolver {
	private HashMap<String, HashMap<String, VarDeclEntry>> funcVar = new HashMap<String, HashMap<String, VarDeclEntry>>();

	public LocalVariableResolver(Program prog) {
		super(prog);
	}

	public void extractCandidate(String func, String type) {
		// HashMap<String, Type> varInMethod =
		// controller.getAllVarInMethod(func);
		Queue<String> queue = new LinkedList<String>();

	}

	public void add(String name, StructDef struct, String func) {
		HashMap<String, VarDeclEntry> map = funcVar.get(func);
		map = (map == null) ? new HashMap<String, VarDeclEntry>() : map;

		VarDeclEntry entry = new VarDeclEntry(name, struct, func);
		map.put(name, entry);
		funcVar.put(func, map);

	}

	public VarDeclEntry getVarTypeInFunc(String func, String t) {
		return funcVar.get(func).get(t);
	}

	public VarDeclEntry getFieldTypeInStruct(VarDeclEntry entry, String t) {
		StructDef strt = entry.getType();
		VarDeclEntry fieldEntry = funcVar.get(entry.getFunc()).get(t);
		if (entry != null)
			return fieldEntry;
		HashMap<String, VarDeclEntry> map = funcVar.get(entry.getFunc());
		ImmutableTypedHashMap<String, Type> fieldMap = strt.getFieldTypMap();
		Iterator<Entry<String, Type>> iterator = fieldMap.iterator();
		while (iterator.hasNext()) {
			Entry<String, Type> e = iterator.next();
			if (e.getKey().equals(t)) {
				System.out.println("===DEBUG ===" + e.getKey() + "," + e.getValue());
				Type type = e.getValue();
				String origin = entry.getOrigin() + "." + t;
				VarDeclEntry new_e = new VarDeclEntry(type.toString() + "." + t, origin,
						this.getStruct(type.toString()), entry.getFunc());
				map.put(origin, new_e);
				funcVar.put(entry.getFunc(), map);
				return new_e;
			}
		}
		return null;
	}

//	public void add(VarDeclEntry entry) {
//		add(entry.getName(), entry.getType(), entry.getFunc());
//	}

	// private Type getType(String type, String field) {
	// if (type.contains("@"))
	// type = type.substring(0, type.indexOf("@"));
	// StructDef strt = nRes.getStruct(type);
	// // StructDef strt = structMap.get(type);
	// ImmutableTypedHashMap<String, Type> fieldMap = strt.getFieldTypMap();
	// return fieldMap.get(field);
	// }
}
