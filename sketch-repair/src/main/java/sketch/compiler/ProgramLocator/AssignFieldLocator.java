/**
 * @author Lisa Mar 21, 2016 AssignFieldLocator.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.CandidateGenerator.RepairSketchReplacer;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public class AssignFieldLocator extends SuspiciousStmtLocator {
	private String summary = "";

	public AssignFieldLocator(RepairProgramController utility) {
		super(utility);
	}

	public List<StmtAssign> findSuspiciousStmtInMethod(List<VarDeclEntry> sField, String func) {
		List<StmtAssign> assigns = new ArrayList<StmtAssign>();
		for (StmtAssign assign : utility.getAssignMap().get(func)) {
			List<VarDeclEntry> lhsField = utility.resolveFieldChain(func, assign.getLHS().toString());
			VarDeclEntry suspField = sField.get(sField.size() - 1);
			// FIXME now I only consider last field;
			// FIXME now I only consider LHS
			if (lhsField.get(lhsField.size() - 1).getName().equals(suspField.getName())) {
				assigns.add(assign);
				summary = func + ":" + assign.toString();
				System.out.println("Suspcious field - " + summary);
			}
		}
		return assigns;
	}

//	public boolean runSketch(List<Object> bugAssign) {
//		// int index = 0;
//		// List<String> files = new ArrayList<String>();
//		// String path = utility.getSketchFile();
//		RepairSketchReplacer replGen = new RepairSketchReplacer(bugAssign);
//		Program prog = (Program) replGen.visitProgram(utility.getProgram());
//		if (utility.solveSketch(prog)) {
//			System.out.println("====SketchExprGenerator === successful solve");
//			return true;
//		}
//		return false;
//	}

	public String toString() {
		return summary;
	}

	@Override
	public boolean runSketch(List<StmtAssign> bugAssign) {
		RepairSketchReplacer replGen = new RepairSketchReplacer((List<sketch.compiler.ast.core.stmts.StmtAssign>) bugAssign);
		Program prog = (Program) replGen.visitProgram(utility.getProgram());
		if (utility.solveSketch(prog)) {
			System.out.println("====SketchExprGenerator === successful solve");
			return true;
		}
		return false;
	}
}
