/**
 * @author Lisa Mar 19, 2016 ProgramRecord.java 
 */
package sketch.compiler.CandidateGenerator.multi;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import sketch.compiler.CandidateGenerator.multi.candStrategy.RepairGenerator;
import sketch.compiler.assertionLocator.AssertIsolator;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.bugLocator.FailureAssertHandler;
import sketch.compiler.main.other.RepairSketchOptions;
import sketch.compiler.main.other.RepairStageRunner;
import sketch.compiler.main.other.SimpleSketchFilePrinter;
import sketch.compiler.nameResolver.BlockNameResolver;

public class RepairMultiController {
	private HashMap<String, List<StmtAssert>> funcAssertMap = new HashMap<String, List<StmtAssert>>();
	private HashMap<String, List<String>> funcCallMap = new HashMap<String, List<String>>();
	private HashMap<String, Function> funcMap = new HashMap<String, Function>();
	private HashMap<String, List<StmtAssign>> assignMap = new HashMap<String, List<StmtAssign>>();
	// private String buggyType = null;
	private RepairSketchOptions options;

	private LocalVariableResolver resolver;
	private Program prog;
	private int num = 0;
	private FailureAssertHandler failHandler = null;
	private BlockNameResolver blockResolver;
	private Program parsedProg = null;

	public RepairMultiController(final Program prog, final RepairSketchOptions options) {
		resolver = new LocalVariableResolver(prog);
		this.options = options;
		this.prog = prog;
		blockResolver = new BlockNameResolver(prog, options.repairOptions.bound);

		initProgram(prog);
	}

	public HashSet<String> getAllStructNames() {
		HashSet<String> structs = resolver.getStructNames();
		HashSet<String> structNames = new HashSet<String>();
		for (String name : structs)
			if (name.contains("@"))
				structNames.add(name.substring(0, name.indexOf("@")));
		return structNames;
	}

	private void initProgram(Program prog) {

		for (Package pkg : prog.getPackages()) {

			for (Function func : pkg.getFuncs()) {
				ProgramParseVisitor visitor = new ProgramParseVisitor();
				// RepairFEFuncVisitor visitor = new RepairFEFuncVisitor();
				func.accept(visitor);
				String name = func.getName();
				if (name.contains("@"))
					funcMap.put(name.substring(0, name.indexOf("@")), func);
				else
					funcMap.put(name, func);
				funcAssertMap.put(func.getName(), visitor.getAsserts());
				// System.out.println("controller get func call
				// s"+visitor.getFunCallS().get(0));
				funcCallMap.put(func.getName(), visitor.getFunCallS());

				for (Parameter para : visitor.getParameter()) {
					resolver.add(para.getName(), para.getType(), func.getName());
				}
				for (StmtVarDecl var : visitor.getVarDecl()) {
					Iterator<StmtVarDecl.VarDeclEntry> iterator = var.iterator();
					while (iterator.hasNext()) {
						StmtVarDecl.VarDeclEntry entry = iterator.next();
						resolver.add(entry.getName(), entry.getType(), func.getName());
					}
				}

				assignMap.put(func.getName(), visitor.getStmtAssign());
			}
		}
	}

	public boolean startRepair(String message) {
		failHandler = new FailureAssertHandler(this);
		StmtAssert failAssert = failHandler.findBuggyAssertion(message);
		if (failAssert == null) {
			failHandler = new NullFailureAssertHandler(this);
		}
		// return runCandidates();
		return runAtomicModel();
	}

	private boolean runCandidates() {
		List<String> types = failHandler.getBuggyTypeS();
		List<String> funcs = failHandler.getSuspFunctions();
		List<SketchTypeReplacer> replacer = new ArrayList<SketchTypeReplacer>(Arrays.asList(
				new SketchTypeExprReplacer(), new SketchTypeLoopReplacer(), new SketchTypeDependentLoopReplacer()));

		for (SketchTypeReplacer rep : replacer) {
			for (int j = funcs.size() - 1; j >= 0; j--) {
				// for (int i = types.size() - 1; i >= 0; i--) {
				rep.generateCandidate(this, types, funcs.get(j));
				// prog.accept(rep);
				// Program updateProg = (Program) rep.visitProgram(prog);
				String message = solveSketch((Program) rep.visitProgram(prog));

				if (message.equals(""))
					return true;
				// failHandler.findBuggyAssertion(message);
				// if (!failHandler.getBuggyTypeS().equals(types))
				// prog = updateProg;
				// }
			}
		}
		return false;
	}

	private boolean runAtomicModel() {
		RepairGenerator generator = new RepairGenerator(this);
		String res = generator.generateAtomicRunModel();

		return false;
	}

	public List<VarDeclareEntry> resolveFieldChain(String func, String string) {
		return resolver.resolveFieldChain(func, string);
	}

	public String resolveFieldType(String func, String string) {
		List<VarDeclareEntry> list = resolver.resolveFieldChain(func, string);

		return list.get(list.size() - 1).getTypeS();
	}

	public Function getFuncMap(String name) {
		return resolver.getFun(name);
	}

	public HashMap<String, Function> getFunctionNameMap() {
		return funcMap;
	}

	public HashMap<String, List<StmtAssert>> getFuncAssertMap() {
		return funcAssertMap;
	}

	public HashMap<String, List<String>> getFuncCallMap() {
		return funcCallMap;
	}

	public HashMap<String, List<StmtAssign>> getAssignMap() {
		return assignMap;
	}

	public LocalVariableResolver getNameResolver() {
		return resolver;
	}

	public FailureAssertHandler getFailureHandler() {
		return failHandler;
	}

	public String getSketchFile() {
		return options.args[0];
	}

	public int getTimeOut() {
		return options.repairOptions.timeout;
	}

	private int getRepairBound() {
		int bound = options.repairOptions.bound;
		if (bound == 0)
			bound = 1;
		return bound;
	}

	public StringBuilder genCandidateAllS(String func, int loc, String type) {
		return blockResolver.getAllCandidates(func, type, loc);
		// return resolver.extractCandidateHoleAllS(func, typeS,
		// getRepairBound());
	}

	@Deprecated
	public StringBuilder genCandidateAllS(String func, String typeS) {
		return resolver.extractCandidateHoleAllS(func, typeS, getRepairBound());
	}

	public Program getProgram() {
		return prog;
	}

	public String solveSketch(Program prog) {
		System.out.println("solve sketch " + options.sktmpdir().getAbsolutePath());
		String path = options.sketchName + ++num;
		try {

			new SimpleSketchFilePrinter(path).visitProgram(prog);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		RepairStageRunner runner = new RepairStageRunner(options);
		String res = runner.solveSketch(path);
		parsedProg = runner.getFixProg();
//		System.out.println("[controller parsedprog]"+parsedProg);
		return res;
	}

	public void deleteCandidates() {

	}

	public Program getParsedProg() {
		return parsedProg;
	}

	public boolean isPrimitiveType(String func, String exp) {
		return resolver.isPrimitiveType(func, exp);
	}

	public RepairSketchOptions getOptions() {
		return options;
	}

}
