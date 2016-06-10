/**
 * @author Lisa Jun 10, 2016 StmtDeclModel.java 
 */
package sketch.compiler.eqTransfer.model;

public class StmtDeclModel extends StmtModel {
	String type;
	String name;

	public StmtDeclModel(String origin) {
		super(origin);
		type = tokens[1];
		name = tokens[3];
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
