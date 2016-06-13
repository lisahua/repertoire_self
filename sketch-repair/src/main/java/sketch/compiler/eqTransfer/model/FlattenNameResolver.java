/**
 * @author Lisa Jun 10, 2016 FlattenNameResolver.java 
 */
package sketch.compiler.eqTransfer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlattenNameResolver {
	static HashMap<String, List<StmtAssignModel>> varValue = new HashMap<String, List<StmtAssignModel>>();
	static HashMap<String, List<StmtModel>> originValue = new HashMap<String, List<StmtModel>>();

	public static void addUpdate(String var, StmtAssignModel value) {
		List<StmtAssignModel> list = varValue.get(var);
		if (list == null)
			list = new ArrayList<StmtAssignModel>();
		list.add(value);
		varValue.put(var, list);
	}

	public static void addOrigin(String var, StmtModel value) {
		List<StmtModel> list = originValue.get(var);
		if (list == null)
			list = new ArrayList<StmtModel>();
		list.add(value);
		originValue.put(var, list);
	}

	public static List<StmtAssignModel> getUpdate(String var) {
		return varValue.get(var);
	}

	public static List<StmtModel> getOrigin(String var) {
		return originValue.get(var);
	}

	public static void declMerge(int startID, int[] mergeID, List<StmtModel> flatOrigin, List<StmtModel> flatUpdate) {
		StmtDeclModel model = (StmtDeclModel) flatUpdate.get(startID);
		String varName = model.name;
		if (varValue.containsKey(varName)) {
			// for (StmtAssignModel rhs: varValue) {
			//
			// }
		}
	}

	public static void assignMerge(int startID, int[] mergeID, List<StmtModel> flatOrigin, List<StmtModel> flatUpdate) {

	}

}
