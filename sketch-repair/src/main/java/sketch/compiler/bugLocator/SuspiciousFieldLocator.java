/**
 * @author Lisa Mar 18, 2016 FailAssertRecord.java 
 */
package sketch.compiler.bugLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.typs.Type;

public class SuspiciousFieldLocator {
	// private HashMap<String, Type> varTypeMap = new HashMap<String, Type>();
	private RepairProgramUtility utility = null;

	public SuspiciousFieldLocator(RepairProgramUtility utility) {
		this.utility = utility;
	}
	// findAllFieldsInMethod(failAssert);

	public List<StmtAssign> findAllFieldsInMethod(List<String> sField, Function suspFunc) {
		HashSet<Function> funSet = findAllSuspiciousMethod(suspFunc);
		String suspField = sField.get(sField.size() - 1);
		List<StmtAssign> assigns = new ArrayList<StmtAssign>();
		for (Function func : funSet) {

			for (StmtAssign assign : utility.getAssignMap().get(func)) {
				// FIXME now I only consider assign fix
				List<String> lhsField = utility.resolveFieldChain(func, assign.getLHS().toString());
				List<String> rhsField = utility.resolveFieldChain(func, assign.getRHS().toString());
				// FIXME now I only consider last field;
				if (lhsField.get(lhsField.size() - 1).equals(sField.get(sField.size() - 1))) {
					assigns.add(assign);
				} else if (rhsField.get(rhsField.size() - 1).equals(sField.get(sField.size() - 1))) {
					assigns.add(assign);
				}
			}
			for (ExprFunCall funCall : utility.getFuncCallMap().get(func)) {
				// TODO recursively check this method has susp field
			}
		}
return assigns;
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

}
