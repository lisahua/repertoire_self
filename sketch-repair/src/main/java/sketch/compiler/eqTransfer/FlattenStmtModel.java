/**
 * @author Lisa Jun 7, 2016 FlattenStmtModel.java 
 */
package sketch.compiler.eqTransfer;

import sketch.compiler.ast.core.exprs.ExprBinary;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.StmtAssign;

public class FlattenStmtModel {

	private String originS = "";
	private String flatS = "";
	private String symbolLHS = "";
	private String nameLHS = "";
	private String symbolRHS = "";
	private String nameRHS = "";
	private String type = "";
	private String opString = "";
	private int location = 0;

	public FlattenStmtModel(StmtAssign assign, int loc) {
		originS = assign.toString();
		type = "assign";
		flattenStmt(assign);
		location = loc;
	
	}

	public FlattenStmtModel(Expression exp) {
		originS = exp.toString();

	}

	public FlattenStmtModel(String line) {
		String[] tokens = line.split(",");
		type = tokens[0];
		if (type.equals("stmtAssign")) {
			symbolLHS = tokens[1];
			nameLHS = tokens[2];
			symbolRHS = tokens[3];
			nameRHS = tokens[4];
			opString = tokens[5];
			originS = tokens[6];
			location = Integer.parseInt(tokens[7]);
		}
	}

	public String toString() {
		return flatS;
	}

	private void flattenStmt(StmtAssign assign) {
		String lhs = assign.getLHS().toString();
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

		symbolLHS = getExprSymbol(lhs);
		nameLHS = getExprName(lhs);
		// now I don't support operator a+b
		Expression rhs = assign.getRHS();
		if (rhs instanceof ExprBinary) {
			ExprBinary binRHS = (ExprBinary) rhs;
			symbolRHS += getExprSymbol(binRHS.getLeft().toString()) + binRHS.getOpString();
			nameRHS += getExprName(binRHS.getLeft().toString()) + binRHS.getOpString();
			symbolRHS += getExprSymbol(binRHS.getRight().toString());
			nameRHS += getExprName(binRHS.getRight().toString());
		} else {
			symbolRHS += getExprSymbol(rhs.toString());
			nameRHS += getExprName(rhs.toString());
		}
		flatS = type + "," + symbolLHS + "," + nameLHS + "," + symbolRHS + "," + nameRHS + "," + opString + ","
				+ originS+","+location;
	}

	public String getOriginS() {
		return originS;
	}

	public String getFlatS() {

		return flatS;
	}

	private String getExprSymbol(String str) {
		if (str.contains(".")) {
			return "$." + str.substring(str.indexOf(".") + 1);
		}
		return str;
	}

	private String getExprName(String str) {
		if (str.contains(".")) {
			String varName = str.substring(0, str.indexOf("."));
			return varName + str.substring(str.indexOf(".") );
		}
		return str;
	}

}
