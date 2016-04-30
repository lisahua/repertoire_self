/**
 * @author Lisa Apr 27, 2016 SketchExpressionGenerator.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.stmts.StmtReturn;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.stmts.StmtWhile;

public class SketchTypeDependentLoopReplacer extends SketchTypeLoopReplacer {
	Statement insertRecur(Statement origin, HashSet<String> existVar, HashSet<String> definedVar) {
		isBuggyTypeStmt = false;
		if (origin instanceof StmtBlock) {
			return insertBlock((StmtBlock) origin, existVar, definedVar);
		} else if (origin instanceof StmtIfThen) {
			return insertIfThen((StmtIfThen) origin, existVar, definedVar);
		} else if (origin instanceof StmtWhile) {
			return insertLoop((StmtWhile) origin, existVar, definedVar);
		} else if (origin instanceof StmtVarDecl) {
			definedVar.add(((StmtVarDecl) origin).getName(0));
		} else if (origin instanceof StmtAssign) {
			StmtAssign assign = (StmtAssign) origin;
			String type = controller.resolveFieldType(funcName, assign.getLHS().toString());

			if (type.equals(buggyType) && resolveLHS(existVar, definedVar)) {
				isBuggyTypeStmt = true;
				return origin;
				// return insertStmt;
			} else {
				// System.out.println("replacer tell me why not loop " + assign
				// + "," + buggyType + ","
				// + resolveLHS(existVar, definedVar));
			}
		}
		return origin;
	}

	StmtBlock insertBlock(StmtBlock block, HashSet<String> existVar, HashSet<String> definedVar) {
		List<Statement> allSList = new ArrayList<Statement>();
		List<Statement> list = ((StmtBlock) block).getStmts();
		int typeCount = 0;
		List<Statement> tmpList = new ArrayList<Statement>();
		Statement lastReturn = null;
		for (int i = 0; i < list.size() - 1; i++) {
			Statement rtn = insertRecur(list.get(i), existVar, definedVar);
			if (isBuggyTypeStmt) {
				typeCount++;
				tmpList.add(rtn);
			} else
				allSList.add(rtn);
		}
		Statement rtn = insertRecur(list.get(list.size() - 1), existVar, definedVar);
		if (isBuggyTypeStmt) {
			typeCount++;
			tmpList.add(rtn);
		} else if (rtn instanceof StmtReturn)
			lastReturn = rtn;
		else
			allSList.add(rtn);

		if (typeCount > 1) {
			allSList.add(insertStmt);
		}
		if (lastReturn != null)
			allSList.add(lastReturn);

		return new StmtBlock(allSList);
	}
}
