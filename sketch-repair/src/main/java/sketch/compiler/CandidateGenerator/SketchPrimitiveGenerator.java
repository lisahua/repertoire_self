/**
 * @author Lisa Mar 23, 2016 SketchRepairGenerator.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.List;

import sketch.compiler.SchemaGenerator.SchemaGenerator;
import sketch.compiler.ast.core.exprs.ExprStar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.regens.ExprChoiceBinary;
import sketch.compiler.ast.core.exprs.regens.ExprRegen;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public class SketchPrimitiveGenerator extends SketchRepairGenerator {

	public SketchPrimitiveGenerator(RepairProgramController repairProgramUtility) {
		super(repairProgramUtility);
	}

	public List<StmtAssign> createCandidate(String func, List<StmtAssign> bugAssign) {
		List<StmtAssign> layerCandidate = new ArrayList<StmtAssign>();
		for (StmtAssign assign : bugAssign) {
			List<VarDeclEntry> candType = utility.resolveFieldChain(func, assign.getLHS().toString());
			if (candType != null) {
				VarDeclEntry decl = candType.get(candType.size() - 1);
				Expression rhs = assign.getRHS();
				List<StringBuilder> gen = utility.genCandidateSetString(func, decl.getTypeS());
				// gen constant hole
				Expression n_rhs = new ExprStar(rhs.getOrigin());
				// layerCandidate.add(new ArrayList<StmtAssign>());
				for (int op : SchemaGenerator.getAssignOperator()) {
					StmtAssign ass = new StmtAssign(assign.getLHS(), n_rhs, op);
					layerCandidate.add(ass);
				}
			}
		}
		return layerCandidate;
	}

	public List<StmtAssign> createCandidate(String func, StmtAssign assign) {
		List<StmtAssign> layerCandidate = new ArrayList<StmtAssign>();
		List<VarDeclEntry> candType = utility.resolveFieldChain(func, assign.getLHS().toString());
		if (candType != null) {
			VarDeclEntry decl = candType.get(candType.size() - 1);
			Expression rhs = assign.getRHS();
			// a=0; rhs ==null
			if (rhs == null)
				return layerCandidate;
			if (rhs.getOrigin() == null)
				return layerCandidate;
			Expression n_rhs = new ExprStar(rhs.getOrigin());

			for (int op : SchemaGenerator.getAssignOperator()) {
				StmtAssign ass = new StmtAssign(assign.getLHS(), n_rhs, op);;
				layerCandidate.add(ass);
			}
		}
		return layerCandidate;
	}
}
