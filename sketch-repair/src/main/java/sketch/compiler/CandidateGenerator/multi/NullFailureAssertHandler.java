/**
 * @author Lisa Mar 18, 2016 RepairTypeResolver.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sketch.compiler.ast.core.stmts.StmtAssert;

public class NullFailureAssertHandler extends FailureAssertHandler {
	private List<String> funcs = new ArrayList<String>();
	private List<String> allTypes = new ArrayList<String>();

	public NullFailureAssertHandler(RepairMultiController repairMultiController) {
		super(repairMultiController);
		for (String s : utility.getFunctionNameMap().keySet())
			funcs.add(s);

		for (String s : utility.getAllStructNames())
			allTypes.add(s);
		allTypes.add("int");
	}

	public List<String> getSuspFunctions() {
		return funcs;
	}

	public StmtAssert findBuggyAssertion(String message) {
		return null;
	}

	public List<String> getBuggyTypeS() {

		return allTypes;
	}

}
