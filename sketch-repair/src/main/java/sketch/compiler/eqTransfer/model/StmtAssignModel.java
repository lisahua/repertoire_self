/**
 * @author Lisa Jun 10, 2016 StmtAssignModel.java 
 */
package sketch.compiler.eqTransfer.model;

public class StmtAssignModel extends StmtModel {

	String lhsSymbol;
	String lhsName;
	String rhsSymbol;
	String rhsName;
	String opString;

	public StmtAssignModel(String origin) {
		super(origin);
		lhsSymbol = tokens[1];
		lhsName = tokens[2];
		opString = tokens[3];
		rhsSymbol = tokens[4];
		rhsName = tokens[5];
	}

	public String getLhsSymbol() {
		return lhsSymbol;
	}

	public void setLhsSymbol(String lhsSymbol) {
		this.lhsSymbol = lhsSymbol;
	}

	public String getLhsName() {
		return lhsName;
	}

	public void setLhsName(String lhsName) {
		this.lhsName = lhsName;
	}

	public String getRhsSymbol() {
		return rhsSymbol;
	}

	public void setRhsSymbol(String rhsSymbol) {
		this.rhsSymbol = rhsSymbol;
	}

	public String getRhsName() {
		return rhsName;
	}

	public void setRhsName(String rhsName) {
		this.rhsName = rhsName;
	}

	public String getOpString() {
		return opString;
	}

	public void setOpString(String opString) {
		this.opString = opString;
	}

}
