/**
 * @author Lisa Jun 10, 2016 StmtReturnModel.java 
 */
package sketch.compiler.eqTransfer.model;

public class StmtReturnModel extends StmtModel {

	String expSymbol;
	String expName;

	public StmtReturnModel(String origin,int loc) {
		super(origin,loc);
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
