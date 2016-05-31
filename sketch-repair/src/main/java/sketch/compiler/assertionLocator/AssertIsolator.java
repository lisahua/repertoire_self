/**
 * @author Lisa May 19, 2016 AssertIsolator.java 
 */
package sketch.compiler.assertionLocator;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.main.other.RepairSketchOptions;
import sketch.compiler.main.other.RepairStageRunner;
import sketch.compiler.main.other.SimpleSketchFilePrinter;

public class AssertIsolator extends FEReplacer {
	HashSet<StmtAssert> assList = new HashSet<StmtAssert>();
	HashSet<StmtAssert> correctList = new HashSet<StmtAssert>();
	HashSet<StmtAssert> failList = new HashSet<StmtAssert>();
	private RepairSketchOptions options = null;
	private AssertReplacer replacer = new AssertReplacer();

	public AssertIsolator(RepairSketchOptions options) {
		this.options = options;
	}

	private String solveSketch(Program prog) {
		String path = options.sketchName + "_ass";
		try {
			new SimpleSketchFilePrinter(path).visitProgram(prog);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new RepairStageRunner(options).solveSketch(path);
	}

	public void checkEachAssert(Program prog) {
		this.visitProgram(prog);
		for (StmtAssert ass : assList) {
			correctList.add(ass);
			replacer.setStmtAsserts(correctList);
			Program newProg = (Program) replacer.visitProgram(prog);
			String res = solveSketch(newProg);
			if (!res.equals(""))
				correctList.remove(ass);
		}
		for (StmtAssert ass : assList) {
			if (correctList.contains(ass))
				continue;
			failList.add(ass);
		}
	}

	public Object visitStmtAssert(StmtAssert stmtAss) {
		assList.add(stmtAss);
		return super.visitStmtAssert(stmtAss);
	}

	public HashSet<StmtAssert> getCorrectAsserts() {
		System.out.println("[Correct asserts] " + correctList);
		return correctList;
	}

	public HashMap<String, StmtAssert> getFailAsserts() {
		HashMap<String, StmtAssert> failAsserts = new HashMap<String, StmtAssert>();
		for (StmtAssert ass : failList) {
			String assString = ass.toString();
			if (assString.contains(","))
				assString = assString.substring(0, assString.indexOf(","));
			failAsserts.put(assString, ass);
		}
		System.out.println("[Fail asserts] " + failAsserts.size());
		return failAsserts;
	}

}
