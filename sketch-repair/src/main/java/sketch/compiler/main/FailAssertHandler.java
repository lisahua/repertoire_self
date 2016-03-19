/**
 * @author Lisa Mar 18, 2016 RepairTypeResolver.java 
 */
package sketch.compiler.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.bugLocator.RepairFEFuncVisitor;
import sketch.util.datastructures.ImmutableTypedHashMap;

public class FailAssertHandler {

	HashMap<String, StructDef> structMap = new HashMap<String, StructDef>();
	FailAssertRecord failAssertInstance = null;
	RepairFEFuncVisitor visitor = null;
	HashMap<String, Function> funcMap = new HashMap<String, Function>();
	HashMap<String, Type> varTypeMap = new HashMap<String, Type>();
	HashMap<StmtAssert,List<String>> fieldAssertMap = new HashMap<StmtAssert,List<String>>();
//	List<String> suspFields = new ArrayList<String>();
	public FailAssertHandler() {

	}

	public StmtAssert findFailAssert(Program prog, String failAssList) {
		System.out.println("=====fail assert ====" + failAssList);
		for (Package pkg : prog.getPackages()) {
			for (Function func : pkg.getFuncs())
				funcMap.put(func.getName(), func);
			for (StructDef struct : pkg.getStructs()) {
				structMap.put(struct.getName(), struct);

			}
		}

		for (Function func : funcMap.values()) {
			// RepairFEVisitor visitor = new RepairFEVisitor();
			RepairFEFuncVisitor visitor = new RepairFEFuncVisitor();
			prog.accept(visitor);
			ArrayList<StmtAssert> asserts = visitor.getAsserts();
			System.out.println("=======Function " + func.getName() + "=============");
			for (StmtAssert ass : asserts) {
				fieldAssertMap.put(ass, findFailAssertTypeField(ass));
				if ((ass.getCx().toString().trim()).equals(failAssList)) {

					System.out.println("====Failure found at =====");
					System.out.println(ass.getMsg() + "," + ass.getCx() + "," + ass.toString());
					failAssertInstance = new FailAssertRecord(visitor);
					this.visitor = visitor;
					findFailAssertTypeField(ass);
					for (StmtVarDecl var : visitor.getVarDecl()) {
						varTypeMap.put(var.getName(0), var.getType(0));
					}
					return ass;
				}
				
				
			}
		}
		return null;
	}

	private List<String> findFailAssertTypeField(StmtAssert ass) {
		String[] token = ass.toString().split("=");
		String lhs = token[0].replace("(", "").replace(")", "").trim();
		String rhs = token[1].replace("(", "").replace(")", "").trim();
		List<String> suspFields = new ArrayList<String>();
		if (lhs.contains("\\."))
			suspFields.addAll(resolveFieldChain(lhs));
		if (rhs.contains("\\."))
			suspFields.addAll(resolveFieldChain(lhs));
		return suspFields;
	}

	private List<String> resolveFieldChain(String string) {
		String[] token = string.split("\\.");
		Type current = null;
		String tmp = "";
		List<String> fieldType = new ArrayList<String>();
		for (String t : token) {
			if (current == null) {
				current = varTypeMap.get(t);
			} else {
				current = getType(current.toString(), t);
				varTypeMap.put(tmp + "." + t, current);
			}
			tmp = current.toString();
			fieldType.add(tmp);
		}
		return fieldType;
	}

	private Type getType(String type, String field) {
		StructDef strt = structMap.get(type);
		ImmutableTypedHashMap<String, Type> fieldMap = strt.getFieldTypMap();
		return fieldMap.get(field);
	}

	public void generateNewSketch(Program prog, String failAssList) {
		StmtAssert findFailAssert = findFailAssert(prog,failAssList);
		if (findFailAssert==null) {
			System.out.println("Cannot identify failing assertion! Repair stop.");
			return;
		}
		findAllAsserts();
		findAllFieldsInMethod();
		generateSketchHole();
		generateHelperMethod();// no necessary
	}

	private void findAllAsserts() {
		
	}

	private void findAllFieldsInMethod() {
		HashSet<Function> functions = findAllSuspiciousMethod();

	}

	private HashSet<Function> findAllSuspiciousMethod() {
		ArrayList<ExprFunCall> funCall = visitor.getFunCall();
		HashSet<Function> funSet = new HashSet<Function>();
		for (ExprFunCall call : funCall) {
			Function func = funcMap.get(call.getName());
			if (func != null)
				funSet.add(func);
		}
		return funSet;
	}

	private void generateSketchHole() {

	}

	private void generateHelperMethod() {

	}

}
