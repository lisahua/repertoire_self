/**
 * @author Lisa Jun 11, 2016 MergeDeclStrategy.java 
 */
package sketch.compiler.eqTransfer;

import java.util.HashMap;
import java.util.List;

import sketch.compiler.eqTransfer.model.StmtAssignModel;
import sketch.compiler.eqTransfer.model.StmtDeclModel;
import sketch.compiler.eqTransfer.model.StmtModel;

public class MergeDeclStrategy extends MergeStrategy {
	public String atomicify(int start, int end, List<StmtModel> flatOrigin, List<StmtModel> flatUpdate) {
		System.out.println("[atomicify] ");
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, String> newGenMap = new HashMap<String, String>();
		// List<String> rhs = new ArrayList<String>();
		for (int i = start; i < end; i++) {
			StmtModel model = flatUpdate.get(i);
			System.out.println(model);
			switch (model.getStmtType()) {
			case 1:
				StmtDeclModel decl = (StmtDeclModel) model;
				map.put(decl.getName(), "");
				break;
			case 2:
				StmtAssignModel assign = (StmtAssignModel) model;
				map.put(assign.getLhsName(), assign.getRhsName());
				for (String lhs : map.keySet()) {
					if (assign.getRhsName().contains(lhs))
						newGenMap.put(assign.getLhsName(), map.get(lhs));
					// rhs.add(assign.getRhsName());
				}
				break;
			case 5:
				// StmtExprModel expr = (StmtExprModel) model;
				// TODO
			}
		}
		String res = "";
		for (String key : newGenMap.keySet())
			res +=  key + "=" + newGenMap.get(key)+";";
		return res;

	}

}
