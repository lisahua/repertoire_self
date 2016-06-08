/**
 * @author Lisa Jun 7, 2016 TempVarReverter.java 
 */
package sketch.compiler.eqTransfer;

import java.util.ArrayList;
import java.util.List;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.stmts.StmtWhile;

public class TempVarReverter {
	private Function origin = null;
	public String funcName = "";
	private int count = 0;

	public TempVarReverter(Function origin) {
		this.origin = origin;
		funcName = origin.getName();
		if (funcName.contains("@"))
			funcName = funcName.substring(0, funcName.indexOf("@"));
	}

	public TempVarReverter() {

	}

	public List<String> visitFunction(Function func) {
		List<String> stmtS = recurMatch(func.getBody());

		return stmtS;
	}

	List<String> recurMatch(Statement stmt) {
		List<String> stmtS = new ArrayList<String>();
		if (stmt instanceof StmtBlock) {
			stmtS.addAll(matchBlock((StmtBlock) stmt));
		} else if (stmt instanceof StmtIfThen) {
			count++;
			stmtS.addAll(matchIfThen((StmtIfThen) stmt));
		} else if (stmt instanceof StmtWhile) {
			count++;
			stmtS.addAll(matchLoop((StmtWhile) stmt));
		} else {
			stmtS.add(new FlattenStmtModel(stmt,++count).getFlatS());
		} 
		return stmtS;
	}

	List<String> matchIfThen(StmtIfThen stmt) {
		List<String> stmtS = new ArrayList<String>();
		FlattenStmtModel model = new FlattenStmtModel(stmt,++count);
		stmtS.add(model.getFlatS());
		stmtS.addAll(recurMatch(stmt.getCons()));
		stmtS.addAll(recurMatch(stmt.getAlt()));
		stmtS.add("endif," + model.getFlatS().substring(model.getFlatS().indexOf(",")));
		return stmtS;
	}

	List<String> matchLoop(StmtWhile loop) {
		List<String> stmtS = new ArrayList<String>();
		FlattenStmtModel model = new FlattenStmtModel(loop,++count);
		stmtS.add(model.getFlatS());
		stmtS.addAll(recurMatch(loop.getBody()));
		stmtS.add("endwhile," +model.getFlatS().substring(model.getFlatS().indexOf(",")));
		return stmtS;
	}

	List<String> matchBlock(StmtBlock block) {
		List<String> stmtS = new ArrayList<String>();
		List<Statement> list = ((StmtBlock) block).getStmts();
		for (int i = 0; i < list.size(); i++) {
			stmtS.addAll(recurMatch(list.get(i)));
		}
		return stmtS;
	}

}
