/**
 * @author Lisa Apr 30, 2016 AtomicRunModel.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.List;

import sketch.compiler.ast.core.stmts.Statement;

public class AtomicRunModel {
	String func;
	List<String> type;
	Statement insertStmt;
	int location;

	public AtomicRunModel(String func, List<String> type, Statement insertStmt, int loc) {
		this.func = func;
		this.type = type;
		this.insertStmt = insertStmt;
		this.location = loc;
	}

	public String toString() {
		return "[AtomicModel] " + func + "," + type + "," + insertStmt + "," + location;
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

}
