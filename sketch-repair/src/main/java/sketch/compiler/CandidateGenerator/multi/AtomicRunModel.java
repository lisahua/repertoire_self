/**
 * @author Lisa Apr 30, 2016 AtomicRunModel.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.List;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.Statement;

public class AtomicRunModel {
	String func;
	List<String> type;
	Statement insertStmt;
	int location;
	Function insertFunction;
	boolean insertSucc = false;

	public AtomicRunModel(String func, List<String> type, Statement insertStmt, int loc) {
		this.func = func;
		this.type = type;
		this.insertStmt = insertStmt;
		this.location = loc;
	}

	public String toString() {
		String rtn = "[AtomicModel] " + func + "," + type + "," + insertStmt + "," + location + ",";
		if (insertFunction != null)
			rtn += insertFunction.getName() + "," + insertFunction.getBody();
		return rtn;
	}

	public boolean isInsertSucc() {
		return insertSucc;
	}

	public void setInsertSucc(boolean insertSucc) {
		this.insertSucc = insertSucc;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public Statement getInsertStmt() {
		return insertStmt;
	}

	public void setInsertStmt(Statement insertStmt) {
		this.insertStmt = insertStmt;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public Function getInsertFunction() {
		return insertFunction;
	}

	public void setInsertFunction(Function insertFunction) {
		// System.out.println("insert function "+insertFunction);
		this.insertFunction = insertFunction;
	}

	public AtomicRunModel clone() {
		AtomicRunModel model = new AtomicRunModel(func, type, insertStmt, location);
		model.setInsertFunction(insertFunction);
		return model;
	}
}
