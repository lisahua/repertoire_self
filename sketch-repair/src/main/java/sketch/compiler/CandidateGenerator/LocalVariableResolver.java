/**
 * @author Lisa Mar 21, 2016 LocalVariableResolver.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sketch.compiler.ast.core.NameResolver;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.bugLocator.VarDeclEntry;
import sketch.util.datastructures.ImmutableTypedHashMap;

public class LocalVariableResolver extends NameResolver {
	private HashMap<String, HashMap<String, VarDeclEntry>> funcVar = new HashMap<String, HashMap<String, VarDeclEntry>>();
	private HashMap<String, HashMap<String, VarDeclEntry>> fieldPerStruct;
	private HashSet<String> allStructs = new HashSet<String>();

	public LocalVariableResolver(Program prog) {
		super(prog);
		allStructs.addAll(structNamesList());
		genAllFieldsPerType();
	}

	public void add(String name, StructDef struct, String func) {
		HashMap<String, VarDeclEntry> map = funcVar.get(func);
		map = (map == null) ? new HashMap<String, VarDeclEntry>() : map;

		VarDeclEntry entry = new VarDeclEntry(name, struct, func);
		map.put(name, entry);
		funcVar.put(func, map);

	}

	private VarDeclEntry getVarTypeInFunc(String func, String t) {
		return funcVar.get(func).get(t);
	}

	private VarDeclEntry getFieldTypeInStruct(VarDeclEntry entry, String t) {
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

		}
		return fields;
	}

	private VarDeclEntry getEntryInStruct(String type, String field) {
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
			}
			fieldPerStruct.put(struct, entryList);
		}
	}

	public StructDef getStruct(String name) {
		return super.getStruct(name);
	}

	public List<StringBuilder> extractCandidateSetAsHole(String func, String type, int bound) {
		List<HashSet<String>> layers = extractCandidateList(func, type, bound);

		List<StringBuilder> sList = new ArrayList<StringBuilder>();
		for (HashSet<String> set : layers) {
			StringBuilder builder = new StringBuilder();
			if (set.size() > 0) {
				for (String s : set)
					builder.append(s + "|");
				builder.deleteCharAt(builder.length() - 1);
			}
			sList.add(builder);
		}
		return sList;
	}

	public List<HashSet<String>> extractCandidateList(String func, String type, int bound) {
		HashMap<String, VarDeclEntry> map = funcVar.get(func);
		HashMap<String, CandidateWrapper> first = new HashMap<String, CandidateWrapper>();
		for (Map.Entry<String, VarDeclEntry> entry : map.entrySet()) {
			String typ = entry.getValue().getTypeS();
			CandidateWrapper wp = first.get(typ);
			if (wp == null)
				wp = new CandidateWrapper(typ);
			if (!entry.getKey().contains(".")) {
				wp.addValue(entry.getKey());
				first.put(typ, wp);
			}
		}
		List<HashMap<String, CandidateWrapper>> table = new ArrayList<HashMap<String, CandidateWrapper>>();
		table.add(first);
		for (int i = 1; i < bound; i++)
			table.add(genNextLayerCandidateList(table.get(i - 1)));
		List<HashSet<String>> canList = genCandStringList(table, type);
		return canList;
	}

	private List<HashSet<String>> genCandStringList(List<HashMap<String, CandidateWrapper>> table, String type) {
		List<HashSet<String>> result = new ArrayList<HashSet<String>>();
		for (int i = 0; i < table.size(); i++) {
			HashSet<String> candSet = new HashSet<String>();
			HashMap<String, CandidateWrapper> map = table.get(i);
			if (map.containsKey(type))
				candSet.addAll(map.get(type).getValues());
			result.add(candSet);
		}
		return result;
	}

	private HashMap<String, CandidateWrapper> genNextLayerCandidateList(HashMap<String, CandidateWrapper> prev) {
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
				wp.addValue(prev_e.getValue().getValues(), fld.getKey());
				new_layer.put(type, wp);
			}
		}
		return new_layer;
	}

	public HashSet<String> resolveFieldInFunc(String func, String fields) {
		HashSet<String> set = new HashSet<String>();
		String[] token = fields.split("\\.");
		if (token.length == 0)
			return set;

		for (int i = 0; i < token.length - 1; i++) {

		}
		return set;
	}

	public boolean isPrimitiveType(String func, String exp) {
		List<VarDeclEntry> list = resolveFieldChain(func, exp);
		VarDeclEntry entry = list.get(list.size() - 1);
		return entry.getType() == null;
	}

	public List<String> instantiateField(String func, String field) {
		HashMap<String, VarDeclEntry> varType = funcVar.get(func);
		List<String> res = new ArrayList<String>();
		String[] token = field.split("\\.");
		if (token.length == 0) {
			for (Map.Entry<String, VarDeclEntry> entry : varType.entrySet()) {
				VarDeclEntry decl = entry.getValue();
				if (decl.getName().equals(field)) {
					res.add(decl.getOrigin());
				}
			}
		} else {
			
			VarDeclEntry current = null;
			List<String> root = new ArrayList<String>();

			for (Map.Entry<String, VarDeclEntry> entry : varType.entrySet()) {
				VarDeclEntry decl = entry.getValue();
				if (decl.getName().equals(field)) {

				}
			}
		}
		return res;
	}
}
