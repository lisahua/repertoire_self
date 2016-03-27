/**
 * @author Lisa Mar 19, 2016 ProgramRecord.java 
 */
package sketch.compiler.bugLocator;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import sketch.compiler.CandidateGenerator.LocalVariableResolver;
import sketch.compiler.CandidateGenerator.RepairSketchInsertionReplacer;
import sketch.compiler.ProgramLocator.SuspiciousFieldCollector;
import sketch.compiler.assertionLocator.AssertionLocator;
import sketch.compiler.assertionLocator.FailAssertHandler;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.main.other.RepairSketchOptions;
import sketch.compiler.main.other.RepairStageRunner;
import sketch.compiler.main.other.SimpleSketchFilePrinter;
import sketch.compiler.passes.printers.SimpleCodePrinter;

public class RepairProgramController {
	private HashMap<String, List<StmtAssert>> funcAssertMap = new HashMap<String, List<StmtAssert>>();
	private HashMap<String, List<ExprFunCall>> funcCallMap = new HashMap<String, List<ExprFunCall>>();
	private HashMap<String, List<StmtAssign>> assignMap = new HashMap<String, List<StmtAssign>>();
	private HashMap<String, String> fileFixMap = null;
	private RepairSketchOptions options;
	private LocalVariableResolver resolver;
	private Program prog;
	private int num = 0;

	public RepairProgramController(Program prog, final RepairSketchOptions options) {
		resolver = new LocalVariableResolver(prog);
		initProgram(prog);
		this.options = options;
		prog.accept(new SimpleCodePrinter());
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

	public boolean startRepair(String message) {
		FailAssertHandler failHandler = new FailAssertHandler(this);
		StmtAssert failAssert = failHandler.findBuggyAssertion(message);
		if (failAssert == null) {
			System.out.println("Cannot identify failing assertion! Repair stop.");
			return false;
		}
		AssertionLocator assertLocator = new AssertionLocator(failHandler.getAllAsserts());
		List<StmtAssert> asserts = assertLocator.findAllAsserts(failHandler.getFailField());

		SuspiciousFieldCollector suspLocator = new SuspiciousFieldCollector(this);
		return suspLocator.findAllFieldsInMethod(failHandler.getFailField(), failHandler.getBuggyHarness());

		//
		// SketchRepairCollector holeGenerator = new
		// SketchRepairCollector(this);
		// List<String> files =
		// holeGenerator.runSketch(suspLocator.getSuspciousAssign());
		//
		// fileFixMap = holeGenerator.getFixPerFile();
		
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

	private List<HashSet<String>> genCandidateList(String func, String typeS) {
		return resolver.extractCandidateList(func, typeS, getRepairBound());
	}

	public List<StringBuilder> genCandidateSetString(String func, String typeS) {
		return resolver.extractCandidateSetAsHole(func, typeS, getRepairBound());
	}

	public Program getProgram() {
		return prog;
	}

	public boolean solveSketch(Program prog) {
		String path = options.sketchName + num++;
		try {
			new SimpleSketchFilePrinter(path).visitProgram(prog);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new RepairStageRunner(options).solveSketch(path);
	}

	public RepairProgramController writeFile(RepairSketchInsertionReplacer replacer) {

		try {
			String path = options.sketchName + num++;
			prog = (Program) replacer.visitProgram(prog);
			System.out.println("===Write after insert assigns ====");
			new SimpleCodePrinter().visitProgram(prog);
			new SimpleSketchFilePrinter(path).visitProgram(prog);

			Program prog = new RepairStageRunner(options).readSketch(path);
			RepairProgramController update_c = new RepairProgramController(prog, options);
			return update_c;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isPrimitiveType(String func, String exp) {
		return resolver.isPrimitiveType(func, exp);
	}

	public HashSet<Expression> instantiateField(String func, String field) {
		return resolver.instantiateField(func, field, null);
	}
}
