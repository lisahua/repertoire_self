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
import sketch.compiler.bugLocator.FieldWrapper;
import sketch.compiler.bugLocator.RepairProgramUtility;

public class SuspiciousFieldLocator {
	// private HashMap<String, Type> varTypeMap = new HashMap<String, Type>();
	private RepairProgramUtility utility = null;
	private HashMap<Function, List<StmtAssign>> suspAssign = new HashMap<Function, List<StmtAssign>>();

	public SuspiciousFieldLocator(RepairProgramUtility utility) {
		this.utility = utility;
	}
	// findAllFieldsInMethod(failAssert);

	public HashMap<Function, List<StmtAssign>> findAllFieldsInMethod(List<FieldWrapper> sField, Function suspFunc) {
		HashSet<Function> funSet = findAllSuspiciousMethod(suspFunc);
		List<StmtAssign> assigns = new ArrayList<StmtAssign>();
		for (Function func : funSet) {

			for (StmtAssign assign : utility.getAssignMap().get(func)) {
				// FIXME now I only consider assign fix
				List<FieldWrapper> lhsField = utility.resolveFieldChain(func, assign.getLHS().toString());
				FieldWrapper suspField = sField.get(sField.size() - 1);
				// List<String> rhsField = utility.resolveFieldChain(func,
				// assign.getRHS().toString());
				// FIXME now I only consider last field;
				if (lhsField.get(lhsField.size()-1).getFieldS().equals(suspField.getFieldS())) {
					assigns.add(assign);
					System.out.println(
							"Suspcious field - " + func.getName() + ":" + assign.getLHS() + " RHS " + assign.getRHS());
				}
				// } else if (rhsField.get(rhsField.size() -
				// 1).equals(suspField)) {
				// assigns.add(assign);
				// }
			}
			suspAssign.put(func, assigns);
			for (ExprFunCall funCall : utility.getFuncCallMap().get(func)) {
				// TODO recursively check this method has susp field
			}
		}
		return suspAssign;
	}

	private HashSet<Function> findAllSuspiciousMethod(Function suspFunc) {
		List<ExprFunCall> funCall = utility.getFuncCallMap().get(suspFunc);
		HashSet<Function> funSet = new HashSet<Function>();
		for (ExprFunCall call : funCall) {
			Function func = utility.getFuncMap().get(call.getName());
			if (func != null)
				funSet.add(func);
		}
		return funSet;
	}

	public HashMap<Function, List<StmtAssign>> getSuspciousAssign() {
		return suspAssign;
	}
}
