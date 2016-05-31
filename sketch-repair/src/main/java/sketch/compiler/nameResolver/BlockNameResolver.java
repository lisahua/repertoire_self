/**
 * @author Lisa May 19, 2016 BlockNameResolver.java 
 */
package sketch.compiler.nameResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import sketch.compiler.ast.core.FieldDecl;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.NameResolver;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtFor;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.stmts.StmtWhile;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.StructDef.StructFieldEnt;
import sketch.compiler.ast.core.typs.Type;

public class BlockNameResolver {
	HashMap<String, List<BlockNameResolverModel>> funcNames = new HashMap<String, List<BlockNameResolverModel>>();
	NameResolver resolver = null;
	TreeMap<Integer, Integer> blocks = null;
	int count = 0;
	// this is hacky
	HashMap<String, Type> typeBank = new HashMap<String, Type>();
	HashMap<Type, HashSet<String>> nameBank = new HashMap<Type, HashSet<String>>();
	private final int bound;
	QueryRecorder queryRecorder = new QueryRecorder();
	HashMap<Type, List<FieldWrapper>> dereference = null;

	public BlockNameResolver(Program prog, int bound) {
		resolver = new NameResolver(prog);
		initBlockModel(prog);
		this.bound = bound;
		dereference = getDereference(prog, bound);
	}

	private void initBlockModel(Program prog) {
		List<BlockNameResolverModel> blockName = new ArrayList<BlockNameResolverModel>();
		for (sketch.compiler.ast.core.Package pkg : prog.getPackages()) {
			for (FieldDecl field : pkg.getVars()) {
				if (field.getName(0).startsWith("_"))
					continue;
				BlockNameResolverModel model = new BlockNameResolverModel("", field.getName(0), field.getType(0), -1,
						-1);
				blockName.add(model);
			}
			funcNames.put("", blockName);
			for (Function func : pkg.getFuncs()) {
				blockName = new ArrayList<BlockNameResolverModel>();
				if (func.isSketchHarness())
					continue;
				String funcNS = func.getName();
				if (funcNS.contains("@"))
					funcNS = func.getName().substring(0, func.getName().indexOf("@"));
				for (Parameter para : func.getParams()) {
					//FIXME may be error because return value is not set.
					if (para.getName().startsWith("_"))
						continue;
					BlockNameResolverModel model = new BlockNameResolverModel(funcNS, para.getName(), para.getType(),
							-1, -1);
					blockName.add(model);
				}

				count = 0;
				blocks = new TreeMap<Integer, Integer>();
				List<BlockNameResolverModel> varSet = recurGetName(func.getName(), func.getBody(), blockName);
				funcNames.put(funcNS, varSet);
				// for (BlockNameResolverModel model : varSet) {
				// System.out.println("func " + func.getName() + "," +
				// model.toString());
				// }
			}
		}
	}

	private List<BlockNameResolverModel> recurGetName(String func, Statement stmt,
			List<BlockNameResolverModel> blockName) {
		if (stmt instanceof StmtBlock) {

			int start = count;
			blocks.put(start, -1);
			List<Statement> stmts = ((StmtBlock) stmt).getStmts();
			for (Statement line : stmts)
				recurGetName(func, line, blockName);
			for (BlockNameResolverModel md : blockName) {
				if (md.start == start)
					md.end = count;
			}
			blocks.put(start, count);
		} else if (stmt instanceof StmtVarDecl) {
			StmtVarDecl decl = (StmtVarDecl) stmt;
			if (!decl.getName(0).startsWith("_")) {
				BlockNameResolverModel model = new BlockNameResolverModel(func, decl.getName(0), decl.getType(0),
						blocks.lastKey(), count);
				blockName.add(model);
			}
			// String typeNS = decl.getType(0).toString();
			// if (typeNS.contains("@"))
			// typeNS = typeNS.substring(0, typeNS.indexOf("@"));
			// typeBank.put(typeNS, decl.getType(0));
			typeBank.put(decl.getType(0).toString(), decl.getType(0));
			count++;
		} else if (stmt instanceof StmtWhile) {
			count++;
			recurGetName(func, ((StmtWhile) stmt).getBody(), blockName);
		} else if (stmt instanceof StmtFor) {
			count++;
			recurGetName(func, ((StmtFor) stmt).getBody(), blockName);
		} else if (stmt instanceof StmtIfThen) {
			count++;
			recurGetName(func, ((StmtIfThen) stmt).getCons(), blockName);
			recurGetName(func, ((StmtIfThen) stmt).getAlt(), blockName);
		}

		else
			count++;
		return blockName;
	}

	public HashMap<Type, HashSet<BlockNameResolverModel>> getVar(String func, int loc) {
		HashMap<Type, HashSet<BlockNameResolverModel>> names = new HashMap<Type, HashSet<BlockNameResolverModel>>();
		// field
		if (funcNames.get("") != null) {
			for (BlockNameResolverModel model : funcNames.get("")) {
				HashSet<BlockNameResolverModel> tmp = names.get(model.type);
				if (tmp == null)
					tmp = new HashSet<BlockNameResolverModel>();
				tmp.add(model);
				names.put(model.type, tmp);
			}
		}
		for (BlockNameResolverModel model : funcNames.get(func)) {
			HashSet<BlockNameResolverModel> tmp = names.get(model.type);
			if (model.start == -1 || (model.start <= loc && model.end >= loc)) {
				if (tmp == null)
					tmp = new HashSet<BlockNameResolverModel>();
				tmp.add(model);
				names.put(model.type, tmp);
			}
		}

		return names;
	}

	public StringBuilder getAllCandidates(String func, String typeS, int loc) {

		Type strType = typeBank.get(typeS);
		if (strType == null && !typeS.contains("@"))
			strType = typeBank.get(typeS + "@ANONYMOUS");
		System.out.println("[input type] " + typeS + "," + strType);
		StringBuilder builder = queryRecorder.getRecord(func, loc, strType);
		if (builder != null)
			return builder;
		builder = new StringBuilder();
		HashMap<Type, HashSet<BlockNameResolverModel>> vars = getVar(func, loc);
		HashMap<Type, HashSet<String>> names = new HashMap<Type, HashSet<String>>();
		for (Type varType : vars.keySet()) {
			// TEST resolver.getStruct
			HashSet<String> typeNames = new HashSet<String>();
			for (BlockNameResolverModel name : vars.get(varType)) {
				typeNames.add(name.name);
			}
			names.put(varType, typeNames);
		}
		HashSet<String> nSet = names.get(strType);
		if (nSet == null)
			nSet = new HashSet<String>();
		if (dereference.get(strType) == null) {
			System.out.println("[dereference no strType] " + strType);
			return builder;
		}
		for (FieldWrapper wrap : dereference.get(strType)) {
			if (vars.get(wrap.origin) == null)
				continue;
			for (BlockNameResolverModel model : vars.get(wrap.origin)) {
				nSet.add(model.name + "." + wrap.name);
			}
		}
		if (nSet.size() == 0) {
			return builder;
		}
		for (String name : nSet) {
			builder.append(name + "|");
		}
		StringBuilder trim = new StringBuilder(builder.substring(0, builder.length() - 1));
		return trim;

	}

	@Deprecated
	public StringBuilder getAllCandidates_old(String func, String typeS, int loc) {
		Type type = null;
		for (String ts : typeBank.keySet()) {
			if (ts.contains(typeS))
				type = typeBank.get(ts);
		}

		StringBuilder builder = queryRecorder.getRecord(func, loc, type);
		if (builder != null)
			return builder;
		builder = new StringBuilder();
		HashMap<Type, HashSet<BlockNameResolverModel>> vars = getVar(func, loc);
		HashMap<Type, HashSet<String>> names = new HashMap<Type, HashSet<String>>();
		for (Type varType : vars.keySet()) {
			// TEST resolver.getStruct
			HashSet<String> typeNames = new HashSet<String>();
			for (BlockNameResolverModel name : vars.get(varType)) {
				typeNames.add(name.name);
			}
			names.put(varType, typeNames);
			for (int i = 0; i < bound - 1; i++) {
				if (!varType.isStruct()) {
					continue;
				}
				HashMap<Type, HashSet<String>> nClone = (HashMap<Type, HashSet<String>>) names.clone();
				for (StructFieldEnt field : resolver.getStruct(varType.toString()).getFieldEntries()) {
					HashSet<String> allName = null;
					Type fieldT = field.getType();
					for (Type t : nClone.keySet()) {
						if (t.toString().contains(field.getType().toString())
								|| field.getType().toString().contains(t.toString())) {
							allName = nClone.get(t);
							fieldT = t;
							break;
						}
					}
					if (allName == null)
						allName = new HashSet<String>();

					for (String name : (HashSet<String>) allName.clone()) {
						String derivedName = name + "." + field.getName();
						allName.add(derivedName);
					}
					names.put(fieldT, allName);
				}
			}
		}
		for (Type t : names.keySet())
			System.out.println("[has field dereference]" + type + "," + t + "," + names.get(t));
		if (!names.containsKey(type)) {

			return builder;
		}
		for (

		String name : names.get(type))

		{
			builder.append("|" + name);
		}
		builder.append("|");
		return builder;

	}

	private HashMap<Type, List<FieldWrapper>> getDereference(Program prog, int bound) {
		HashMap<Type, List<FieldWrapper>> destMap = new HashMap<Type, List<FieldWrapper>>();
		for (sketch.compiler.ast.core.Package pkg : prog.getPackages()) {
			for (StructDef struct : pkg.getStructs()) {
				Type strType = typeBank.get(struct.getName());
				if (!struct.getName().contains("@"))
					strType = typeBank.get(struct.getName() + "@ANONYMOUS");
				// list.add(new FieldWrapper(strType, strType,
				// strType.toString(), 0));
				// destMap.put(strType, list);
				StructDef strt = resolver.getStruct(strType.toString());
				if (strt == null)
					continue;
				for (StructFieldEnt field : strt.getFieldEntries()) {
					Type destType = typeBank.get(field.getType().toString());
					if (!field.getType().toString().contains("@"))
						destType = typeBank.get(field.getType().toString() + "@ANONYMOUS");
					if (destType == null) {
						destType = field.getType();
						// FIXME Hacky
						typeBank.put(field.getType().toString(), field.getType());
					}
					List<FieldWrapper> typeList = destMap.get(destType);
					if (typeList == null)
						typeList = new ArrayList<FieldWrapper>();

					FieldWrapper wrap = new FieldWrapper(strType, destType, field.getName(), 0);
					typeList.add(wrap);
					destMap.put(destType, typeList);
					// System.out.println("[dest wrap] " + typeList);
				}
			}
		}
		// combine fields;
		HashMap<Type, List<FieldWrapper>> combinedMap = new HashMap<Type, List<FieldWrapper>>();
		for (Type type : destMap.keySet()) {
			// System.out.println("[map wrap] " + type + "," +
			// destMap.get(type));
			List<FieldWrapper> list = new ArrayList<FieldWrapper>();
			for (FieldWrapper wrap : destMap.get(type)) {
				for (int j = bound; j > -1; j--) {
					if (destMap.get(wrap.origin) == null) {
						// System.out.println("[origin ] "
						// +destMap.keySet()+","+ wrap);
						continue;
					}
					for (FieldWrapper wp : destMap.get(wrap.origin)) {
						FieldWrapper newW = new FieldWrapper(wp.origin, wrap.destination, wp.name + "." + wrap.name,
								wp.degree);
						list.add(newW);
						// System.out.println("[combine wrap] " + newW);
					}
				}
			}
			combinedMap.put(type, list);
		}
		return combinedMap;
	}
}
