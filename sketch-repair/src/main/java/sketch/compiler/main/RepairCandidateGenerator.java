/**
 * @author Lisa Mar 9, 2016 RepairCandidateGenerator.java 
 */
package sketch.compiler.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprLocalVariables;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.bugLocator.AssertionLocator;
import sketch.compiler.bugLocator.RepairFEVisitor;
import sketch.util.datastructures.ImmutableTypedHashMap;
import sketch.util.exceptions.SketchException;
import sketch.util.exceptions.SketchNotResolvedException;

public class RepairCandidateGenerator {
	private List<Function> functs = new ArrayList<Function>();
	private List<StmtAssert> assertions = new ArrayList<StmtAssert>();
	private List<StmtAssert> failAsserts = new ArrayList<StmtAssert>();
	private List<Function> failFunc = new ArrayList<Function>();

	public void generateCandidaite(Program prog, SketchException se, File file) {
		if (!(se instanceof SketchNotResolvedException)) {
			System.out.println("Repair-Sketch requires a SketchNotResolvedException to start with");
			return;
		}
		System.out.println("====================Repair starts ======================");
		String failAssList = findSuspicious(se.getMessage(), file);
		parseProgram(prog, failAssList);

		System.out.println("====================Repair Ends ======================");

	}

	private void parseProgram(Program prog, String failAssList) {
		for (Package pkg : prog.getPackages()) {
			functs.addAll(pkg.getFuncs());
		}
		StmtAssert failAss = null;// TODO take out for recursion later
		RepairFEVisitor visitor = new RepairFEVisitor();
		prog.accept(visitor);
		for (Function func : functs) {
			// RepairFEVisitor visitor = new RepairFEVisitor();
			func.accept(visitor);
			System.out.println("=======Function " + func.getFullName() + "=============");

			ArrayList<StmtAssert> asserts = visitor.getAsserts();
			ArrayList<StmtVarDecl> varDecl = visitor.getVars();
			ArrayList<StructDef> structDef = visitor.getStructDef();
			for (StmtAssert ass : asserts) {
				if ((ass.getCx().toString().trim()).equals(failAssList)) {
					System.out.println("====Failure found at =====");
					System.out.println(ass.getMsg() + "," + ass.getCx() + "," + ass.toString());
					failAss = ass;
				}
			}
			for (StmtVarDecl ass : varDecl) {
				System.out.println(ass.getName(0) + "," + ass.getType(0) + "," + ass.getCx() + ","
						+ ass.getOrigin().toString() + "," + ass.toString());

			}

			for (StructDef ass : structDef) {
				System.out.println("======Struct " + ass.getName() + "==========");
				ImmutableTypedHashMap<String, Type> fieldMap = ass.getFieldTypMap();
				for (String s : fieldMap.keySet())
					System.out.println(s + "," + fieldMap.get(s));

			}
		}
	}

	private String findSuspicious(String msg, File file) {
		AssertionLocator assLocator = new AssertionLocator();
		String failAssert = assLocator.findBuggyAssertion(msg, file);
		return failAssert;
	}

}
