/**
 * @author Lisa Mar 21, 2016 LocalVariableResolver.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import sketch.compiler.ast.core.FieldDecl;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.NameResolver;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.bugLocator.VarDeclEntry;
import sketch.util.datastructures.ImmutableTypedHashMap;

public class LocalVariableResolver extends NameResolver {
	private HashMap<String, HashMap<String, VarDeclEntry>> funcVar = new HashMap<String, HashMap<String, VarDeclEntry>>();
	private HashMap<String, List<VarDeclEntry>> fieldPerStruct ;
	public LocalVariableResolver(Program prog) {
		super(prog);
		genAllFieldsPerType();
	}

	public void extractCandidate(String func, String type, int bound) {
		LinkedList<VarDeclEntry> queue = new LinkedList<VarDeclEntry>();
		HashMap<String, VarDeclEntry> map = funcVar.get(func);
		queue.addAll(map.values());
		String candidate = "{|";
		int curBound = 0;
		while (!queue.isEmpty()) {
			VarDeclEntry entry = queue.poll();
			String var = "";
			if (entry.getBound() == curBound) {
				if (entry.getTypeS().equals(type)) {
					var += entry.getName();
				} else {

				}
			}
		}

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
		String origin = entry.getOrigin() + "." + t;
		VarDeclEntry fieldEntry = getVarTypeInFunc(entry.getFunc(), origin);
		if (fieldEntry != null)
			return fieldEntry;
		HashMap<String, VarDeclEntry> map = funcVar.get(entry.getFunc());
		ImmutableTypedHashMap<String, Type> fieldMap = strt.getFieldTypMap();

		Iterator<Entry<String, Type>> iterator = fieldMap.iterator();
		while (iterator.hasNext()) {
			Entry<String, Type> e = iterator.next();

			if (e.getKey().equals(t)) {
				Type type = e.getValue();
				VarDeclEntry new_e = new VarDeclEntry(strt.getName() + "." + t, origin, getStruct(type.toString()),
						entry.getFunc());
				System.out.println("===getFieldTypeInStruct equal===" + e.getKey() + "," + e.getValue() + "," + new_e);

				map.put(origin, new_e);
				funcVar.put(entry.getFunc(), map);
				return new_e;
			}
		}
		return null;
	}

	public List<VarDeclEntry> resolveFieldChain(String func, String string) {
		String[] token = string.split("\\.");
		VarDeclEntry current = null;
		List<VarDeclEntry> fields = new ArrayList<VarDeclEntry>();
		for (String t : token) {
			t = t.trim();
			if (t.length() == 0)
				continue;
			if (current == null) {
				current = getVarTypeInFunc(func, t);
			} else {
				current = getFieldTypeInStruct(current, t);
			}
			fields.add(current);
			System.out.println(current);
		}
		return fields;
	}

	private void genAllFieldsPerType() {
	fieldPerStruct	= new HashMap<String, List<VarDeclEntry>>();
	for (String struct: structNamesList()) {
		StructDef def = getStruct(struct);
		ImmutableTypedHashMap<String, Type> fieldMap = def.getFieldTypMap();
		Iterator<Entry<String, Type>> iterator = fieldMap.iterator();
		List<VarDeclEntry> entryList = new ArrayList<VarDeclEntry>();
		while (iterator.hasNext()) {
			Entry<String, Type> e = iterator.next();
			VarDeclEntry new_e = new VarDeclEntry(struct+"."+e.getKey(), getStruct(e.getValue().toString()),null);
			entryList.add(new_e);
		}
		fieldPerStruct.put(struct, entryList);
	}

	}
}
