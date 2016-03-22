/**
 * @author Lisa Mar 21, 2016 AssignFieldLocator.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.ArrayList;
import java.util.List;

import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public class AssignFieldLocator extends SuspiciousStmtLocator {
	private RepairProgramController utility;
	private String summary = "";

	public AssignFieldLocator(RepairProgramController utility) {
		super(utility);
		this.utility = utility;
	}

	public List<StmtAssign> findSuspiciousStmtInMethod(List<VarDeclEntry> sField, String func) {
		List<StmtAssign> assigns = new ArrayList<StmtAssign>();

		for (StmtAssign assign : utility.getAssignMap().get(func)) {
			List<VarDeclEntry> lhsField = utility.resolveFieldChain(func, assign.getLHS().toString());
			VarDeclEntry suspField = sField.get(sField.size() - 1);
			// FIXME now I only consider last field;
			// FIXME now I only consider LHS
//			System.out.println("===DEBUG ===lhsField "+lhsField.size()+" suspField "+suspField+" "+sField.size());
			if (lhsField.get(lhsField.size() - 1).getName().equals(suspField.getName())) {
				assigns.add(assign);
				summary = func + ":" + assign.toString();
				System.out.println("Suspcious field - " + summary);
			}
		}
		return assigns;
	}

	public String toString() {
		return summary;
	}
}
