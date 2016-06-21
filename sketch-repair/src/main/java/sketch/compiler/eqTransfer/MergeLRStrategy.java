/**
 * @author Lisa Jun 11, 2016 MergeDeclStrategy.java 
 */
package sketch.compiler.eqTransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.eqTransfer.model.StmtAssignModel;
import sketch.compiler.eqTransfer.model.StmtModel;

public class MergeLRStrategy extends MergeStrategy {

	public static List<StmtModel> atomicify(int start, int end, List<StmtModel> flatOrigin,
			List<StmtModel> flatUpdate) {
		// System.out.println("[atomicify] ");
		HashMap<String, StmtAssignModel> map = new HashMap<String, StmtAssignModel>();
		HashMap<String, List<StmtAssignModel>> lhsMaps = new HashMap<String, List<StmtAssignModel>>();
		HashMap<String, StmtAssignModel> newGenMap = new HashMap<String, StmtAssignModel>();
		List<StmtModel> res = new ArrayList<StmtModel>();
		// List<String> rhs = new ArrayList<String>();
		for (int i = start; i < end; i++) {
			StmtModel model = flatUpdate.get(i);
			switch (model.getStmtType()) {
			case 2:
				StmtAssignModel assign = (StmtAssignModel) model;
				String lhsThis = assign.getLhsName();
				map.put(lhsThis, assign);
				List<StmtAssignModel> list = lhsMaps.get(lhsThis);
				if (list == null) {
					list = new ArrayList<StmtAssignModel>();
				}
				list.add(assign);
				lhsMaps.put(lhsThis, list);

				for (String lhs : map.keySet()) {
					if (assign.getRhsName().contains(lhs)) {
						StmtAssignModel newLine = new StmtAssignModel(map.get(lhs).getInitString(),
								map.get(lhs).getLocation());
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
			default:
				res.add(model);
				break;
			}
		}
		// res.addAll(map.values());
		res.addAll(newGenMap.values());
		for (String lhs : map.keySet())
			if (!newGenMap.containsKey(lhs))
				res.add(map.get(lhs));
		// String res = "";
		System.out.println(newGenMap.values());
		return res;
	}

}
