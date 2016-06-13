/**
 * @author Lisa Jun 11, 2016 MergeStrategy.java 
 */
package sketch.compiler.eqTransfer;

import java.util.List;

import sketch.compiler.eqTransfer.model.StmtModel;

public abstract class MergeStrategy {
	public abstract String atomicify(int start, int end, List<StmtModel> flatOrigin, List<StmtModel> flatUpdate);
}
