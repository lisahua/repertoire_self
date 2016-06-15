/**
 * @author Lisa Jun 10, 2016 StmtModel.java 
 */
package sketch.compiler.eqTransfer.model;

public class StmtModel implements Comparable {
	int stmtType;
	String origin;
	int location;
	boolean autoGen;
	String[] tokens;
	String updated;
	String initString;

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

	public StmtModel(String origin, int loc) {
		initString = origin;
		tokens = origin.split(",");
		this.origin = tokens[tokens.length - 3];
		location = loc;
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

	public static StmtModel genModel(String line, int loc) {
		String token = line.split(",")[0];
		if (token.equals("varDecl"))
			return new StmtDeclModel(line, loc);
		else if (token.equals("assign"))
			return new StmtAssignModel(line, loc);
		else if (token.contains("if"))
			return new StmtIfModel(line, loc);
		else if (token.contains("while"))
			return new StmtWhile(line, loc);
		else if (token.equals("stmtExpr"))
			return new StmtExprModel(line, loc);
		else if (token.equals("return"))
			return new StmtReturnModel(line, loc);
		return null;
	}

	public StmtModel parse(StmtModel next) {
		return parseNext(next);
	}

	public StmtModel parseNext(StmtModel next) {
		return next;
	}

	@Override
	public int compareTo(Object o) {
		StmtModel model = (StmtModel) o;
		return location - model.location;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	 public String toString() {
	 return location + "," + updated + "," + origin;
	 }

	public String getInitString() {
		return initString;
	}

	public void setInitString(String initString) {
		this.initString = initString;
	}
	 
	 
}
