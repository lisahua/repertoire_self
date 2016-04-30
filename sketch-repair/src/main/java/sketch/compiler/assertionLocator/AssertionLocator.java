/**
 * @author Lisa Mar 17, 2016 AssertionLocator.java 
 */
package sketch.compiler.assertionLocator;

import java.util.List;

import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.bugLocator.VarDeclEntry;
@Deprecated 
public class AssertionLocator {

	private List<StmtAssert> assertList = null;

	public AssertionLocator(List<StmtAssert> assertList) {
		this.assertList = assertList;
	}

	public List<StmtAssert> findAllAsserts(List<VarDeclEntry> fields) {
		// TODO assert with same type or field
		return assertList;
	}

	
}
