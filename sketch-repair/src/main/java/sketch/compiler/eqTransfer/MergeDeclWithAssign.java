/**
 * @author Lisa Jun 8, 2016 MergeDeclWithAssign.java 
 */
package sketch.compiler.eqTransfer;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.eqTransfer.model.StmtModel;

public class MergeDeclWithAssign extends FEReplacer {

	public static void atomicify(List<String> stmts) {
		System.out.println("[atomicify] " + stmts);

	}

	private static void mergeDeclAssign(List<String> stmts) {

	}

	private static void mergeAssigns(List<String> stmts) {

	}

	private static void mergeRenamed() {

	}

	public static void compareWithStmt(String origin, List<String> update) {
		varDeclSplit(origin, update);
	}

	private static void varDeclSplit(String origin, List<String> update) {
		String[] originTkn = origin.split(",");
		String varName = "";
		if (originTkn[0].equals("varDecl"))
			varName = originTkn[3];
		int id = 0;
		for (; id < update.size(); id++) {
			String[] upTkn = update.get(id).split(",");
			System.out.println("[MergeDeclWithAssign] " + update.get(id));
			boolean flag = false;
			// if it is auto-gen, ignore
			// if (upTkn[upTkn.length - 1].equals("true"))
			// continue;
			if (upTkn[0].equals("varDecl")) {
				if (FlattenStmtModel.matchNode(origin, update.get(id))) {
					flag = true;
					System.out.println("[MergeDeclWithAssign] true");
					continue;
				}
			} else if (upTkn[0].equals("assign")) {
				String var = upTkn[2];
				int tmp = var.indexOf(".");
				if (tmp > 0)
					var = var.substring(0, tmp);
				if (varName.equals(var)) {
					flag = true;
					System.out.println("[MergeDeclWithAssign] true");
					continue;
				}
			} else if (upTkn[0].equals("stmtExpr")) {
				if (originTkn[4].contains(upTkn[1])) {
					flag = true;
					System.out.println("[MergeDeclWithAssign] true");
					continue;
				}
			}
			if (!flag)
				break;
		}
		if (id < update.size() - 1)
			atomicify(update.subList(id, update.size()));
	}

	public static void mergeStmts(int[] matchID, List<String> flatOrigin, List<String> flatUpdate) {
		StmtModel model = StmtModel.genModel(flatUpdate.get(0), 0);
		TreeSet<StmtModel> mergeSet = new TreeSet<StmtModel>();
		for (int i = 1; i < flatUpdate.size(); i++) {
			mergeSet.add(model);
			StmtModel next = StmtModel.genModel(flatUpdate.get(i), i);
			if (next == null) {
				System.out.println("[mergestmt bug] " + flatUpdate.get(i));
			} else
				model = model.parse(next);
		}
		int[] mergeID = new int[mergeSet.size()];
		int i = 0;
		for (StmtModel md : mergeSet) {
			mergeID[i++] = md.getLocation();
		}
		HashSet<Integer> matchSet = new HashSet<Integer>();
		HashSet<Integer> mergeIDSet = new HashSet<Integer>();
		for (int id : matchID)
			matchSet.add(id);
		for (int id : mergeID) {
			if (!matchSet.contains(id)) {
				mergeIDSet.add(id);
				System.out.println(id);
			}
		}
for (int id: mergeIDSet) {
	
}
	}

	// System.out.println("[merge stmt] " + mergeID);
	// check with matchID

}
