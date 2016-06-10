/**
 * @author Lisa Jun 10, 2016 StmtModel.java 
 */
package sketch.compiler.eqTransfer.model;

public abstract class StmtModel {
	int stmtType;
	String origin;
	int location;
	boolean autoGen;
	String[] tokens;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public boolean isAutoGen() {
		return autoGen;
	}

	public void setAutoGen(boolean autoGen) {
		this.autoGen = autoGen;
	}

	public int getStmtType() {
		return stmtType;
	}

	public void setStmtType(int stmtType) {
		this.stmtType = stmtType;
	}

	public StmtModel(String origin) {
		tokens = origin.split(",");
		this.origin = tokens[tokens.length - 3];
		location = Integer.parseInt(tokens[tokens.length - 2]);
		autoGen = Boolean.parseBoolean(tokens[tokens.length - 1]);
		if (tokens[0].equals("varDecl"))
			stmtType = 1;
		else if (tokens[0].equals("assign"))
			stmtType = 2;
		else if (tokens[0].equals("if"))
			stmtType = 3;
		else if (tokens[0].equals("while"))
			stmtType = 4;
		else if (tokens[0].equals("stmtExpr"))
			stmtType = 5;
		else if (tokens[0].equals("return"))
			stmtType = 6;
	}
}
