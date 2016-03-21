/**
 * @author Lisa Mar 18, 2016 RepairTypeResolver.java 
 */
package sketch.compiler.assertionLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.bugLocator.RepairProgramUtility;

public class FailAssertHandler {

	private RepairProgramUtility utility;
	private Function buggyHarness = null;
	private StmtAssert failAssert = null;

	private List<FieldWrapper> fields = new ArrayList<FieldWrapper>();

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

		// TODO return all assert with same field or type
		return utility.getFuncAssertMap().get(buggyHarness);
	}

	public List<FieldWrapper> getFailField() {
		return fields;
	}

	public StmtAssert getFailAssert() {
		return failAssert;
	}

	private HashMap<String, Type> findFailField(StmtAssert ass) {
		String[] token = ass.toString().replace("assert", "").split("==");
		HashMap<String, Type> varType = utility.getFuncVarType().get(buggyHarness);
//		for (String key: varType.keySet()) {
//			System.out.println("key "+key+","+varType.get(key));
//		}
		String lhs = token[0].replace("(", "").replace(")", "").trim();
		String rhs = token[1].replace("(", "").replace(")", "").trim();
		if (lhs.contains(".")) {
			fields.addAll(utility.resolveFieldChain(buggyHarness, lhs));
		}
		if (rhs.contains("."))
			fields.addAll(utility.resolveFieldChain(buggyHarness, rhs));
		return varType;
	}

}
