/**
 * @author Lisa Mar 21, 2016 AssignFieldLocator.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.CandidateGenerator.RepairSketchInsertionReplacer;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.Expression;
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
		Program prog = writeToFile(func, assigns);
		assigns = new ArrayList<StmtAssign>();
		for (int i = 0; i < prog.getPackages().size(); i++) {
			sketch.compiler.ast.core.Package pkg = prog.getPackages().get(i);
			for (int j = 0; j < pkg.getFuncs().size(); j++) {
				Function f = pkg.getFuncs().get(j);
				if (f.getName().equals(func)) {
					for (StmtAssign assign : utility.getAssignMap().get(func)) {
						List<VarDeclEntry> lhsField = utility.resolveFieldChain(func, assign.getLHS().toString());
						if (lhsField.get(lhsField.size() - 1).getName().equals(suspField.getName())) {
							assigns.add(assign);
						}
					}
					return assigns;
				}
			}
		}
		return assigns;
	}

	private Program writeToFile(String func, List<StmtAssign> assigns) {
		RepairSketchInsertionReplacer replacer = new RepairSketchInsertionReplacer(func, assigns);
		// Function fuc = (Function) utility.getFuncMap(func).accept(replacer);

		return utility.writeFile(replacer);
	}

	@Override
	public boolean runSketch(List<StmtAssign> bugAssign) {
		// RepairSketchInsertionReplacer replGen = new
		// RepairSketchInsertionReplacer(funcbugAssign);
		// Program prog = (Program) replGen.visitProgram(utility.getProgram());
		// if (utility.solveSketch(prog)) {
		// System.out.println("====SketchExprGenerator === successful solve");
		// return true;
		// }
		return false;
	}

	// public boolean runSketch(List<Object> bugAssign) {
	//

	// }
}
