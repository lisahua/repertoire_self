/**
 * @author Lisa Jun 11, 2016 MergeDeclStrategy.java 
 */
package sketch.compiler.eqTransfer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.eqTransfer.model.StmtAssignModel;
import sketch.compiler.eqTransfer.model.StmtDeclModel;
import sketch.compiler.eqTransfer.model.StmtModel;

public class MergeLRStrategy extends MergeStrategy {
	public static Collection<StmtAssignModel> atomicify(int start, int end, List<StmtModel> flatOrigin,
			List<StmtModel> flatUpdate) {
		// System.out.println("[atomicify] ");
		HashMap<String, StmtAssignModel> map = new HashMap<String, StmtAssignModel>();
		HashMap<String, StmtAssignModel> newGenMap = new HashMap<String, StmtAssignModel>();

		// List<String> rhs = new ArrayList<String>();
		for (int i = start; i < end; i++) {
			StmtModel model = flatUpdate.get(i);
			// System.out.println(model);
			switch (model.getStmtType()) {
			case 2:
				StmtAssignModel assign = (StmtAssignModel) model;
				map.put(assign.getLhsName(), assign);
				for (String lhs : map.keySet()) {
					if (assign.getRhsName().contains(lhs)) {
						StmtAssignModel newLine = new StmtAssignModel(map.get(lhs).getInitString(),
								map.get(lhs).getLocation());
						// System.out.println(assign.getOrigin()+","+assign.getLocation());
						// StmtAssignModel newLine =
						// (StmtAssignModel)mmodel.newModel.clone();
						// mmodel.addNewModel(mmodel.getNewModel());
						newLine.setLhsSymbol(assign.getLhsSymbol());
						newLine.setLhsName(assign.getLhsName());
						newLine.setUpdated(newLine.getLhsName() + " = " + newLine.getRhsName());
						newGenMap.put(lhs, newLine);
						// newGenMap.put(assign.getLhsName(), newLine);
						// System.out.println("[mergeLR] " +
						// newLine.getLhsName() + "=" +newLine.getRhsName());
					}
				}
				break;
			}
		}
		// String res = "";
		System.out.println(newGenMap.values());
		return newGenMap.values();
	}
}
