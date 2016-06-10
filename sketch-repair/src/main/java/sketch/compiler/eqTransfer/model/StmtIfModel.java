/**
 * @author Lisa Jun 10, 2016 StmtIfModel.java 
 */
package sketch.compiler.eqTransfer.model;

public class StmtIfModel extends StmtModel {

	String expSymbol;
	String expName;

	public StmtIfModel(String origin) {
		super(origin);
		expSymbol = tokens[1];
		expName = tokens[2];
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

}
