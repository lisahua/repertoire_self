/**
 * @author Lisa Mar 18, 2016 RepairTypeResolver.java 
 */
package sketch.compiler.assertionLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sketch.compiler.CandidateGenerator.multi.RepairMultiController;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public class FailAssertHandler {

	private RepairProgramController utility;
//	private RepairMultiController utility;
	private String buggyHarness = null;
	private StmtAssert failAssert = null;
	private String buggyType = null;
	private List<VarDeclEntry> fields = new ArrayList<VarDeclEntry>();

	public FailAssertHandler(final RepairProgramController utility) {
//		this.utility = utility;
	}

//	public FailAssertHandler(RepairMultiController repairMultiController) {
//		// TODO Auto-generated constructor stub
//		utility = repairMultiController;
//	}

	

	public StmtAssert findBuggyAssertion(String message) {
		int index2 = message.indexOf("at");
		int index3 = message.indexOf("(");
		String context = message.substring(index2 + 3, index3);
		System.out.println("=====fail assert ====" + context);
		return findFailAssert(context.trim());
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
					findFailField(ass);
					return ass;
				}
			}
		}
		return null;
	}

	public List<StmtAssert> getAllAsserts() {

		// TODO return all assert with same field or type
		return utility.getFuncAssertMap().get(buggyHarness);
	}

	public List<VarDeclEntry> getFailField() {

		return fields;
	}

	public StmtAssert getFailAssert() {
		return failAssert;
	}

	public String getBuggyTypeS() {
		return buggyType;
	}

	private void findFailField(StmtAssert ass) {
		String[] token = ass.toString().replace("assert", "").split("==");
		String lhs = token[0].replace("(", "").replace(")", "").trim();
		String rhs = token[1].replace("(", "").replace(")", "").trim();
		if (lhs.contains(".")) {

			fields.addAll(utility.resolveFieldChain(buggyHarness, lhs));
			buggyType = fields.get(fields.size() - 1).getTypeS();
		}
		if (rhs.contains("."))
			fields.addAll(utility.resolveFieldChain(buggyHarness, rhs));

	}
//public List<String> getBuggyFunctions() {
//	utility.
//}
}
