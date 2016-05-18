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

public class SingleTypeStmtStrategy extends CandidateStrategy {

	public SingleTypeStmtStrategy(RepairMultiController controller) {
		super(controller);
	}

	@Override
	public List<AtomicRunModel> generateModel(AtomicRunModel model, List<String> types, HashMap<String, Statement> typeInsertMap) {
		List<AtomicRunModel> models = new ArrayList<AtomicRunModel>();
		for (String type : types) {
			for (int i = 0; i <model.getLocation(); i++)
//			for (int i = model.getLocation(); i > 0; i--)
				models.add(new AtomicRunModel(model.getFunc(), new ArrayList<String>(Arrays.asList(type)),
						typeInsertMap.get(type), i));
		}

		return models;
	}

}
