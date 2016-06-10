/**
 * @author Lisa Jun 10, 2016 StmtDeclModel.java 
 */
package sketch.compiler.eqTransfer.model;

public class StmtDeclModel extends StmtModel {
	String type;
	String name;

	public StmtDeclModel(String origin,int loc) {
		super(origin,loc);
		type = tokens[1];
		name = tokens[3];
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public  StmtModel parseNext(StmtModel next){
		switch (next.stmtType) {
		case 1:
		case 3:
		case 4:
			return next;
		case 2:
			StmtAssignModel assign = (StmtAssignModel) next;
			if (assign.getLhsName().contains(name))
				return this;
			else
				return next;
		case 5:
			StmtExprModel exp = (StmtExprModel) next;
			if (origin.toString().contains(exp.getExpName())) {
				return this;
			} else
				return next;
		}
		
		return null;

	}

}
