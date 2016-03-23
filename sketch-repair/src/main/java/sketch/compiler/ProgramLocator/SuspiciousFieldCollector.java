/**
 * @author Lisa Mar 18, 2016 FailAssertRecord.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public class SuspiciousFieldCollector {
	private RepairProgramController utility = null;
	private HashMap<String, List<StmtAssign>> suspAssign = new HashMap<String, List<StmtAssign>>();
	private List<SuspiciousStmtLocator> locatorList = new ArrayList<SuspiciousStmtLocator>();

	public SuspiciousFieldCollector(RepairProgramController utility) {
		this.utility = utility;
		locatorList.add(new AssignFieldLocator(utility));
		locatorList.add(new OmissionFieldLocator(utility));

	}

	public HashMap<String, List<StmtAssign>> findAllFieldsInMethod(List<VarDeclEntry> sField, String suspFunc) {
		HashSet<String> funSet = findAllSuspiciousMethod(suspFunc);
		
		for (String func : funSet) {
			List<StmtAssign> assigns = new ArrayList<StmtAssign>();
			for (SuspiciousStmtLocator locator : locatorList) {
				assigns.addAll(locator.findSuspiciousStmtInMethod(sField, func));
			}
			suspAssign.put(func, assigns);
			for (ExprFunCall funCall : utility.getFuncCallMap().get(func)) {
			}
		}

		return suspAssign;
	}


	private HashSet<String> findAllSuspiciousMethod(String suspFunc) {
		List<ExprFunCall> funCall = utility.getFuncCallMap().get(suspFunc);
		HashSet<String> funSet = new HashSet<String>();
		for (ExprFunCall call : funCall) {
			Function func = utility.getFuncMap(call.getName());
			if (func != null)
				funSet.add(func.getName());
		}
		return funSet;
	}

	public HashMap<String, List<StmtAssign>> getSuspciousAssign() {
		return suspAssign;
	}
}
