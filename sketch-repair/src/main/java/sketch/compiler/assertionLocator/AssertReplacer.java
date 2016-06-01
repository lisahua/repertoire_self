/**
 * @author Lisa May 19, 2016 AssertIsolator.java 
 */
package sketch.compiler.assertionLocator;

import java.io.FileNotFoundException;
import java.util.HashSet;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtEmpty;
import sketch.compiler.main.other.RepairSketchOptions;
import sketch.compiler.main.other.RepairStageRunner;
import sketch.compiler.main.other.SimpleSketchFilePrinter;

public class AssertReplacer extends FEReplacer {
	HashSet<StmtAssert> assList = new HashSet<StmtAssert>();
	HashSet<StmtAssert> removeList = new HashSet<StmtAssert>();
	private RepairSketchOptions options = null;
	private Program updatedProg;
private String field ="";
	public AssertReplacer(RepairSketchOptions options) {
		this.options = options;
	}

	public Program getUpdatedProg() {
		return updatedProg;
	}
	
	public String removeStmtAsserts(HashSet<StmtAssert> assList, Program prog) {
		this.removeList = assList;
		updatedProg = (Program) this.visitProgram(prog);
		return solveSketch(updatedProg);
	}
	public String removeStmtAsserts(String field, Program prog) {
		this.field = field;
		updatedProg = (Program) this.visitProgram(prog);
		return solveSketch(updatedProg);
	}
	public String removeStmtAssert(StmtAssert ass, Program prog) {
//		removeList = new HashSet<StmtAssert>();
		removeList.add(ass);
		return removeStmtAsserts(removeList, prog);
	}

	public Object visitStmtAssert(StmtAssert stmtAss) {
		assList.add(stmtAss);
		if (stmtAss.toString().contains(field)) {
			System.out.println("[add assert] "+field+","+stmtAss);
//		if (removeList.contains(stmtAss)) {
			return super.visitStmtAssert(stmtAss);
		} else {
			System.out.println("[no assert] "+field+","+stmtAss);
			return new StmtEmpty(stmtAss.getOrigin());
		}
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

	public boolean continueRemoveAsserts() {
		return assList.size()==0 || assList.size() > removeList.size();
	}
	
}
