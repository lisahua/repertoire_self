/**
 * @author Lisa Jun 6, 2016 RepairMapper.java 
 */
package sketch.compiler.eqTransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;
import sketch.compiler.eqTransfer.model.StmtModel;

public class RepairMapper {
	private static Program origin = null;
	private static HashMap<String, Function> originMap = new HashMap<String, Function>();
	private static HashSet<String> funcs = null;

	public static void setOriginProgram(Program prog) {
		origin = prog;
	}

	public static void setChangedFuncs(HashSet<String> func) {
		funcs = func;
	}

	public static Program checkMapping(Program prog) {
		parseOriginProg();
		parseUpdateProg(prog);
		return prog;
	}

	private static void parseOriginProg() {
		if (origin == null)
			return;
		for (sketch.compiler.ast.core.Package pkg : origin.getPackages()) {
			for (Function func : pkg.getFuncs()) {
				String fName = func.getName();
				int id = fName.indexOf("@");
				if (id > 0)
					fName = fName.substring(0, id);
				originMap.put(fName, func);
			}
		}
	}

	// Why I don't save atomic model: because may multiple, and change other
	// place.
	private static Program parseUpdateProg(Program prog) {
		if (prog == null)
			return prog;
		System.out.println("[parseUpdateProg] " + originMap.keySet() + "," + funcs);
		for (sketch.compiler.ast.core.Package pkg : prog.getPackages()) {
			for (Function func : pkg.getFuncs()) {
				String fName = func.getName();
				int id = fName.indexOf("@");
				if (id > 0)
					fName = fName.substring(0, id);
				if (!originMap.containsKey(fName))
					continue;
				if (funcs == null || !funcs.contains(fName))
					continue;
				Function originFunc = originMap.get(fName);
				TreeSet<StmtModel> added = matchStmts(originFunc, func);
				// TODO
				for (StmtModel model : added) {
					System.out.println("[RepairMapper] " + model);
				}
			}
		}
		return prog;
	}

	private static TreeSet<StmtModel> matchStmts(Function origin, Function update) {
		TempVarReverter revert = new TempVarReverter();
		List<String> flatOrigin = revert.visitFunction(origin);
		List<String> flatUpdate = revert.visitFunction(update);
		System.out.println("[Test match stmt origin ] ");
		int id = 0;
		for (String s : flatOrigin)
			System.out.println(id++ + s);
		System.out.println("[Test match stmt update ] ");
		id = 0;
		for (String s : flatUpdate)
			System.out.println(id++ + s);
		// editDistance(flatOrigin, flatUpdate);
		return parseCompare(flatOrigin, flatUpdate);
	}

	private static TreeSet<StmtModel> parseCompare(List<String> flatOrigin, List<String> flatUpdate) {
		int[] originMap = new int[flatOrigin.size() + 1];
		HashSet<Integer> matchedUpdate = new HashSet<Integer>();
		for (int i = 0; i < flatOrigin.size(); i++) {
			for (int j = originMap[i]; j < flatUpdate.size(); j++) {
				if (matchTwoFlat(flatOrigin.get(i), flatUpdate.get(j)) && !matchedUpdate.contains(j)) {
					if (originMap[i + 1] == 0) {
						originMap[i + 1] = j;
						matchedUpdate.add(j);
						System.out
								.println("match " + i + "," + j + "," + flatOrigin.get(i) + " ---" + flatUpdate.get(j));
//					} else {
//						
					}
				}
			}
		}
		return MergeDeclWithAssign.mergeStmts(originMap, flatOrigin, flatUpdate);
	}

	@Deprecated
	private static void editDistance(List<String> flatOrigin, List<String> flatUpdate) {
		int len1 = flatOrigin.size();
		int len2 = flatUpdate.size();
		int[][] dp = new int[len1 + 1][len2 + 1];
		int[] originMap = new int[len1 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			String c1 = flatOrigin.get(i);
			for (int j = i; j < len2; j++) {
				String c2 = flatUpdate.get(j);
				// if last two chars equal
				if (matchTwoFlat(c1, c2)) {
					dp[i + 1][j + 1] = dp[i][j];
					if (originMap[i] == 0) {
						if (i == 0)
							originMap[i] = j;
						else if (originMap[i - 1] < j) {
							originMap[i] = j;
							System.out.println("match " + i + "," + j + "," + c1 + " ---" + c2);
						}
					}
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
		for (int i = 0; i < len1 - 1; i++) {
			if (originMap[i] + 1 == originMap[i + 1])
				continue;
			List<String> list = new ArrayList<String>();
			for (int j = originMap[i]; j < originMap[i + 1]; j++) {
				list.add(flatUpdate.get(j));
			}
			// MergeDeclWithAssign.compareWithStmt(flatOrigin.get(i), list);
		}

	}

	private static boolean matchTwoFlat(String origin, String update) {
		return FlattenStmtModel.matchNode(origin, update);
	}

}
