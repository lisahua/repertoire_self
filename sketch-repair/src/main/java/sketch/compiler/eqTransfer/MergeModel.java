/**
 * @author Lisa Jun 14, 2016 MergeModel.java 
 */
package sketch.compiler.eqTransfer;

import java.util.ArrayList;
import java.util.List;

import sketch.compiler.eqTransfer.model.StmtAssignModel;
import sketch.compiler.eqTransfer.model.StmtModel;

public class MergeModel {

	List<StmtModel> eliminatedModel = new ArrayList<StmtModel>();
	StmtAssignModel newModel;

	public MergeModel(StmtAssignModel assign) {
		newModel = assign;
	}

	public List<StmtModel> getElminatedModel() {
		return eliminatedModel;
	}

	public void setElminatedModel(List<StmtModel> elminatedModel) {
		this.eliminatedModel = elminatedModel;
	}

	public StmtAssignModel getNewModel() {
		return newModel;
	}

	public void setNewModel(StmtAssignModel model) {
		newModel = model;
	}

	public MergeModel addNewModel(StmtAssignModel newLine) {
		eliminatedModel.add(newModel);
		newModel = newLine;
		return this;
	}

	public List<Integer> getEliminatedID() {
		List<Integer> list = new ArrayList<Integer>();
		for (StmtModel model : eliminatedModel) {
			list.add(model.getLocation());
		}
		return list;
	}

	public String toString() {
		return newModel.toString() + "," + eliminatedModel;
	}
}
