/**
 * @author Lisa Apr 30, 2016 SingleTypeStmtStrategy.java 
 */
package sketch.compiler.CandidateGenerator.multi.candStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.CandidateGenerator.multi.AtomicRunModel;
import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtBlock;

public class DiffTypeDoubleStmtStrategy extends CandidateStrategy {

	public DiffTypeDoubleStmtStrategy(RepairMultiController controller) {
		super(controller);
	}

	@Override
	public List<AtomicRunModel> generateModel(AtomicRunModel model, List<String> buggTypes, HashMap<String, Statement> typeInsertMap) {
		List<AtomicRunModel> models = new ArrayList<AtomicRunModel>();
		List<String> types = new ArrayList<String>();
		types.addAll(typeInsertMap.keySet());
		if (types.size() < 2)
			return models;
		for (int m = 0; m < types.size() - 1; m++) {
			for (int n = m + 1; n < types.size(); n++) {
				StmtBlock stmt = (StmtBlock) typeInsertMap.get(types.get(m));
				StmtBlock stmt2 = (StmtBlock) typeInsertMap.get(types.get(n));
				
				List<Statement> newStmt = new ArrayList<Statement>();
				if (stmt!=null)
				newStmt.addAll(stmt.getStmts());
				if (stmt2 != null)
					newStmt.addAll(stmt2.getStmts());
				stmt = new StmtBlock(stmt.getOrigin(), newStmt);
				for (int i = model.getLocation(); i > 0; i--) {
					models.add(new AtomicRunModel(model.getFunc(), types, stmt, i));
				}
			}
		}

		return models;
	}

}
