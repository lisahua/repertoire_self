/**
 * @author Lisa Mar 18, 2016 FailAssertRecord.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.CandidateGenerator.SketchRepairCollector;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public class SuspiciousFieldCollector {
	private RepairProgramController utility = null;
	private List<SuspiciousStmtLocator> locatorList = new ArrayList<SuspiciousStmtLocator>();
	private SketchRepairCollector genCollector = null;

	public SuspiciousFieldCollector(RepairProgramController utility) {
		this.utility = utility;
		locatorList.add(new AssignFieldLocator(utility));
		locatorList.add(new OmissionFieldLocator(utility));
		genCollector = new SketchRepairCollector(utility);
	}

	public boolean findAllFieldsInMethod(List<VarDeclEntry> sField, String suspFunc) {
		HashSet<String> funSet = findAllSuspiciousMethod(suspFunc);
		// for (SuspiciousStmtLocator locator : locatorList) {
		SuspiciousStmtLocator locator = new AssignFieldLocator(utility);
		for (String func : funSet) {
			List<StmtAssign> assigns = new ArrayList<StmtAssign>();
			assigns.addAll(locator.findSuspiciousStmtInMethod(sField, func));
			List<List<StmtAssign>> genAssign = genCollector.createCandidate(func, assigns);
			for (List<StmtAssign> ass : genAssign)
				if (locator.runSketch(ass, utility.getProgram()))
					return true;
		}

		OmissionFieldLocator m_locator = new OmissionFieldLocator(utility);
		for (String func : funSet) {
			if (m_locator.repairInMethod(sField, func))
				return true;

			// List<StmtAssign> assigns = new ArrayList<StmtAssign>();
			// assigns.addAll(locator.findSuspiciousStmtInMethod(sField, func));
			// List<List<StmtAssign>> genAssign =
			// genCollector.createCandidate(func, assigns);
			// for (List<StmtAssign> ass : genAssign)
			// if (locator.runSketch(assigns,null))
			// return true;
		}

		return false;
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
}
