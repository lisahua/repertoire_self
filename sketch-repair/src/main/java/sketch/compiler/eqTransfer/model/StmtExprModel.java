/**
 * @author Lisa Jun 10, 2016 StmtWhile.java 
 */
package sketch.compiler.eqTransfer.model;

public class StmtExprModel extends StmtModel {

	String expSymbol;
	String expName;
	String funName;

	public StmtExprModel(String origin, int loc) {
		super(origin, loc);
		funName = tokens[1].trim();
		expSymbol = tokens[2].trim();
		expName = tokens[3].trim();
	}

	public String getExpSymbol() {
		return expSymbol;
	}

	public void setExpSymbol(String expSymbol) {
		this.expSymbol = expSymbol;
	}

	public String getExpName() {
		return expName;
	}

	public void setExpName(String expName) {
		this.expName = expName;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

}
