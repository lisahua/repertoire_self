/**
 * @author Lisa Mar 18, 2016 RepairTypeResolver.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sketch.compiler.ast.core.stmts.StmtAssert;

public class FailureAssertHandler {

	// private RepairProgramController utility;
	private RepairMultiController utility;
	private String buggyHarness = null;
	private StmtAssert failAssert = null;
	private List<String> buggyType = new ArrayList<String>();
	// private List<VarDeclEntry> fields = new ArrayList<VarDeclEntry>();
	private List<String> suspFunc = null;

	public FailureAssertHandler(RepairMultiController repairMultiController) {
		// TODO Auto-generated constructor stub
		utility = repairMultiController;
	}

	public List<String> getSuspFunctions() {
		return suspFunc;
	}

	public StmtAssert findBuggyAssertion(String message) {
		int index2 = message.indexOf("at");
		int index3 = message.indexOf("(");
		try {
			String context = message.substring(index2 + 3, index3);
			System.out.println("=====fail assert ====" + context);
			return findFailAssert(context.trim());
		} catch (Exception e) {
			return null;
		}
	}

	public String getBuggyHarness() {
		return buggyHarness;
	}

	private StmtAssert findFailAssert(String failAssList) {
		for (Map.Entry<String, List<StmtAssert>> entry : utility.getFuncAssertMap().entrySet()) {

			for (StmtAssert ass : entry.getValue()) {
				// fieldAssertMap.put(ass, findFailAssertTypeField(ass));
				if ((ass.getCx().toString().trim()).equals(failAssList)) {
					System.out.println(
							"====Failure found at =====" + ass.getMsg() + "," + ass.getCx() + "," + ass.toString());
					buggyHarness = entry.getKey();
					failAssert = ass;
					findFailField(entry.getKey(), ass);
					return ass;
				}
			}
		}
		return null;
	}

	// public List<StmtAssert> getAllAsserts() {
	//
	// // TODO return all assert with same field or type
	// return utility.getFuncAssertMap().get(buggyHarness);
	// }

	// public List<VarDeclEntry> getFailField() {
	//
	// return fields;
	// }

	// public StmtAssert getFailAssert() {
	// return failAssert;
	// }

	public List<String> getBuggyTypeS() {
		return buggyType;
	}

	private void findFailField(String func, StmtAssert ass) {
		String[] token = ass.toString().replace("assert", "").split("==");
		String lhs = token[0].replace("(", "").replace(")", "").trim();
		// String rhs = token[1].replace("(", "").replace(")", "").trim();
		List<VarDeclEntry> typeList = utility.resolveFieldChain(func, lhs);
		for (VarDeclEntry type : typeList) {
			if (buggyType.contains(type.getTypeS()))
				continue;
			buggyType.add(type.getTypeS());
		}
		// System.out.println("failureAssertHandler "+func);
		// System.out.println("failureAssertHandler "+func);
		suspFunc = utility.getFuncCallMap().get(func);

		// if (lhs.contains(".")) {
		//
		//// fields.addAll(utility.resolveFieldChain(buggyHarness, lhs));
		//
		// }
		// if (rhs.contains("."))
		// fields.addAll(utility.resolveFieldChain(buggyHarness, rhs));

	}
	// public List<String> getBuggyFunctions() {
	// utility.
	// }
}
