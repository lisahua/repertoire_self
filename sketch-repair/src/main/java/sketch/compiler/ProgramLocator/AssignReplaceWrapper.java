/**
 * @author Lisa Mar 20, 2016 AssignReplaceWrapper.java 
 */
package sketch.compiler.ProgramLocator;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.StmtAssign;

public class AssignReplaceWrapper {

	private String replace;
	private StmtAssign assign;
	private Function func;

	public AssignReplaceWrapper(String rep, StmtAssign assign, Function func) {
		replace = rep;
		this.assign = assign;
	}

	public String getReplace() {
		return replace;
	}

	public StmtAssign getAssign() {
		return assign;
	}

	public Function getFunc() {
		return func;
	}

}
