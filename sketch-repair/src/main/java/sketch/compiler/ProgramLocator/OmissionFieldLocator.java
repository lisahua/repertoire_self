/**
 * @author Lisa Mar 21, 2016 AssignFieldLocator.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.ArrayList;
import java.util.List;

import sketch.compiler.assertionLocator.FieldWrapper;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;

public class OmissionFieldLocator extends SuspiciousStmtLocator {
	private RepairProgramController utility;
	private String summary = "";

	public OmissionFieldLocator(RepairProgramController utility) {
		super(utility);
		this.utility = utility;
	}

	public List<StmtAssign> findSuspiciousStmtInMethod(List<FieldWrapper> sField, Function func) {
		List<StmtAssign> assigns = new ArrayList<StmtAssign>();

//		for (StmtAssign assign : utility.getAssignMap().get(func)) {
//			List<FieldWrapper> lhsField = utility.resolveFieldChain(func, assign.getLHS().toString());
//			FieldWrapper suspField = sField.get(sField.size() - 1);
//			if (lhsField.get(lhsField.size() - 1).getFieldS().equals(suspField.getFieldS())) {
//				assigns.add(assign);
//				summary = func.getName() + ":" + assign.toString();
//				System.out.println("Suspcious field - " + summary);
//			}
//		}
		
		
		return assigns;
	}

	public String toString() {
		return summary;
	}
}
