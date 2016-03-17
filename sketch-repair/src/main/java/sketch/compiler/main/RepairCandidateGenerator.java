/**
 * @author Lisa Mar 9, 2016 RepairCandidateGenerator.java 
 */
package sketch.compiler.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtSpAssert;
import sketch.compiler.bugLocator.AssertionLocator;
import sketch.compiler.bugLocator.SuspiciousVisitor;
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
		String failAssList = findSuspicious(se, file);
		parseProgram(prog, failAssList);

		System.out.println("====================Repair Ends ======================");

	}

	private void parseProgram(Program prog, String failAssList) {
		for (Package pkg : prog.getPackages()) {
			functs.addAll(pkg.getFuncs());
		}
		SuspiciousVisitor visitor = new SuspiciousVisitor();
		for (Function func : functs) {
			func.getBody().accept(visitor);
//			if (func.isSketchHarness()) {
				func.accept(visitor);
			
				System.out.println(func.getName()+","+func.isSketchHarness());
				for (StmtAssert ass : visitor.getAsserts()) {
					System.out.println(ass.getOrigin().toString());
					if (ass.toString().contains(failAssList)) {
						// assertions.clear();
						assertions.addAll(visitor.getAsserts());
						failAsserts.add(ass);
					}
				}
				failFunc.add(func);
//			}
		}

		System.out
				.println("parse program: assertion " + assertions.size() + " failing assertion  " + failAsserts.get(0));
	}

	private String findSuspicious(SketchException se, File file) {
		AssertionLocator assLocator = new AssertionLocator();
		String failAssert = assLocator.findBuggyAssertion(se.getMessage(), file);
		return failAssert;
	}

}
