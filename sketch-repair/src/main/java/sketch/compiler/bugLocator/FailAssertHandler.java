/**
 * @author Lisa Mar 18, 2016 RepairTypeResolver.java 
 */
package sketch.compiler.bugLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.StmtAssert;

public class FailAssertHandler {

	private RepairProgramUtility utility;
	private Function buggyHarness = null;
	private StmtAssert failAssert = null;

	private List<String> failField = null;

	public FailAssertHandler(final RepairProgramUtility utility) {
		this.utility = utility;
	}

	public StmtAssert findBuggyAssertion(String message) {
		int index2 = message.indexOf("at");
		int index3 = message.indexOf("(");
		String context = message.substring(index2 + 3, index3);
		System.out.println("=====fail assert ====" + context);
		return findFailAssert(context.trim());
	}

	public Function getBuggyHarness() {
		return buggyHarness;
	}
	
	private StmtAssert findFailAssert(String failAssList) {
		for (Map.Entry<Function, List<StmtAssert>> entry : utility.getFuncAssertMap().entrySet()) {
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
		return utility.getFuncAssertMap().get(buggyHarness);
	}

	public List<String> getFailField() {
		return failField;
	}

	private List<String> findFailField(StmtAssert ass) {
		String[] token = ass.toString().split("=");
		String lhs = token[0].replace("(", "").replace(")", "").trim();
		String rhs = token[1].replace("(", "").replace(")", "").trim();
		List<String> suspFields = new ArrayList<String>();
		if (lhs.contains("\\."))
			suspFields.addAll(utility.resolveFieldChain(buggyHarness, lhs));
		if (rhs.contains("\\."))
			suspFields.addAll(utility.resolveFieldChain(buggyHarness, rhs));
		return suspFields;
	}


	private void generateSketchHole() {

	}

	private void generateHelperMethod() {

	}

}
