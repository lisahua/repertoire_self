/**
 * @author Lisa Apr 30, 2016 SingleTypeStmtStrategy.java 
 */
package sketch.compiler.CandidateGenerator.multi.candStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.CandidateGenerator.multi.AtomicRunModel;
import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtBlock;

public class SameTypeDoubleStmtStrategy extends CandidateStrategy {

	public SameTypeDoubleStmtStrategy(RepairMultiController controller) {
		super(controller);
	}

	@Override
	public List<AtomicRunModel> generateModel(AtomicRunModel model, List<String> types, HashMap<String, Statement> typeInsertMap) {
		List<AtomicRunModel> models = new ArrayList<AtomicRunModel>();
		for (String type : types) {
			StmtBlock stmt = (StmtBlock) typeInsertMap.get(type);
			if (stmt==null) continue;
			List<Statement> stmtList = stmt.getStmts();
			List<Statement> newStmt = new ArrayList<Statement>();
			newStmt.addAll(stmtList);
			newStmt.addAll(stmtList);
			stmt = new StmtBlock(stmt.getOrigin(), newStmt);
			for (int i = model.getLocation(); i > 0; i--) {
				models.add(new AtomicRunModel(model.getFunc(), new ArrayList<String>(Arrays.asList(type)), stmt, i));
//				System.out.println(models);
			}
		}

		return models;
	}

}
