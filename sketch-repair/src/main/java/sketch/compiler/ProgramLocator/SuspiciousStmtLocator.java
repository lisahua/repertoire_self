/**
 * @author Lisa Mar 18, 2016 FailAssertRecord.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.assertionLocator.FieldWrapper;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramUtility;

public abstract class SuspiciousStmtLocator {
	private RepairProgramUtility utility = null;
//	private HashMap<Function, List<StmtAssign>> suspAssign = new HashMap<Function, List<StmtAssign>>();

	public SuspiciousStmtLocator(RepairProgramUtility utility) {
		this.utility = utility;
	}

	public abstract List<StmtAssign> findSuspiciousStmtInMethod(List<FieldWrapper> sField, Function func) ;

	
}
