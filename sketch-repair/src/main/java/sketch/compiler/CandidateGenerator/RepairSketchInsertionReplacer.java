/**
 * @author Lisa Mar 23, 2016 RepairSketchReplacer.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.ArrayList;
import java.util.List;

import sketch.compiler.ast.core.FEReplacer;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;

public class RepairSketchInsertionReplacer extends FEReplacer {
	private List<Statement> stmt = new ArrayList<Statement>();
	private String fName = null;

	// public RepairSketchInsertionReplacer(String func, List<StmtAssign>
	// bugAssign) {
	// for (Object ass : bugAssign)
	// stmt.add((Statement) ass);
	// fName = func;
	// System.out.println("RepairSketchInsertionReplacer " + func + "," +
	// bugAssign.get(0));
	// }

	public RepairSketchInsertionReplacer(String func, StmtAssign ass) {
		stmt.add(ass);
		fName = func;
	}

	public Statement getAssign() {
		return stmt.get(0);
	}

	public Object visitProgram(Program prog) {
		// List<sketch.compiler.ast.core.Package> pkgs =prog.getPackages();
		int i = 0, j = 0;
		for (; i < prog.getPackages().size(); i++) {
			sketch.compiler.ast.core.Package pkg = prog.getPackages().get(i);
			for (; j < pkg.getFuncs().size(); j++) {
				Function func = pkg.getFuncs().get(j);
				if (func.getName().equals(fName)) {
					Statement body = func.getBody();
					List<Statement> list = ((StmtBlock) body).getStmts();
					List<Statement> allSList = new ArrayList<Statement>();
					allSList.addAll(list);
					Statement last = allSList.get(allSList.size() - 1);
					if (last.toString().contains("return")) {
						last = allSList.remove(allSList.size() - 1);
						allSList.addAll(stmt);
						allSList.add(last);
					} else {
						allSList.addAll(stmt);
					}
					// System.out.println("=====replacer ===" +
					// allSList.get(allSList.size() - 1));
					StmtBlock block = new StmtBlock(allSList);
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

}
