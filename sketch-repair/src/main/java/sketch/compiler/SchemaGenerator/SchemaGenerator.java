/**
 * @author Lisa Mar 24, 2016 SchemaGenerator.java 
 */
package sketch.compiler.SchemaGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchemaGenerator {
	private static List<Integer> operators = Arrays.asList(1, 2, 3, 4);

	public static List<Integer> getOperator() {
		return operators;
	}

	public static List<StringBuilder> extendCandidateHorizon(StringBuilder builder, int hbound) {
		List<StringBuilder> canList = new ArrayList<StringBuilder>();
		canList.add(builder);
		for (int i = 1; i < hbound; i++) {
			StringBuilder n_builder = new StringBuilder();
			for (int j = 1; j < hbound; j++) {
				n_builder.append("(");
				n_builder.append(builder);
				n_builder.append(")");
				n_builder.append("(+|-|*)");
			}
			n_builder.append("??");
			canList.add(n_builder);
		}
		return canList;
	}
}
