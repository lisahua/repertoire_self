/**
 * @author Lisa Mar 19, 2016 ProgramRecord.java 
 */
package sketch.compiler.bugLocator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import sketch.compiler.CandidateGenerator.LocalVariableResolver;
import sketch.compiler.CandidateGenerator.SketchRepairGenerator;
import sketch.compiler.ProgramLocator.SuspiciousFieldCollector;
import sketch.compiler.assertionLocator.AssertionLocator;
import sketch.compiler.assertionLocator.FailAssertHandler;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.main.other.RepairSketchOptions;
import sketch.compiler.passes.printers.SimpleCodePrinter;

public class RepairProgramController {
	private HashMap<String, List<StmtAssert>> funcAssertMap = new HashMap<String, List<StmtAssert>>();
	private HashMap<String, List<ExprFunCall>> funcCallMap = new HashMap<String, List<ExprFunCall>>();
	private HashMap<String, List<StmtAssign>> assignMap = new HashMap<String, List<StmtAssign>>();
	private HashMap<String, String> fileFixMap = null;
	private RepairSketchOptions options;
	private LocalVariableResolver resolver;
private Program prog;
	public RepairProgramController(Program prog, final RepairSketchOptions options) {
		resolver = new LocalVariableResolver(prog);
		initProgram(prog);
		this.options = options;
		prog.accept(new  SimpleCodePrinter());
		this.prog = prog;
	}

	private void initProgram(Program prog) {
		for (Package pkg : prog.getPackages()) {

			for (Function func : pkg.getFuncs()) {
				RepairFEFuncVisitor visitor = new RepairFEFuncVisitor();
				func.accept(visitor);
				funcAssertMap.put(func.getName(), visitor.getAsserts());
				funcCallMap.put(func.getName(), visitor.getFunCall());

				for (Parameter para : visitor.getParameter()) {
					resolver.add(para.getName(), resolver.getStruct(para.getType().toString()), func.getName());
				}
					for (StmtVarDecl var : visitor.getVarDecl()) {
					Iterator<StmtVarDecl.VarDeclEntry> iterator = var.iterator();
					while (iterator.hasNext()) {
						StmtVarDecl.VarDeclEntry entry = iterator.next();
						resolver.add(entry.getName(), resolver.getStruct(entry.getType().toString()), func.getName());
					}
				}
				assignMap.put(func.getName(), visitor.getStmtAssign());
			}
		}
	}

	public List<String> startRepair(String message) {
		FailAssertHandler failHandler = new FailAssertHandler(this);
		StmtAssert failAssert = failHandler.findBuggyAssertion(message);
		if (failAssert == null) {
			System.out.println("Cannot identify failing assertion! Repair stop.");
			return null;
		}
		AssertionLocator assertLocator = new AssertionLocator(failHandler.getAllAsserts());
		List<StmtAssert> asserts = assertLocator.findAllAsserts(failHandler.getFailField());

		SuspiciousFieldCollector suspLocator = new SuspiciousFieldCollector(this);
		suspLocator.findAllFieldsInMethod(failHandler.getFailField(), failHandler.getBuggyHarness());
		SketchRepairGenerator holeGenerator = new SketchRepairGenerator(this);
		List<String> files = holeGenerator.runSketch(suspLocator.getSuspciousAssign());

		fileFixMap = holeGenerator.getFixPerFile();
		return files;
	}

	public List<VarDeclEntry> resolveFieldChain(String func, String string) {
		return resolver.resolveFieldChain(func, string);
	}

	public Function getFuncMap(String name) {
		return resolver.getFun(name);
	}

	public HashMap<String, List<StmtAssert>> getFuncAssertMap() {
		return funcAssertMap;
	}

	public HashMap<String, List<ExprFunCall>> getFuncCallMap() {
		return funcCallMap;
	}

	public HashMap<String, List<StmtAssign>> getAssignMap() {
		return assignMap;
	}

	public HashMap<String, String> getFixPerFile() {
		return fileFixMap;
	}

	public String getSketchFile() {
		return options.args[0];
	}

	private int getRepairBound() {
		int bound = options.repairOptions.bound;
		if (bound == 0)
			bound = 3;
		return bound;
	}

	public String genCandidate(String func, String typeS) {
		// TODO Auto-generated method stub
		return resolver.extractCandidate(func, typeS, getRepairBound());
	}

	public Program getProgram() {
		return prog;
	}

}
