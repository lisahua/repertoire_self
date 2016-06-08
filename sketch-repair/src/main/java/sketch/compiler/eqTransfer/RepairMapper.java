/**
 * @author Lisa Jun 6, 2016 RepairMapper.java 
 */
package sketch.compiler.eqTransfer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;

public class RepairMapper {
	private static Program origin = null;
	private static HashMap<String, Function> originMap = new HashMap<String, Function>();
	private static HashSet<String> funcs = null;

	public static void setOriginProgram(Program prog) {
		origin = prog;
	}

	public static void setChangedFuncs(HashSet<String> func) {
		if (funcs == null || funcs.size() == 0)
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
				matchStmts(originFunc, func);
			}
		}
		return prog;
	}

	private static void matchStmts(Function origin, Function update) {
		TempVarReverter revert = new TempVarReverter();
		List<String> flatOrigin = revert.visitFunction(origin);
		List<String> flatUpdate = revert.visitFunction(update);
		System.out.println("[Test match stmt origin ] ");
		for (String s : flatOrigin)
			System.out.println(s);
		System.out.println("[Test match stmt update ] ");
		for (String s : flatUpdate)
			System.out.println(s);
		editDistance(flatOrigin, flatUpdate);
	}

	private static void editDistance(List<String> flatOrigin, List<String> flatUpdate) {
		int len1 = flatOrigin.size();
		int len2 = flatUpdate.size();
		int[][] dp = new int[len1 + 1][len2 + 1];
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			String c1 = flatOrigin.get(i);
			for (int j = 0; j < len2; j++) {
				String c2 = flatUpdate.get(j);
				// if last two chars equal
				if (matchTwoFlat(c1, c2)) {
					dp[i + 1][j + 1] = dp[i][j];
					System.out.println("match " + c1 + " ---" + c2);
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
	}

	private static boolean matchTwoFlat(String origin, String update) {
		return FlattenStmtModel.matchNode(origin, update);
	}
}
