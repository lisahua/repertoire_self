/**
 * @author Lisa Mar 17, 2016 AssertionLocator.java 
 */
package sketch.compiler.bugLocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.StmtSpAssert;

public class AssertionLocator {


	public String findBuggyAssertion(String message, File sketchFile) {
		int index2 = message.indexOf("at");
		int index3 = message.indexOf("(");
		String context= message.substring(index2 + 3, index3);
		return context.trim();
	}

	private String locateAssertionField(File file, int line) {
		// TODO
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int index = 0;
			String tmp = "";
			while (index < line && (tmp = reader.readLine()) != null) {
				index++;
			}
			reader.close();
			// parse assertion
			tmp = tmp.replace("assert", "");
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	private void parseExpression(List<StmtSpAssert> failAssList) {
		for (StmtSpAssert stmt : failAssList) {
			List<Expression> exps = stmt.getAssertExprs();
			for (Expression e : exps) {
				System.out.print(e.toString() + " ");
				// TODO type resolving
			}
			System.out.println("");
		}
	}

	private List<StmtSpAssert> mapToAssertion(List<StmtSpAssert> assertions, String failAssert) {
		List<StmtSpAssert> failAssList = new ArrayList<StmtSpAssert>();
		System.out.println("map to assertion " + failAssert + " " + assertions);
		for (StmtSpAssert assertns : assertions) {
			if (assertns.toString().contains(failAssert)) {
				failAssList.add(assertns);
			} else
				System.out.println(assertns.toString());
		}

		parseExpression(failAssList);
		return failAssList;
	}

}
