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

	public List<List<StmtAssign>> createCandidate(String func, List<StmtAssign> bugAssign) {
		List<List<StmtAssign>> layerCandidate = new ArrayList<List<StmtAssign>>();
		for (StmtAssign assign : bugAssign) {
			List<VarDeclEntry> candType = utility.resolveFieldChain(func, assign.getLHS().toString());
			if (candType != null) {
				VarDeclEntry decl = candType.get(candType.size() - 1);
				Expression rhs = assign.getRHS();
				List<StringBuilder> gen = utility.genCandidateSetString(func, decl.getTypeS());
				// gen constant hole
				Expression n_rhs = new ExprStar(rhs.getOrigin());
				layerCandidate.add(new ArrayList<StmtAssign>());
				for (int op : SchemaGenerator.getAssignOperator())
					layerCandidate.get(0).add(new StmtAssign(assign.getLHS(), n_rhs, op));

//				for (int i = 0; i < gen.size(); i++) {
//					if (layerCandidate.size() <= i)
//						layerCandidate.add(new ArrayList<StmtAssign>());
//					if (gen.get(i).toString().trim().length() != 0) {
//						n_rhs = new ExprRegen(rhs.getOrigin(), "{|(" + gen.get(i).toString() + ")|}");
//						for (int op1 : SchemaGenerator.getChoiceOperator()) {
//							n_rhs = new ExprChoiceBinary(n_rhs, op1, new ExprStar(rhs.getOrigin()));
//							StmtAssign rep_assign = new StmtAssign(assign.getLHS(), n_rhs, 0);
//							layerCandidate.get(i).add(rep_assign);
//						}
//					}
//				}
			}
		}
		return layerCandidate;
	}
}