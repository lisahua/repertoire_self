/**
 * @author Lisa Mar 19, 2016 ProgramRecord.java 
 */
package sketch.compiler.bugLocator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.CandidateGenerator.SketchHoleGenerator;
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
import sketch.compiler.main.other.RepairSketchOptions;
import sketch.util.datastructures.ImmutableTypedHashMap;

public class RepairProgramUtility {
	private HashMap<Function, HashMap<String, Type>> funcVarType = new HashMap<Function, HashMap<String, Type>>();
	private HashMap<Function, List<StmtAssert>> funcAssertMap = new HashMap<Function, List<StmtAssert>>();
	private HashMap<Function, List<ExprFunCall>> funcCallMap = new HashMap<Function, List<ExprFunCall>>();
	private HashMap<Function, List<StmtAssign>> assignMap = new HashMap<Function, List<StmtAssign>>();
	private HashMap<String, String> fileFixMap = null;
	private NameResolver nRes;
	private RepairSketchOptions options;

	public RepairProgramUtility(Program prog, final RepairSketchOptions options) {
		initProgram(prog);
		nRes = new NameResolver(prog);
		this.options = options;
	}

	private void initProgram(Program prog) {
		for (Package pkg : prog.getPackages()) {

		for (Function func : pkg.getFuncs()) {
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
	}

	public List<String> startRepair(String message) {
		FailAssertHandler failHandler = new FailAssertHandler(this);
		StmtAssert failAssert = failHandler.findBuggyAssertion(message);
		if (failAssert == null) {
			System.out.println("Cannot identify failing assertion! Repair stop.");
			return null;
		}
		AssertionLocator assertLocator = new AssertionLocator(failHandler.getAllAsserts());
		List<StmtAssert> asserts = assertLocator.findAllAsserts(failHandler.getFailField());

		SuspiciousFieldCollector suspLocator = new SuspiciousFieldCollector(this);
		suspLocator.findAllFieldsInMethod(failHandler.getFailField(), failHandler.getBuggyHarness());

		SketchHoleGenerator holeGenerator = new SketchHoleGenerator(this);
		List<String> files = holeGenerator.runSketch(suspLocator.getSuspciousAssign());

		fileFixMap = holeGenerator.getFixPerFile();
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
//		if (type.contains("@"))
//			type = type.substring(0, type.indexOf("@"));
		StructDef strt = nRes.getStruct(type);
//		StructDef strt = structMap.get(type);
		ImmutableTypedHashMap<String, Type> fieldMap = strt.getFieldTypMap();
		return fieldMap.get(field);
	}

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

	public HashMap<String, String> getFixPerFile() {
		return fileFixMap;
	}

	public String getSketchFile() {
		return options.args[0];
	}

}
