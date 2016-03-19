/**
 * @author Lisa Mar 18, 2016 FailAssertRecord.java 
 */
package sketch.compiler.main;

import java.util.List;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.bugLocator.RepairFEFuncVisitor;

public class FailAssertRecord {

	Type type = null;
	List<String> fields = null;
	RepairFEFuncVisitor visitor=null; 
	// relax step by step
	public FailAssertRecord(RepairFEFuncVisitor visitor) {
		this.visitor = visitor;
	}

	public void findAllAssertSameType() {

	}

	public List<StmtAssert> findAllAssertSameField() {
		//find failAss field
		for (StmtAssert ass: visitor.getAsserts()) {
			
		}
		
return null;
	}

	public void findAllSameFieldOneMethod() {

	}

	public List<StmtAssert> getAllAsserts(List<String> suspFields) {
		// TODO Auto-generated method stub
		//FIXME change to same type
		return findAllAssertSameField();
		
	}

}
