/**
 * @author Lisa Jun 7, 2016 TempVarReverter.java 
 */
package sketch.compiler.eqTransfer;

import java.util.ArrayList;
import java.util.List;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtExpr;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.stmts.StmtReturn;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.stmts.StmtWhile;

public class TempVarReverter  {
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
//		String fName = func.getName();
//		if (fName.contains("@"))
//			fName = fName.substring(0, fName.indexOf("@"));
//		if (!fName.equals(funcName))
//			return null;
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
		} else if (stmt instanceof StmtVarDecl) {
			count++;
			StmtVarDecl decl = (StmtVarDecl) stmt;
			stmtS.add("varDecl," +decl.toString() );
		} else if (stmt instanceof StmtAssign) {
			count++;
			StmtAssign assign = (StmtAssign) stmt;
			stmtS.add(new FlattenStmtModel(assign,count).getFlatS() );
		} else if (stmt instanceof StmtExpr) {
			count++;
			stmtS.add("stmtExpr,"+stmt.toString() );
		} else if (stmt instanceof StmtReturn) {
			count++;
			stmtS.add("stmtReturn,"+stmt.toString() );
		}
		return stmtS;
	}

	List<String> matchIfThen(StmtIfThen stmt) {
		List<String> stmtS = new ArrayList<String>();
		stmtS.add("if," + stmt.getCond());
		stmtS.addAll(recurMatch(stmt.getCons()));
		stmtS.addAll(recurMatch(stmt.getAlt()));
		stmtS.add("endif," + stmt.getCond());
		return stmtS;
	}

	List<String> matchLoop(StmtWhile loop) {
		List<String> stmtS = new ArrayList<String>();
		stmtS.add("while," + loop.getCond());
		stmtS.addAll(recurMatch(loop.getBody()));
		stmtS.add("endwhile," + loop.getCond());
		return stmtS;
	}

	List<String> matchBlock(StmtBlock block) {
		List<String> stmtS = new ArrayList<String>();
		List<Statement> list = ((StmtBlock) block).getStmts();
		for (int i = 0; i < list.size(); i++)
			stmtS.addAll(recurMatch(list.get(i)));
		return stmtS;
	}

}
