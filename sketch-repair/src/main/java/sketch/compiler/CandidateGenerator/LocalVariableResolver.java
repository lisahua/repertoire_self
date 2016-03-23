/**
 * @author Lisa Mar 21, 2016 LocalVariableResolver.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import sketch.compiler.ast.core.NameResolver;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.bugLocator.CandidateWrapper;
import sketch.compiler.bugLocator.VarDeclEntry;
import sketch.util.datastructures.ImmutableTypedHashMap;

public class LocalVariableResolver extends NameResolver {
	private HashMap<String, HashMap<String, VarDeclEntry>> funcVar = new HashMap<String, HashMap<String, VarDeclEntry>>();
	private HashMap<String, HashMap<String, VarDeclEntry>> fieldPerStruct;

	public LocalVariableResolver(Program prog) {
		super(prog);
		genAllFieldsPerType();
	}

	public String extractCandidate(String func, String type, int bound) {
		// LinkedList<VarDeclEntry> queue = new LinkedList<VarDeclEntry>();
		HashMap<String, VarDeclEntry> map = funcVar.get(func);
		HashMap<String, CandidateWrapper> first = new HashMap<String, CandidateWrapper>();
		for (Map.Entry<String, VarDeclEntry> entry : map.entrySet()) {
			String typ = entry.getValue().getTypeS();
//			System.out.println("====extract candidate ====" + typ);
			CandidateWrapper wp = first.get(typ);
			if (wp == null)
				wp = new CandidateWrapper(typ);
			wp.addValue(entry.getKey());
			first.put(typ, wp);
		}
		List<HashMap<String, CandidateWrapper>> table = new ArrayList<HashMap<String, CandidateWrapper>>();
		table.add(first);
		for (int i = 1; i < bound; i++)
			table.add(genNextLayerCandidate(table.get(i - 1)));
		return genCandString(table, type);
	}

	private String genCandString(List<HashMap<String, CandidateWrapper>> table, String type) {
		HashMap<String, CandidateWrapper> prev = null;
		StringBuilder builder = new StringBuilder("{|");
		for (HashMap<String, CandidateWrapper> map : table) {
			if (prev == null) {
				if (map.containsKey(type)) {
					builder.append(map.get(type).getValueString());
				}
			} else {
				if (map.containsKey(type)) {
					builder.append(prev.toString() + map.get(type).toString());
				}
			}
			prev = map;
		}
		builder.append("}");
		System.out.println("===genCandString ====" + builder.toString());
		return builder.toString();
	}

	private HashMap<String, CandidateWrapper> genNextLayerCandidate(HashMap<String, CandidateWrapper> prev) {
		HashMap<String, CandidateWrapper> new_layer = new HashMap<String, CandidateWrapper>();

		for (Map.Entry<String, CandidateWrapper> prev_e : prev.entrySet()) {
			HashMap<String, VarDeclEntry> fields = fieldPerStruct.get(prev_e.getKey());
			if (fields == null)
				continue;
			for (Map.Entry<String, VarDeclEntry> fld : fields.entrySet()) {
				String type = fld.getValue().getTypeS();
				CandidateWrapper wp = new_layer.get(type);
				if (wp == null)
					wp = new CandidateWrapper(type);
				wp.setRootString(prev_e.getValue().toString());
				wp.addValue(fld.getKey());
			}
		}
		return new_layer;
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
		VarDeclEntry model_e = getEntryInStruct(strt.getName(), t).clone();
		model_e.setOrigin(origin);
		model_e.setFunc(entry.getFunc());
		map.put(origin, model_e);
		funcVar.put(entry.getFunc(), map);
		return model_e;
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

	private VarDeclEntry getEntryInStruct(String type, String field) {
		System.out.println("===getEntryInstruct " + type + "," + field);
		return fieldPerStruct.get(type).get(field);
	}

	private void genAllFieldsPerType() {
		fieldPerStruct = new HashMap<String, HashMap<String, VarDeclEntry>>();
		for (String struct : structNamesList()) {
			StructDef def = getStruct(struct);
			if (struct.contains("@"))
				struct = struct.substring(0, struct.indexOf("@"));
			ImmutableTypedHashMap<String, Type> fieldMap = def.getFieldTypMap();
			Iterator<Entry<String, Type>> iterator = fieldMap.iterator();
			HashMap<String, VarDeclEntry> entryList = new HashMap<String, VarDeclEntry>();
			while (iterator.hasNext()) {
				Entry<String, Type> e = iterator.next();
				VarDeclEntry new_e = new VarDeclEntry(struct + "." + e.getKey(), getStruct(e.getValue().toString()),
						null);
				entryList.put(e.getKey(), new_e);
				System.out.println("====getAllFieldPerType add" + new_e);
			}
			fieldPerStruct.put(struct, entryList);
		}

	}
}
