/**
 * @author Lisa Mar 23, 2016 RepairSketchReplacer.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.stmts.StmtIfThen;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.ast.core.stmts.StmtWhile;

public class RepairSketchInsertionReplacer extends FEReplacer {
	private Statement insert_stmt = null;
	private String fName = null;

	public RepairSketchInsertionReplacer(String func, StmtAssign ass) {

		insert_stmt = ass;
		fName = func;
	}

	public Statement getAssign() {
		return insert_stmt;
	}

	public Object visitProgram(Program prog) {
		int i = 0, j = 0;
		for (; i < prog.getPackages().size(); i++) {
			sketch.compiler.ast.core.Package pkg = prog.getPackages().get(i);
			for (; j < pkg.getFuncs().size(); j++) {
				Function func = pkg.getFuncs().get(j);
				if (func.getName().equals(fName)) {
					StmtBlock block = new StmtBlock(func.getOrigin(), putAfterDefine(func, insert_stmt));
					func = func.creator().body(block).create();
					List<Function> fList = pkg.getFuncs();
					fList.set(j, func);
					pkg = new sketch.compiler.ast.core.Package(pkg.getOrigin(), pkg.getName(), pkg.getStructs(),
							pkg.getVars(), pkg.getFuncs(), pkg.getSpAsserts());
					List<sketch.compiler.ast.core.Package> pList = prog.getPackages();
					pList.set(i, pkg);
					prog = prog.creator().streams(pList).create();
					return super.visitProgram(prog);
				}
			}
		}
		return super.visitProgram(prog);

	}

	private Statement putAfterDefine(Function func, Statement ass) {
		Statement body = func.getBody();
		List<Parameter> params = func.getParams();
		HashSet<String> existVar = new HashSet<String>();
		HashSet<String> definedVar = new HashSet<String>();
		for (Parameter p : params) {
			existVar.add(p.getName());
		}
		return insertRecur(body, ass, existVar, definedVar);
	}

	private Statement insertRecur(Statement origin, Statement ass, HashSet<String> existVar,
			HashSet<String> definedVar) {
		List<Statement> allSList = new ArrayList<Statement>();

		if (origin instanceof StmtBlock) {
			return insertBlock((StmtBlock) origin, ass, existVar, definedVar);
		} else if (origin instanceof StmtIfThen) {
			return insertIfThen((StmtIfThen) origin, ass, existVar, definedVar);
		} else if (origin instanceof StmtWhile) {
			return insertLoop((StmtWhile) origin, ass, existVar, definedVar);
		} else {
			allSList.add(origin);
			if (origin instanceof StmtVarDecl) {
				definedVar.add(((StmtVarDecl) origin).getName(0));
			} else {
				// StmtAssign assign = (StmtAssign) stmt;
				HashSet<String> lhs = getVarInStmt(origin.toString());
				// HashSet<String> rhs =
				// getVarInStmt(assign.getRHS().toString());
				if (resolveLHS(existVar, definedVar, lhs)) {
					allSList.add(ass);
					return new StmtBlock(origin.getOrigin(), origin,ass);
				}

			}
		}
		return origin;
	}

	private StmtIfThen insertIfThen(StmtIfThen stmt, Statement ass, HashSet<String> existVar,
			HashSet<String> definedVar) {
		Statement cons = insertRecur(stmt.getCons(), ass, existVar, definedVar);
		Statement alt = insertRecur(stmt.getAlt(), ass, existVar, definedVar);
		return new StmtIfThen(stmt.getOrigin(), stmt.getCond(), cons, alt);
	}

	private StmtWhile insertLoop(StmtWhile loop, Statement ass, HashSet<String> existVar, HashSet<String> definedVar) {
		Statement lps = insertRecur(loop.getBody(), ass, existVar, definedVar);
		return new StmtWhile(loop.getOrigin(), loop.getCond(), lps);
	}

	private StmtBlock insertBlock(StmtBlock block, Statement ass, HashSet<String> existVar,
			HashSet<String> definedVar) {
		List<Statement> allSList = new ArrayList<Statement>();
		List<Statement> list = ((StmtBlock) block).getStmts();
		for (Statement stmt : list) {
			allSList.add(insertRecur(stmt, ass, existVar, definedVar));
		}
		return new StmtBlock(allSList);
	}

	private HashSet<String> getVarInStmt(String str) {
		HashSet<String> vars = new HashSet<String>();
		String terms = str.replace("|", " ").replace("(", " ").replace(")", " ");
		String[] tokens = terms.split(" ");
		for (String s : tokens) {
			if (s.contains(".")) {
				String v = s.substring(0, s.indexOf("."));
				vars.add(v);
			}
		}

		// }
		return vars;
	}

	private boolean allResolved(HashSet<String> exist, HashSet<String> var) {
		if (var.size() == 0)
			return false;
		for (String s : var) {
			if (!exist.contains(s))
				return false;
		}
		return true;
	}

	// for lhs
	private boolean resolveLHS(HashSet<String> exist, HashSet<String> defined, HashSet<String> var) {
		if (var.size() == 0)
			return false;
		for (String s : var) {
			if (defined.contains(s)) {
				defined.remove(s);
				exist.add(s);
				return false;
			} else if (!exist.contains(s))
				return false;
		}

		return true;
	}
}
