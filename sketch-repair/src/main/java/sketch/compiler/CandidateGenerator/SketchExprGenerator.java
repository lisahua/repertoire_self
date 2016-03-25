/**
 * @author Lisa Mar 23, 2016 SketchRepairGenerator.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.regens.ExprRegen;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public class SketchExprGenerator extends SketchRepairGenerator {

	public SketchExprGenerator(RepairProgramController utility) {
		super(utility);
	}

	public List<String> runSketch(HashMap<String, List<StmtAssign>> bugAssign) {
		List<List<StmtAssign>> assignLine = createCandidate(bugAssign);
		int index = 0;
		List<String> files = new ArrayList<String>();
		String path = utility.getSketchFile();
		for (List<StmtAssign> ass_list : assignLine) {
			RepairSketchReplacer replGen = new RepairSketchReplacer(ass_list);
			Program prog = (Program) replGen.visitProgram(utility.getProgram());
			if (utility.solveSketch(prog)) {
				System.out.println("====SketchExprGenerator === successful solve");
				break;
			}
			// }
		}
		return files;
	}

	public List<List<StmtAssign>> createCandidate(HashMap<String, List<StmtAssign>> bugAssign) {
		List<List<StmtAssign>> layerCandidate = new ArrayList<List<StmtAssign>>();
		// List<StmtAssign> singleLayer = new ArrayList<StmtAssign>();
		for (String func : bugAssign.keySet()) {
			for (StmtAssign assign : bugAssign.get(func)) {
				List<VarDeclEntry> candType = utility.resolveFieldChain(func, assign.getLHS().toString());
				if (candType != null) {
					VarDeclEntry decl = candType.get(candType.size() - 1);
					Expression rhs = assign.getRHS();
					List<StringBuilder> gen = utility.genCandidateSetString(func, decl.getTypeS());
					for (int i = 0; i < gen.size(); i++) {
						Expression n_rhs = new ExprRegen(rhs.getOrigin(),
								"{|" + rhs.toString() + "|" + gen.get(i).toString() + "|}");
						StmtAssign rep_assign = new StmtAssign(assign.getLHS(), n_rhs, assign.getOp());
						if (layerCandidate.size() <= i)
							layerCandidate.add(new ArrayList<StmtAssign>());
						layerCandidate.get(i).add(rep_assign);
						// System.out.println("===createCandidate ===" + func +
						// "," + gen + "," + rep_assign + ","
						// + assign.toString());

					}
				}
			}
		}
		return layerCandidate;
	}

}
