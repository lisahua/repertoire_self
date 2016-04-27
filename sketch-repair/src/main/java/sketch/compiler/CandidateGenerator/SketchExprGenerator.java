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
				System.out.println("====SketchExprGenerator create === " + decl + ",");
				List<StringBuilder> gen = utility.genCandidateSetString(func, decl.getTypeS());

				// List<HashSet<String>> lhs_S = utility.genCandidateList(func,
				// decl.getTypeS());
				// System.out.print("===sketchExprGen lhs ===");
				// HashSet<Expression> lhs_set = new HashSet<Expression>();
				// HashSet<Expression> rhs_set = new HashSet<Expression>();
				// for (HashSet<String> set : lhs_S) {
				// for (String s : set) {
				// Expression n_lhs = null;
				// String[] token = s.split("\\.");
				// n_lhs = new ExprVar(assign.getOrigin(), token[0]);
				// int len = token.length;
				// for (int i = 1; i < len; i++) {
				// n_lhs = new ExprField(assign.getOrigin(), n_lhs, token[i]);
				// }
				// lhs_set.add(n_lhs);
				// }
				// }

				for (int i = 1; i < gen.size(); i++) {
					if (gen.get(i).length() != 0)
						gen.get(0).append("|" + gen.get(i));
				}
				// change rhs
				// if (layerCandidate.size() <= i)
//				layerCandidate.add(new ArrayList<StmtAssign>());
				Expression n_rhs = null;
				// if (gen.get(i) == null || rhs == null)
				// continue;
				if (gen.get(0).toString().trim().length() != 0) {
					FENode node = assign.getOrigin();
					// if (node == null) {
					// node = utility.getFuncMap(func).getOrigin();
					// }
					n_rhs = new ExprRegen(assign.getOrigin(),
							"{|" + rhs.toString() + "|" + gen.get(0).toString() + "|}");
					// for (Expression n_lhs : lhs_set) {
					StmtAssign rep_assign = new StmtAssign(assign.getLHS(), n_rhs, assign.getOp());
					System.out.println("====ExprGenerator create candidate node===" + rep_assign + "," + assign);

					layerCandidate.add(rep_assign);
				}
			}
			// }

			// }
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
		System.out.println("====SketchExprGenerator create === " + decl + ",");
		List<StringBuilder> gen = utility.genCandidateSetString(func, decl.getTypeS());

		for (int i = 1; i < gen.size(); i++) {
			if (gen.get(i).length() != 0)
				gen.get(0).append("|" + gen.get(i));
		}
		// change rhs
		// if (layerCandidate.size() <= i)
		// layerCandidate.add(new ArrayList<StmtAssign>());
		Expression n_rhs = null;
		// if (gen.get(i) == null || rhs == null)
		// continue;
		if (gen.get(0).toString().trim().length() != 0) {
			FENode node = assign.getOrigin();
			// if (node == null) {
			// node = utility.getFuncMap(func).getOrigin();
			// }
			n_rhs = new ExprRegen(assign.getOrigin(), "{|" + rhs.toString() + "|" + gen.get(0).toString() + "|}");
			// for (Expression n_lhs : lhs_set) {
			StmtAssign rep_assign = new StmtAssign(assign.getLHS(), n_rhs, assign.getOp());
			System.out.println("====ExprGenerator create candidate node===" + rep_assign + "," + assign);

			layerCandidate.add(rep_assign);
		}
		return layerCandidate;
	}
}
