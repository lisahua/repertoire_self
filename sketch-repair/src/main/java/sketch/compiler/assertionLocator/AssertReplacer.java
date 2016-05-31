/**
 * @author Lisa May 19, 2016 AssertIsolator.java 
 */
package sketch.compiler.assertionLocator;

import java.util.HashSet;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtEmpty;

public class AssertReplacer extends FEReplacer {
	HashSet<StmtAssert> assList = null;
	HashSet<StmtAssert> correctList = new HashSet<StmtAssert>();

	public void setStmtAsserts(HashSet<StmtAssert> assList) {
		this.assList = assList;
	}

	public Object visitStmtAssert(StmtAssert stmtAss) {
		if (assList.contains(stmtAss)) {
			return super.visitStmtAssert(stmtAss);
		} else
		{
			System.out.println("[comment out faulty stmt] " + stmtAss);
			return new StmtEmpty(stmtAss.getOrigin());
		}
	}
}
