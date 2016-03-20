/**
 * @author Lisa Mar 19, 2016 ProgramRecord.java 
 */
package sketch.compiler.bugLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Package;
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
//	private HashMap<StructDef, ImmutableTypedHashMap<String, Type>> structFieldType = new HashMap<StructDef, ImmutableTypedHashMap<String, Type>>();
	private HashMap<Function, HashMap<String, Type>> funcVarType = new HashMap<Function, HashMap<String, Type>>();
	private HashMap<Function, List<StmtAssert>> funcAssertMap = new HashMap<Function, List<StmtAssert>>();
	private HashMap<Function, List<ExprFunCall>> funcCallMap = new HashMap<Function, List<ExprFunCall>>();
	private HashMap<Function, List<StmtAssign>> assignMap = new HashMap<Function, List<StmtAssign>>();

	public RepairProgramUtility(Program prog, String message) {
		// TODO Auto-generated constructor stub
		initProgram(prog);
	}

	private void initProgram(Program prog) {
		for (Package pkg : prog.getPackages()) {
			for (Function func : pkg.getFuncs())
				funcMap.put(func.getName(), func);
			for (StructDef struct : pkg.getStructs()) {
				structMap.put(struct.getName(), struct);
//				structFieldType.put(struct, struct.getFieldTypMap());
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
			funcVarType.put(func, varTypeMap);

			assignMap.put(func, visitor.getStmtAssign());

		}
	}

	public void startRepair(String message) {
		FailAssertHandler failHandler = new FailAssertHandler(this);
		StmtAssert failAssert = failHandler.findBuggyAssertion(message);
		if (failAssert == null) {
			System.out.println("Cannot identify failing assertion! Repair stop.");
			return;
		}

		AssertionLocator assertLocator = new AssertionLocator(failHandler.getAllAsserts());
		List<StmtAssert> asserts = assertLocator.findAllAsserts(failHandler.getFailField());

		SuspiciousFieldLocator suspLocator = new SuspiciousFieldLocator(this);
		List<StmtAssign> assigns = suspLocator.findAllFieldsInMethod(failHandler.getFailField(), failHandler.getBuggyHarness());

		
	}

	public List<String> resolveFieldChain(Function func, String string) {
		HashMap<String, Type> varType = funcVarType.get(func);
		String[] token = string.split("\\.");
		Type current = null;
		String tmp = "";
		List<String> fieldType = new ArrayList<String>();

		for (String t : token) {
			if (current == null) {
				current = varType.get(t);
			} else {
				current = getType(current.toString(), t);
				varType.put(tmp + "." + t, current);
			}
			tmp = current.toString();
			fieldType.add(tmp);
		}
		return fieldType;
	}

	private  Type getType(String type, String field) {
		StructDef strt = structMap.get(type);
		ImmutableTypedHashMap<String, Type> fieldMap = strt.getFieldTypMap();
		return fieldMap.get(field);
	}

	public HashMap<String, StructDef> getStructMap() {
		return structMap;
	}

	public HashMap<String, Function> getFuncMap() {
		return funcMap;
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

}
