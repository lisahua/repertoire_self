/**
 * @author Lisa Mar 19, 2016 ProgramRecord.java 
 */
package sketch.compiler.bugLocator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.ProgramLocator.SketchHoleGenerator;
import sketch.compiler.ProgramLocator.SuspiciousFieldCollector;
import sketch.compiler.assertionLocator.AssertionLocator;
import sketch.compiler.assertionLocator.FailAssertHandler;
import sketch.compiler.assertionLocator.FieldWrapper;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.NameResolver;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.Type;
import sketch.util.datastructures.ImmutableTypedHashMap;

public class RepairProgramUtility {

	private HashMap<String, StructDef> structMap = new HashMap<String, StructDef>();
	private HashMap<String, Function> funcMap = new HashMap<String, Function>();
	// private HashMap<StructDef, ImmutableTypedHashMap<String, Type>>
	// structFieldType = new HashMap<StructDef, ImmutableTypedHashMap<String,
	// Type>>();
	private HashMap<Function, HashMap<String, Type>> funcVarType = new HashMap<Function, HashMap<String, Type>>();
	private HashMap<Function, List<StmtAssert>> funcAssertMap = new HashMap<Function, List<StmtAssert>>();
	private HashMap<Function, List<ExprFunCall>> funcCallMap = new HashMap<Function, List<ExprFunCall>>();
	private HashMap<Function, List<StmtAssign>> assignMap = new HashMap<Function, List<StmtAssign>>();
	private String outputFile = "";
	private HashMap<String, String> fileFixMap = null;
private NameResolver nRes;
	public RepairProgramUtility(Program prog, String message) {
		// TODO Auto-generated constructor stub
		initProgram(prog);
		nRes = new NameResolver(prog);
	}

	private void initProgram(Program prog) {
		for (Package pkg : prog.getPackages()) {
			for (Function func : pkg.getFuncs())
				funcMap.put(func.getName(), func);
			for (StructDef struct : pkg.getStructs()) {
				structMap.put(struct.getName(), struct);
				// structFieldType.put(struct, struct.getFieldTypMap());
			}
		}

		for (Function func : funcMap.values()) {
			// RepairFEVisitor visitor = new RepairFEVisitor();
			RepairFEFuncVisitor visitor = new RepairFEFuncVisitor();
			func.accept(visitor);
			funcAssertMap.put(func, visitor.getAsserts());
			funcCallMap.put(func, visitor.getFunCall());

			HashMap<String, Type> varTypeMap = new HashMap<String, Type>();
			for (StmtVarDecl var : visitor.getVarDecl()) {
				varTypeMap.put(var.getName(0), var.getType(0));
			}
			for (Parameter para : visitor.getParameter())
				varTypeMap.put(para.getName(), para.getType());
			funcVarType.put(func, varTypeMap);
			assignMap.put(func, visitor.getStmtAssign());

		}
	}

	public List<String> startRepair(String message, File file) {
		FailAssertHandler failHandler = new FailAssertHandler(this);
		StmtAssert failAssert = failHandler.findBuggyAssertion(message);
		if (failAssert == null) {
			System.out.println("Cannot identify failing assertion! Repair stop.");
			return null;
		}
		outputFile = file.getAbsolutePath() + ".tmp";
		AssertionLocator assertLocator = new AssertionLocator(failHandler.getAllAsserts());
		List<StmtAssert> asserts = assertLocator.findAllAsserts(failHandler.getFailField());

		SuspiciousFieldCollector suspLocator = new SuspiciousFieldCollector(this);
		suspLocator.findAllFieldsInMethod(failHandler.getFailField(), failHandler.getBuggyHarness());

		SketchHoleGenerator holeGenerator = new SketchHoleGenerator(this);
		List<String> files = holeGenerator.runSketch(suspLocator.getSuspciousAssign(), file);
		
		fileFixMap = holeGenerator.getFixPerFile();
//		for (String key : fileFixMap.keySet())
//			System.out.println("Utility "+key + "," + fileFixMap.get(key));
		return files;
	}

	public List<FieldWrapper> resolveFieldChain(Function func, String string) {
		HashMap<String, Type> varType = funcVarType.get(func);
		String[] token = string.split("\\.");
		Type current = null;
		String tmp = "";
		String fieldName = "";
		List<FieldWrapper> fields = new ArrayList<FieldWrapper>();
		for (String t : token) {
			t = t.trim();
			if (t.length() == 0)
				continue;
			if (current == null) {
				current = varType.get(t);
			} else {
				current = getType(current.toString(), t);
				varType.put(tmp + "." + t, current);
				fieldName = tmp + "." + t;
			}
			tmp = current.toString();
			if (tmp.contains("@"))
				tmp = tmp.substring(0, tmp.indexOf("@"));
			fields.add(new FieldWrapper(fieldName, current));
		}
		return fields;
	}

	public Type resolveFieldType(Function func, String string) {
		HashMap<String, Type> varType = funcVarType.get(func);
		String[] token = string.split("\\.");
		Type current = null;

		for (String t : token) {
			if (current == null) {
				current = varType.get(t);
			} else {
				current = getType(current.toString(), t);
			}
		}
		return current;
	}

	private Type getType(String type, String field) {
		if (type.contains("@"))
			type = type.substring(0, type.indexOf("@"));
		StructDef strt = structMap.get(type);
		ImmutableTypedHashMap<String, Type> fieldMap = strt.getFieldTypMap();
		return fieldMap.get(field);
	}

//	public HashMap<String, StructDef> getStructMap() {
//		return structMap;
//	}
//
//	public HashMap<String, Function> getFuncMap() {
//		return funcMap;
//	}

	
	public Function getFuncMap(String name) {
		return nRes.getFun(name);
	}
		
	public HashMap<Function, HashMap<String, Type>> getFuncVarType() {
		return funcVarType;
	}

	public HashMap<Function, List<StmtAssert>> getFuncAssertMap() {
		return funcAssertMap;
	}

	public HashMap<Function, List<ExprFunCall>> getFuncCallMap() {
		return funcCallMap;
	}

	public HashMap<Function, List<StmtAssign>> getAssignMap() {
		return assignMap;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public HashMap<String, String> getFixPerFile() {
		return fileFixMap;
	}

}
