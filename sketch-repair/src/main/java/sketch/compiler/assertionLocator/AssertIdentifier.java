/**
 * @author Lisa May 31, 2016 AssertIdentifier.java 
 */
package sketch.compiler.assertionLocator;

import java.util.HashSet;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.stmts.StmtAssert;

public class AssertIdentifier extends FEReplacer {
	HashSet<StmtAssert> assList = new HashSet<StmtAssert>();

	public StmtAssert getAssert(String msg) {
		int index2 = msg.indexOf("at");
		int index3 = msg.indexOf("(");

		if (index2 > 0 && index2 + 3 < index3)
			msg = msg.substring(index2 + 3, index3).trim();
		for (StmtAssert ass : assList) {
//			System.out.println("[Asserts context ] " + ass.getCx().toString());
			if (ass.getCx().toString().trim().equals(msg)) {
//				System.out.println("[Find Fix] " + msg);
				return ass;
			}
		}
		return null;
	}

	public Object visitStmtAssert(StmtAssert stmtAss) {
		assList.add(stmtAss);
		return super.visitStmtAssert(stmtAss);
	}
}
