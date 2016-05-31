/**
 * @author Lisa Mar 21, 2016 LocalVariableResolver.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sketch.compiler.CandidateGenerator.CandidateWrapper;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.NameResolver;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprField;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.Type;
import sketch.util.datastructures.ImmutableTypedHashMap;

public class LocalVariableResolver extends NameResolver {
	private HashMap<String, HashMap<String, VarDeclareEntry>> funcVar = new HashMap<String, HashMap<String, VarDeclareEntry>>();
	private HashMap<String, HashMap<String, Type>> funcVarTypes = new HashMap<String, HashMap<String, Type>>();

	private HashMap<String, HashMap<String, VarDeclareEntry>> fieldPerStruct;
	private HashSet<String> allStructs = new HashSet<String>();

	public LocalVariableResolver(Program prog) {
		super(prog);
		allStructs.addAll(structNamesList());
		allStructs.add("int");
		allStructs.add("bit");
		genAllFieldsPerType();
	}

	public void add(String name, Type type, String func) {
		HashMap<String, VarDeclareEntry> map = funcVar.get(func);
		map = (map == null) ? new HashMap<String, VarDeclareEntry>() : map;
		String struct = type.toString();
		struct = struct.contains("@") ? struct.substring(0, struct.indexOf("@")) : struct;
		VarDeclareEntry entry = new VarDeclareEntry(name, struct, func);
		map.put(name, entry);
		funcVar.put(func, map);

		HashMap<String, Type> tmap = funcVarTypes.get(func);
		tmap = (tmap == null) ? new HashMap<String, Type>() : tmap;

		tmap.put(name, type);
		funcVarTypes.put(func, tmap);
	}

	public HashMap<String, Type> getTypeMap(String func) {
		return funcVarTypes.get(func);
	}

	private VarDeclareEntry getVarTypeInFunc(String func, String t) {
		return funcVar.get(func).get(t);
	}

	public HashMap<String, HashMap<String, VarDeclareEntry>> getFuncVar() {
		return funcVar;
	}

	private VarDeclareEntry getFieldTypeInStruct(VarDeclareEntry entry, String t) {
		StructDef strt = getStruct(entry.getType());
		String origin = entry.getOrigin() + "." + t;
		VarDeclareEntry fieldEntry = getVarTypeInFunc(entry.getFunc(), origin);
		if (fieldEntry != null)
			return fieldEntry;
		HashMap<String, VarDeclareEntry> map = funcVar.get(entry.getFunc());
		VarDeclareEntry model_e = getEntryInStruct(strt.getName(), t).clone();
		model_e.setOrigin(origin);
		model_e.setFunc(entry.getFunc());
		map.put(origin, model_e);
		funcVar.put(entry.getFunc(), map);
		return model_e;
	}

	public List<VarDeclareEntry> resolveFieldChain(String func, String string) {
		String[] token = string.split("\\.");
		VarDeclareEntry current = null;
		List<VarDeclareEntry> fields = new ArrayList<VarDeclareEntry>();
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

	private VarDeclareEntry getEntryInStruct(String type, String field) {
		return fieldPerStruct.get(type).get(field);
	}

	private void genAllFieldsPerType() {
		fieldPerStruct = new HashMap<String, HashMap<String, VarDeclareEntry>>();
		for (String struct : structNamesList()) {
			StructDef def = getStruct(struct);
			if (struct.contains("@"))
				struct = struct.substring(0, struct.indexOf("@"));
			ImmutableTypedHashMap<String, Type> fieldMap = def.getFieldTypMap();
			Iterator<Entry<String, Type>> iterator = fieldMap.iterator();
			HashMap<String, VarDeclareEntry> entryList = new HashMap<String, VarDeclareEntry>();
			while (iterator.hasNext()) {
				Entry<String, Type> e = iterator.next();
				VarDeclareEntry new_e = new VarDeclareEntry(struct + "." + e.getKey(), e.getValue().toString(), null);
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

	public StringBuilder extractCandidateHoleAllS(String func, String type, int bound) {
		List<StringBuilder> sList = extractCandidateSetAsHole(func, type, bound);
		if (sList.size() == 0)
			return new StringBuilder();
		StringBuilder sb = sList.get(0);
		for (int i = 1; i < sList.size(); i++) {
			if (sList.get(i).length() == 0)
				continue;
			sb.append("|" + sList.get(i));
		}
		// System.out.println("local var solver stringbuilder extract candidates
		// "+sb);
		return sb;
	}

	public List<HashSet<String>> extractCandidateList(String func, String type, int bound) {
		HashMap<String, VarDeclareEntry> map = funcVar.get(func);
		HashMap<String, CandidateWrapper> first = new HashMap<String, CandidateWrapper>();
		List<HashMap<String, CandidateWrapper>> table = new ArrayList<HashMap<String, CandidateWrapper>>();
		List<HashSet<String>> canList = new ArrayList<HashSet<String>>();
		if (map == null)
			return canList;
		for (Map.Entry<String, VarDeclareEntry> entry : map.entrySet()) {
			String typ = entry.getValue().getTypeS();
			CandidateWrapper wp = first.get(typ);
			if (wp == null)
				wp = new CandidateWrapper(typ);
			if (!entry.getKey().contains(".")) {
				wp.addValue(entry.getKey());
				first.put(typ, wp);
			}
		}
		table.add(first);
		for (int i = 1; i < bound; i++)
			table.add(genNextLayerCandidateList(table.get(i - 1)));
		canList = genCandStringList(table, type);
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
			HashMap<String, VarDeclareEntry> fields = fieldPerStruct.get(prev_e.getKey());
			if (fields == null)
				continue;
			for (Map.Entry<String, VarDeclareEntry> fld : fields.entrySet()) {
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

	public boolean isPrimitiveType(String func, String exp) {
		List<VarDeclareEntry> list = resolveFieldChain(func, exp);
		VarDeclareEntry entry = list.get(list.size() - 1);
		if (entry == null)
			return false;
		return entry.getType() == null;
	}

	public HashSet<Expression> instantiateField(String func, String field, FENode node) {

		HashMap<String, VarDeclareEntry> varType = funcVar.get(func);
		HashSet<Expression> res = new HashSet<Expression>();
		String[] token = field.split("\\.");
		if (token.length == 0) {
			for (Map.Entry<String, VarDeclareEntry> entry : varType.entrySet()) {
				VarDeclareEntry decl = entry.getValue();
				if (decl.getName().equals(field)) {
					res.add(new ExprVar(node, decl.getOrigin()));
				}
			}
		} else {
			String typ = token[0];
			HashSet<Expression> root = new HashSet<Expression>();
			for (Map.Entry<String, VarDeclareEntry> entry : varType.entrySet()) {
				VarDeclareEntry decl = entry.getValue();
				if (decl.getTypeS().equals(typ)) {
					root.add(new ExprVar(node, decl.getOrigin()));
				}
			}
			for (Expression rt : root) {
				Expression expF = rt;
				for (int i = 1; i < token.length; i++) {
					expF = new ExprField(node, rt, token[i]);
				}
				res.add(expF);
			}
		}
		for (Expression e : res)
			System.out.println(e);
		return res;
	}

	private StringBuilder simplifiedExtractCandidateSet(String type, int bound) {
		StringBuilder builder = new StringBuilder();
		getTypeVars();

		return builder;
	}

	private void getTypeVars() {
		HashMap<String, HashMap<String, String>> funcTypeVars = new HashMap<String, HashMap<String, String>>();
		for (String func : funcVar.keySet()) {
			HashMap<String, String> typeVars = new HashMap<String, String>();
			HashMap<String, VarDeclareEntry> varType = funcVar.get(func);
			for (String key : varType.keySet()) {
				String type = varType.get(key).getTypeS();
				String vars = "";
				if (typeVars.containsKey(type))
					vars = typeVars.get(type);
				vars += key + "|";
				typeVars.put(type, vars);
			}
			HashMap<String, String> typeVarTrim = new HashMap<String, String>();
			for (String type : typeVars.keySet()) {
				String names = typeVars.get(type);
				typeVarTrim.put(type, names.substring(0, names.length() - 1));
			}
			funcTypeVars.put(func, typeVarTrim);
		}
	}

	public HashSet<String> getStructNames() {
		return allStructs;
	}

}
