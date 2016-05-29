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

	public BlockNameResolver(Program prog, int bound) {
		resolver = new NameResolver(prog);
		initBlockModel(prog);
		this.bound = bound;
		// dereference = getDereference(prog, bound);
	}

	private void initBlockModel(Program prog) {
		List<BlockNameResolverModel> blockName = new ArrayList<BlockNameResolverModel>();
		for (sketch.compiler.ast.core.Package pkg : prog.getPackages()) {
			for (FieldDecl field : pkg.getVars()) {
				BlockNameResolverModel model = new BlockNameResolverModel("", field.getName(0), field.getType(0), -1,
						-1);
				blockName.add(model);
			}

			for (Function func : pkg.getFuncs()) {
				count = 0;
				blocks = new TreeMap<Integer, Integer>();
				List<BlockNameResolverModel> varSet = recurGetName(func.getName(), func.getBody(),
						new ArrayList<BlockNameResolverModel>());
				funcNames.put(func.getName().substring(0, func.getName().indexOf("@")), varSet);
				for (BlockNameResolverModel model : varSet) {
					System.out.println("func " + func.getName() + "," + model.toString());
				}
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
			BlockNameResolverModel model = new BlockNameResolverModel(func, decl.getName(0), decl.getType(0),
					blocks.lastKey(), count);
			blockName.add(model);
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

	public HashMap<Type, HashSet<BlockNameResolverModel>> getVar(String func,  int loc) {
		HashMap<Type, HashSet<BlockNameResolverModel>> names = new HashMap<Type, HashSet<BlockNameResolverModel>>();
		for (BlockNameResolverModel model : funcNames.get(func)) {
				if (model.start <= loc && model.end >= loc) {
					HashSet<BlockNameResolverModel> tmp = names.get(model.type);
					if (tmp == null)
						tmp = new HashSet<BlockNameResolverModel>();
					tmp.add(model);
				}
		}
		return names;
	}

	public StringBuilder getAllCandidates(String func, Type type, int loc) {
		StringBuilder builder = new StringBuilder();
		HashMap<Type, HashSet<BlockNameResolverModel>> vars = getVar(func,  loc);
		HashMap<Type, HashSet<String>> names = new HashMap<Type, HashSet<String>>();
		for (Type varType : vars.keySet()) {
			// TEST resolver.getStruct
			HashSet<String> typeNames = new HashSet<String>();
			for (BlockNameResolverModel name : vars.get(varType)) {
				typeNames.add(name.name);
			}
			names.put(varType, typeNames);
			for (int i = 0; i < bound; i++) {
				for (StructFieldEnt field : resolver.getStruct(varType.toString()).getFieldEntries()) {
					HashSet<String> allName = names.get(field.getType());
					if (allName == null)
						allName = new HashSet<String>();
					for (String name : typeNames) {
						String derivedName = name + "." + field.getName();
						allName.add(derivedName);
					}
					names.put(field.getType(), allName);
				}
			}
		}
		for (String name : names.get(type)) {
			builder.append("|" + name);
		}
		builder.append("|");
		return builder;
	}

	@Deprecated
	private HashMap<Type, List<FieldWrapper>> getDereference(Program prog, int bound) {
		HashMap<Type, List<FieldWrapper>> destMap = new HashMap<Type, List<FieldWrapper>>();
		for (sketch.compiler.ast.core.Package pkg : prog.getPackages()) {

			for (StructDef struct : pkg.getStructs()) {
				List<FieldWrapper> list = new ArrayList<FieldWrapper>();
				Type strType = typeBank.get(struct.getName());
				list.add(new FieldWrapper(strType, strType, strType.toString(), 0));
				destMap.put(strType, list);
				for (int i = 0; i < bound; i++) {
					int size = list.size();
					for (int j = 0; j < size; j++) {
						FieldWrapper wrapper = list.get(j);
						StructDef strt = resolver.getStruct(wrapper.destination.toString());
						if (strt == null)
							continue;
						for (StructFieldEnt field : strt.getFieldEntries()) {
							List<FieldWrapper> typeList = destMap.get(field.getType());
							if (typeList == null)
								typeList = new ArrayList<FieldWrapper>();
							FieldWrapper wrap = new FieldWrapper(strType, field.getType(), field.getName(), i + 1);
							typeList.add(wrap);
							destMap.put(field.getType(), typeList);
							list.add(wrap);
						}
					}
				}
			}

		}
		return destMap;
	}

}
