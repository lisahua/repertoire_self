/**
 * @author Lisa Jun 7, 2016 FlattenStmtModel.java 
 */
package sketch.compiler.eqTransfer;

import sketch.compiler.ast.core.exprs.ExprBinary;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtExpr;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.stmts.StmtReturn;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.stmts.StmtWhile;

public class FlattenStmtModel {
	private String originS = "";
	private String flatS = "";
	private String type = "";
	private int location = 0;
	private boolean autoGen = false;

	public FlattenStmtModel(Statement stmt, int loc) {
		if (stmt == null)
			return;
		originS = stmt.toString();

		location = loc;
		if (stmt instanceof StmtVarDecl) {
			type = "varDecl";
			flatS = type + "," + flattenStmt((StmtVarDecl) stmt) + "," + originS + "," + location + "," + autoGen;
		} else if (stmt instanceof StmtAssign) {
			type = "assign";
			flatS = type + "," + flattenStmt((StmtAssign) stmt) + "," + originS + "," + location + "," + autoGen;
		} else if (stmt instanceof StmtExpr) {
			type = "stmtExpr";
			flatS = type + "," + flattenStmt(((StmtExpr) stmt).getExpression()) + "," + originS + "," + location + ","
					+ autoGen;
		} else if (stmt instanceof StmtReturn) {
			type = "return";
			flatS = type + "," + flattenStmt(((StmtReturn) stmt).getValue()) + "," + originS + "," + location + ","
					+ autoGen;
		}
	}

	public FlattenStmtModel(StmtAssign assign, int loc) {
		originS = assign.toString();
		type = "assign";
		location = loc;
		flatS = type + "," + flattenStmt(assign) + "," + originS + "," + location + "," + autoGen;
	}

	public FlattenStmtModel(StmtIfThen exp, int loc) {
		originS = exp.getCond().toString();
		location = loc;
		type = "if";
		flatS = type + "," + flattenStmt(exp.getCond()) + "," + originS + "," + location + "," + autoGen;
	}

	public FlattenStmtModel(StmtWhile exp, int loc) {
		originS = exp.getCond().toString();
		location = loc;
		type = "while";
		flatS = type + "," + flattenStmt(exp.getCond()) + "," + originS + "," + location + "," + autoGen;
	}

	public String toString() {
		return flatS;
	}

	private String flattenStmt(Expression exp) {
		if (exp == null)
			return "";
		String symbolRHS = "";
		String nameRHS = "";
		if (exp instanceof ExprBinary) {
			ExprBinary binRHS = (ExprBinary) exp;
			symbolRHS += getExprSymbol(binRHS.getLeft().toString()) + binRHS.getOpString();
			nameRHS += getExprName(binRHS.getLeft().toString()) + binRHS.getOpString();
			symbolRHS += getExprSymbol(binRHS.getRight().toString());
			nameRHS += getExprName(binRHS.getRight().toString());
		} else {
			symbolRHS += getExprSymbol(exp.toString());
			nameRHS += getExprName(exp.toString());
		}
		return symbolRHS + "," + nameRHS;

	}

	private String flattenStmt(StmtVarDecl stmt) {
		String name = stmt.getName(0);
		String symbolLHS = getExprSymbol(name);
		String nameLHS = getExprName(name);
		String type = stmt.getType(0).toString();
		if (type.contains("@"))
			type = type.substring(0, type.indexOf("@"));
		return type + "," + symbolLHS + "," + nameLHS;
	}

	private String flattenStmt(StmtAssign assign) {
		String opString = "";
		// String theOp = "";
		switch (assign.getOp()) {
		case 0:
			opString = "=";
			break;
		case ExprBinary.BINOP_ADD:
			opString = "+=";
			break;
		case ExprBinary.BINOP_SUB:
			opString = "-=";
			break;
		case ExprBinary.BINOP_MUL:
			opString = "*=";
			break;
		case ExprBinary.BINOP_DIV:
			opString = "/=";
			break;
		}
		return flattenStmt(assign.getLHS()) + "," + opString + "," + flattenStmt(assign.getRHS());
	}

	public String getOriginS() {
		return originS;
	}

	public String getFlatS() {
		return flatS;
	}

	private String getExprSymbol(String str) {
		if (str.contains(".")) {
			return "$" + str.substring(str.indexOf("."));
		}
		return "$";
	}

	private String getExprName(String str) {
		String varName = str;
		while (varName.startsWith("_")) {
			autoGen = true;
			// FIXME may be __out
			varName = varName.substring(1);
		}
		if (varName.contains("_")) {
			varName = varName.substring(0, varName.indexOf("_"));
		}
		if (str.contains(".")) {
			return varName + str.substring(str.indexOf("."));
		}
		return varName;
	}

	public static boolean matchNode(String origin, String update) {
		String[] orgTkn = origin.split(",");
		String[] updateTkn = update.split(",");
		if (!orgTkn[0].equals(updateTkn[0]))
			return false;
		else if (orgTkn.length != updateTkn.length)
			return false;
		else
			return matchToken(orgTkn, updateTkn);
	}

	private static boolean matchToken(String[] origin, String[] update) {
		int count = 0;

		for (int i = 1; i < origin.length - 2; i++) {
			if (origin[i].equals(update[i]))
				count++;
		}
		if (origin[0].equals("assign"))
			return count > 3;
		else
			return count > 2;
	}

}
