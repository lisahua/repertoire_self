/**
 * @author Lisa Jun 14, 2016 RecurMergeStrategy.java 
 */
package sketch.compiler.eqTransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.eqTransfer.model.StmtAssignModel;
import sketch.compiler.eqTransfer.model.StmtModel;

public class RecurMergeStrategy extends MergeStrategy {
	private static int pointer = 0;
	private static List<StmtModel> result = new ArrayList<StmtModel>();
	private static List<StmtModel> prog = null;
	private static HashMap<String, StmtAssignModel> map = new HashMap<String, StmtAssignModel>();
	private static HashMap<String, StmtAssignModel> newGenMap = new HashMap<String, StmtAssignModel>();
	private static boolean inside = false;

	public static List<StmtModel> merge(List<StmtModel> flatUpdate) {
		prog = flatUpdate;
		while (pointer < flatUpdate.size()) {
			parse();
		}
		return flatUpdate;
	}

	private static void parse() {
		StmtModel model = prog.get(pointer);
		switch (model.getStmtType()) {
		case 5:
		case 1:
			result.add(model);
			break;
		case 3:
		case 4:
			//TODO replace _tmp in if-expr
			result.add(model);
			if (inside==true) {
				
			}
			
			
			break;
		case 2:
			StmtAssignModel assign = (StmtAssignModel) model;
			map.put(assign.getLhsName(), assign);
			for (String lhs : map.keySet()) {
				if (assign.getRhsName().contains(lhs)) {
					StmtAssignModel newLine = map.get(lhs);
					newLine.setLhsSymbol(assign.getLhsSymbol());
					newLine.setLhsName(assign.getLhsName());
					newLine.setUpdated(newLine.getLhsName() + " = " + newLine.getRhsName());
					newGenMap.put(assign.getLhsName(), newLine);
					System.out.println("[mergeLR] " + newLine.getLhsName() + "=" + newLine.getRhsName());
				}
			}
			break;
		// case 5:
		// StmtExprModel expr = (StmtExprModel) model;
		// TODO
		}

	}
}
