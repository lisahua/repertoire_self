/**
 * @author Lisa Mar 21, 2016 AssignFieldLocator.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.CandidateGenerator.RepairSketchInsertionReplacer;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public class OmissionFieldLocator extends SuspiciousStmtLocator {

	public OmissionFieldLocator(RepairProgramController utility) {
		super(utility);
	}

	public List<StmtAssign> findSuspiciousStmtInMethod(List<VarDeclEntry> sField, String func) {
		List<StmtAssign> assigns = new ArrayList<StmtAssign>();
		VarDeclEntry suspField = sField.get(sField.size() - 1);
		System.out.println("=======Suspcious field omission field====== " + "," + func + "," + suspField);
		HashSet<Expression> fields = utility.instantiateField(func, suspField.getName());
		for (Expression exp : fields) {
			assigns.add(new StmtAssign(exp, exp, 0));
		}
		return assigns;
	}

	@Override
	public  boolean runSketch(List<StmtAssign> bugAssign) {
		RepairSketchInsertionReplacer replGen = new RepairSketchInsertionReplacer(bugAssign);
		Program prog = (Program) replGen.visitProgram(utility.getProgram());
		if (utility.solveSketch(prog)) {
			System.out.println("====SketchExprGenerator === successful solve");
			return true;
		}
		return false;
	}
	
//	public boolean runSketch(List<Object> bugAssign) {
//	

//	}
}
