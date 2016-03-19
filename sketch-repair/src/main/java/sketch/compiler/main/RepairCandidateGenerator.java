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
import sketch.compiler.bugLocator.RepairFEFuncVisitor;
import sketch.util.datastructures.ImmutableTypedHashMap;
import sketch.util.exceptions.SketchException;
import sketch.util.exceptions.SketchNotResolvedException;

public class RepairCandidateGenerator {

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
		FailAssertHandler failAssertHandler  = new FailAssertHandler();
		
		failAssertHandler.generateNewSketch(prog,failAssList);
	}

	private String findSuspicious(String msg, File file) {
		AssertionLocator assLocator = new AssertionLocator();
		String failAssert = assLocator.findBuggyAssertion(msg, file);
		return failAssert;
	}

}
