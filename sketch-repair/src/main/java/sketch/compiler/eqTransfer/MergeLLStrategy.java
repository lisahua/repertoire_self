/**
 * @author Lisa Jun 11, 2016 MergeAssignStrategy.java 
 */
package sketch.compiler.eqTransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import sketch.compiler.eqTransfer.model.StmtAssignModel;
import sketch.compiler.eqTransfer.model.StmtModel;

public class MergeLLStrategy extends MergeStrategy {
	/**
	 * If lhs=r1,lhs=r2, remove lhs=r1; If lhs=lhs, remove this assign
	 * 
	 * @param mergeLR
	 * @param flatUpdate
	 * @return
	 */
	public static List<StmtModel> merge(TreeMap<Integer, List<StmtModel>> mergeLR, List<StmtModel> flatUpdate) {
		int prev = 0;
		List<StmtModel> list = new ArrayList<StmtModel>();
		
		for (int i : mergeLR.keySet()) {
			System.out.println("[mergeLL before  ] " + prev + "," + i);
//			if (prev < i) {
//				list.addAll(flatUpdate.subList(prev, i));
//				prev = i;
//			}
			list.addAll(mergeLR.get(i));
		}
		if (prev < flatUpdate.size())
			list.addAll(flatUpdate.subList(prev, flatUpdate.size()));
		for (StmtModel model : list)
			System.out.println("[mergeLL ] " + model);

		// List<StmtModel> needed = new ArrayList<StmtModel>();
		// HashMap<String, Integer> map = new HashMap<String, Integer>();
		// for (int i = 0; i < list.size(); i++) {
		// if (list.get(i) instanceof StmtAssignModel) {
		// StmtAssignModel assign = (StmtAssignModel) list.get(i);
		// if (map.containsKey(assign.getLhsName())) {
		// System.out.println("[mergeLL remove ] " + needed.get(needed.size() -
		// 1));
		// needed.add(list.get(map.get(assign.getLhsName())));
		// }
		// map.put(assign.getLhsName(), i);
		// }
		// }
		// for (StmtModel model : needed)
		// list.remove(model);
		return list;
	}
}
