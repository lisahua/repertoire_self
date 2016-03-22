/**
 * @author Lisa Mar 20, 2016 AssignReplaceWrapper.java 
 */
package sketch.compiler.ProgramLocator;

import sketch.compiler.ast.core.stmts.StmtAssign;

public class AssignReplaceWrapper {

	private String replace;
	private StmtAssign assign;
	private String  func;

	public AssignReplaceWrapper(String rep, StmtAssign assign, String func) {
		replace = rep;
		this.assign = assign;
		this.func = func;
	}

	public String getReplace() {
		return replace;
	}

	public StmtAssign getAssign() {
		return assign;
	}

	public String getFunc() {
		return func;
	}

}
