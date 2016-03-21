/**
 * @author Lisa Mar 18, 2016 FailAssertRecord.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.assertionLocator.FieldWrapper;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramUtility;

public class SuspiciousFieldCollector {
	// private HashMap<String, Type> varTypeMap = new HashMap<String, Type>();
	private RepairProgramUtility utility = null;
	private HashMap<Function, List<StmtAssign>> suspAssign = new HashMap<Function, List<StmtAssign>>();
	private List<SuspiciousStmtLocator> locatorList = new ArrayList<SuspiciousStmtLocator>();

	public SuspiciousFieldCollector(RepairProgramUtility utility) {
		this.utility = utility;
		locatorList.add(new AssignFieldLocator(utility));
		locatorList.add(new OmissionFieldLocator(utility));

	}

	public HashMap<Function, List<StmtAssign>> findAllFieldsInMethod(List<FieldWrapper> sField, Function suspFunc) {
		HashSet<Function> funSet = findAllSuspiciousMethod(suspFunc);
		List<StmtAssign> assigns = new ArrayList<StmtAssign>();
		for (Function func : funSet) {
			for (SuspiciousStmtLocator locator : locatorList)
				assigns.addAll(locator.findSuspiciousStmtInMethod(sField, func));
//			System.out.println("Suspicios fields "+func.getName()+","+assigns.size());
			suspAssign.put(func, assigns);
			for (ExprFunCall funCall : utility.getFuncCallMap().get(func)) {
			}
		}

		return suspAssign;
	}


	private HashSet<Function> findAllSuspiciousMethod(Function suspFunc) {
		List<ExprFunCall> funCall = utility.getFuncCallMap().get(suspFunc);
		HashSet<Function> funSet = new HashSet<Function>();
		for (ExprFunCall call : funCall) {
			Function func = utility.getFuncMap(call.getName());
			if (func != null)
				funSet.add(func);
		}
		return funSet;
	}

	public HashMap<Function, List<StmtAssign>> getSuspciousAssign() {
		return suspAssign;
	}
}
