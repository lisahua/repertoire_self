/**
 * @author Lisa Jun 8, 2016 MergeDeclWithAssign.java 
 */
package sketch.compiler.eqTransfer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.eqTransfer.model.StmtModel;

public class MergeDeclWithAssign extends FEReplacer {

	public static List<String> atomicify(int[] mergeID, HashSet<Integer> matchSet, List<StmtModel> flatOrigin,
			List<StmtModel> flatUpdate) {
		System.out.println("[atomicify] ");
		// HashMap<String, String> map = new HashMap<String, String>();
		// HashMap<String, String> newGenMap = new HashMap<String, String>();

		List<String> res = new ArrayList<String>();
		for (int i = 0; i < mergeID.length - 1; i++) {
			StmtModel model = flatUpdate.get(mergeID[i]);
			if (model.toString().contains("_out"))
				continue;
			// hacky if its a single stmt
			if (matchSet.contains(model.getLocation() + 1))
				continue;
			switch (model.getStmtType()) {
			case 1:
				String[] lines = new MergeDeclStrategy().atomicify(mergeID[i], mergeID[i + 1], flatOrigin, flatUpdate)
						.split(",");
				for (String line : lines)
					res.add(line);
				break;
			case 2:
				lines = new MergeAssignStrategy().atomicify(mergeID[i], mergeID[i + 1], flatOrigin, flatUpdate)
						.split(",");
				for (String line : lines)
					res.add(line);
				break;
			}
		}
		return res;
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

		// if (id < update.size() - 1)
		// atomicify(update.subList(id, update.size()));
	}

	public static List<String> mergeStmts(int[] matchID, List<String> flatOrigin, List<String> flatUpdate) {
		StmtModel model = StmtModel.genModel(flatUpdate.get(0), 0);
		TreeSet<StmtModel> mergeSet = new TreeSet<StmtModel>();

		List<StmtModel> updateSet = new ArrayList<StmtModel>();
		updateSet.add(model);
		for (int i = 1; i < flatUpdate.size(); i++) {
			mergeSet.add(model);
			StmtModel next = StmtModel.genModel(flatUpdate.get(i), i);
			updateSet.add(next);
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
		TreeSet<Integer> mergeIDSet = new TreeSet<Integer>();
		for (int id : matchID)
			matchSet.add(id);
		for (int id : mergeID) {
			if (!matchSet.contains(id)) {
				mergeIDSet.add(id);
				System.out.println(id);
			}
		}
		mergeID = new int[mergeIDSet.size()];
		i = 0;
		for (int id : mergeIDSet)
			mergeID[i++] = id;
		List<StmtModel> originSet = new ArrayList<StmtModel>();
		for (int j = 0; j < flatOrigin.size(); j++) {
			originSet.add(StmtModel.genModel(flatUpdate.get(j), j));
		}
		return atomicify(mergeID, matchSet, originSet, updateSet);
	}

}
