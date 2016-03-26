/**
 * @author Lisa Mar 23, 2016 RepairSketchReplacer.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.List;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtInsertBlock;

public class RepairSketchInsertionReplacer extends FEReplacer {
	// HashMap<Expression, StmtAssign> repMap = new HashMap<Expression,
	// StmtAssign>();
	// HashSet<StmtAssign> isVisited = new HashSet<StmtAssign>();
	List<Statement> stmt = new ArrayList<Statement>();

	public RepairSketchInsertionReplacer(List<StmtAssign> bugAssign) {
		for (Object ass : bugAssign)
			stmt.add((Statement) ass);
	}

	public Object visitFunction(Function func) {
		// TODO where should I insert ?
		StmtInsertBlock iBlock = new StmtInsertBlock(func.getOrigin(), stmt, ((StmtBlock) func.getBody()).getStmts());
		iBlock.accept(this);
		return super.visitFunction(func);
	}
}
