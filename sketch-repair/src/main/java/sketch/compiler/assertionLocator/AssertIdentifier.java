/**
 * @author Lisa May 31, 2016 AssertIdentifier.java 
 */
package sketch.compiler.assertionLocator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssert;

public class AssertIdentifier extends FEReplacer {
	HashSet<StmtAssert> assList = new HashSet<StmtAssert>();
	HashMap<String, HashSet<StmtAssert>> fieldCategory = new HashMap<String, HashSet<StmtAssert>>();
	LinkedList<String> fields = new LinkedList<String>();

	public StmtAssert getAssert(String msg, Program prog) {
		this.visitProgram(prog);

		int index2 = msg.indexOf("at");
		int index3 = msg.indexOf("(");

		if (index2 > 0 && index2 + 3 < index3)
			msg = msg.substring(index2 + 3, index3).trim();
		for (StmtAssert ass : assList) {
			// System.out.println("[Asserts context ] " +
			// ass.getCx().toString());
			if (ass.getCx().toString().trim().equals(msg)) {
				// System.out.println("[Find Fix] " + msg);
				return ass;
			}
		}
		return null;
	}

	private HashSet<StmtAssert> getSimilarAsserts(StmtAssert ass) {
		String field = ass.toString().replace("(", "").replace("assert", "").replace(")", "").trim();
		field = field.substring(0, field.indexOf("==")).trim();
		if (field.contains(".")) {
			String[] tkns = field.split("\\.");
			field = tkns[tkns.length - 1];
		}
		HashSet<StmtAssert> assSet = fieldCategory.get(field);
		if (assSet == null)
			assSet = new HashSet<StmtAssert>();
		assSet.add(ass);
		return assSet;

	}
	
	public HashSet<StmtAssert> getSimilarAsserts(String field, Program prog) {
		this.visitProgram(prog);
//		HashSet<StmtAssert> assSet = fieldCategory.get(field);
//		if (assSet == null)
//			assSet = new HashSet<StmtAssert>();
//		assSet.add(ass);
		return  fieldCategory.get(field);

	}


	public Object visitStmtAssert(StmtAssert stmtAss) {
		assList.add(stmtAss);
		String field = stmtAss.toString().replace("(", "").replace("assert", "").replace(")", "").trim();
		if (!field.contains("="))
			return super.visitStmtAssert(stmtAss);
		field = field.substring(0, field.indexOf("=")).trim();
		if (field.contains(".")) {
			String[] tkns = field.split("\\.");
			field = tkns[tkns.length - 1];
		}
		
		HashSet<StmtAssert> assSet = fieldCategory.get(field);
		if (assSet == null) {
			assSet = new HashSet<StmtAssert>();
			fields.add(field);
		}
		assSet.add(stmtAss);
		fieldCategory.put(field, assSet);
		
		return super.visitStmtAssert(stmtAss);
	}

	public LinkedList<String> getAllAssertField(Program prog) {
		this.visitProgram(prog);
		return fields;
	}
}
