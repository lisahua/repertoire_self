/**
 * @author Lisa Mar 23, 2016 SketchRepairGenerator.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprField;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.regens.ExprRegen;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public class SketchExprGenerator extends SketchRepairGenerator {

	public SketchExprGenerator(RepairProgramController utility) {
		super(utility);
	}

	public List<String> runSketch(String func, List<StmtAssign> bugAssign) {
		List<StmtAssign> assignLine = createCandidate(func, bugAssign);
		// int index = 0;
		List<String> files = new ArrayList<String>();
		// String path = utility.getSketchFile();
//		for (List<StmtAssign> ass_list : assignLine) {
			RepairSketchReplacer replGen = new RepairSketchReplacer(assignLine);
			Program prog = (Program) replGen.visitProgram(utility.getProgram());
			if (utility.solveSketch(prog)) {
				System.out.println("====SketchExprGenerator === successful solve");
				return files;
			}
			// }
//		}
		return files;
	}

	public List<StmtAssign> createCandidate(String func, List<StmtAssign> bugAssign) {
		List<StmtAssign> layerCandidate = new ArrayList<StmtAssign>();
		for (StmtAssign assign : bugAssign) {
			List<VarDeclEntry> candType = utility.resolveFieldChain(func, assign.getLHS().toString());
			if (candType != null) {
				VarDeclEntry decl = candType.get(candType.size() - 1);
				Expression rhs = assign.getRHS();
				List<StringBuilder> gen = utility.genCandidateSetString(func, decl.getTypeS());

				for (int i = 1; i < gen.size(); i++) {
					if (gen.get(i).length() != 0)
						gen.get(0).append("|" + gen.get(i));
				}
				Expression n_rhs = null;
				if (gen.get(0).toString().trim().length() != 0) {
					n_rhs = new ExprRegen(assign.getOrigin(),
							"{|" + rhs.toString() + "|" + gen.get(0).toString() + "|}");
					// for (Expression n_lhs : lhs_set) {
					StmtAssign rep_assign = new StmtAssign(assign.getLHS(), n_rhs, assign.getOp());

					layerCandidate.add(rep_assign);
				}
			}
		}
		return layerCandidate;
	}

	public List<StmtAssign> createCandidate(String func, StmtAssign assign) {
		List<StmtAssign> layerCandidate = new ArrayList<StmtAssign>();
		List<VarDeclEntry> candType = utility.resolveFieldChain(func, assign.getLHS().toString());
		if (candType == null)
			return layerCandidate;
		VarDeclEntry decl = candType.get(candType.size() - 1);
		Expression rhs = assign.getRHS();
		List<StringBuilder> gen = utility.genCandidateSetString(func, decl.getTypeS());

		for (int i = 1; i < gen.size(); i++) {
			if (gen.get(i).length() != 0)
				gen.get(0).append("|" + gen.get(i));
		}
		Expression n_rhs = null;
		if (gen.get(0).toString().trim().length() != 0) {
			n_rhs = new ExprRegen(assign.getOrigin(), "{|" + rhs.toString() + "|" + gen.get(0).toString() + "|}");
			// for (Expression n_lhs : lhs_set) {
			StmtAssign rep_assign = new StmtAssign(assign.getLHS(), n_rhs, assign.getOp());
			layerCandidate.add(rep_assign);
		}
		return layerCandidate;
	}
}
