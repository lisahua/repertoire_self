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

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtAssert;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import sketch.compiler.main.other.RepairSketchOptions;
import sketch.compiler.main.other.RepairStageRunner;
import sketch.compiler.main.other.SimpleSketchFilePrinter;

public class RepairMultiController {
	private HashMap<String, List<StmtAssert>> funcAssertMap = new HashMap<String, List<StmtAssert>>();
	private HashMap<String, List<String>> funcCallMap = new HashMap<String, List<String>>();
	private HashMap<String, Function> funcMap = new HashMap<String, Function>();
	// private String buggyType = null;
	private RepairSketchOptions options;
	private LocalVariableResolver resolver;
	private Program prog;
	private int num = 0;
	private FailureAssertHandler failHandler = null;

	public RepairMultiController(final Program prog, final RepairSketchOptions options) {
		resolver = new LocalVariableResolver(prog);
		initProgram(prog);
		this.options = options;
		this.prog = prog;
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
				funcMap.put(func.getFullName(), func);
				funcAssertMap.put(func.getName(), visitor.getAsserts());
				// System.out.println("controller get func call
				// s"+visitor.getFunCallS().get(0));
				funcCallMap.put(func.getName(), visitor.getFunCallS());

				for (Parameter para : visitor.getParameter()) {
					resolver.add(para.getName(), para.getType().toString(), func.getName());
				}
				for (StmtVarDecl var : visitor.getVarDecl()) {
					Iterator<StmtVarDecl.VarDeclEntry> iterator = var.iterator();
					while (iterator.hasNext()) {
						StmtVarDecl.VarDeclEntry entry = iterator.next();
						resolver.add(entry.getName(), entry.getType().toString(), func.getName());
					}
				}
				// assignMap.put(func.getName(), visitor.getStmtAssign());
			}
		}
	}

	public boolean startRepair(String message) {
		failHandler = new FailureAssertHandler(this);
		StmtAssert failAssert = failHandler.findBuggyAssertion(message);
		if (failAssert == null) {
			System.out.println("Cannot identify failing assertion! Repair stop.");
			return false;
		}
		return runCandidates();
	}

	private boolean runCandidates() {
		List<String> types = failHandler.getBuggyTypeS();
		List<String> funcs = failHandler.getSuspFunctions();
		List<SketchTypeReplacer> replacer = new ArrayList<SketchTypeReplacer>(Arrays.asList(
				new SketchTypeExprReplacer(), new SketchTypeLoopReplacer(), new SketchTypeDependentLoopReplacer()));
		for (SketchTypeReplacer rep : replacer) {
			for (int j = funcs.size() - 1; j >= 0; j--) {
				for (int i = types.size() - 1; i >= 0; i--) {
					rep.generateCandidate(this, types.get(i), funcs.get(j));
					prog.accept(rep);
					boolean result = solveSketch((Program) rep.visitProgram(prog));
					if (result)
						return true;
				}
			}
		}
		return false;
	}

	public List<VarDeclEntry> resolveFieldChain(String func, String string) {
		return resolver.resolveFieldChain(func, string);
	}

	public String resolveFieldType(String func, String string) {
		List<VarDeclEntry> list = resolver.resolveFieldChain(func, string);
		return list.get(list.size() - 1).getTypeS();
	}

	// public Function getFuncMap(String name) {
	// return resolver.getFun(name);
	// }
	//
	public HashMap<String, List<StmtAssert>> getFuncAssertMap() {
		return funcAssertMap;
	}

	public HashMap<String, List<String>> getFuncCallMap() {
		return funcCallMap;
	}

	// public HashMap<String, List<StmtAssign>> getAssignMap() {
	// return assignMap;
	// }

	// public HashMap<String, String> getFixPerFile() {
	// return fileFixMap;
	// }

	public FailureAssertHandler getFailureHandler() {
		return failHandler;
	}

	public String getSketchFile() {
		return options.args[0];
	}

	private int getRepairBound() {
		int bound = options.repairOptions.bound;
		if (bound == 0)
			bound = 1;
		return bound;
	}

	// private List<HashSet<String>> genCandidateList(String func, String typeS)
	// {
	// return resolver.extractCandidateList(func, typeS, getRepairBound());
	// }
	//
	// private List<StringBuilder> genCandidateSetString(String func, String
	// typeS) {
	// return resolver.extractCandidateSetAsHole(func, typeS, getRepairBound());
	// }
	public StringBuilder genCandidateAllS(String func, String typeS) {
		return resolver.extractCandidateHoleAllS(func, typeS, getRepairBound());
	}

	public Program getProgram() {
		return prog;
	}

	public boolean solveSketch(Program prog) {
		System.out.println("solve sketch " + options.sktmpdir().getAbsolutePath());
		String path = options.sketchName + num++;
		try {

			new SimpleSketchFilePrinter(path).visitProgram(prog);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new RepairStageRunner(options).solveSketch(path);
	}

	// public RepairMultiController writeFile(RepairSketchInsertionReplacer
	// replacer) {
	// String path = options.sketchName + num++;
	// prog = new RepairStageRunner(options).readSketch(options.args[0]);
	// prog = (Program) replacer.visitProgram(prog);
	// try {
	// new SimpleSketchFilePrinter(path).visitProgram(prog);
	// prog = new RepairStageRunner(options).readSketch(path);
	// if (prog != null) {
	// RepairMultiController update_c = new RepairMultiController(prog,
	// options);
	// return update_c;
	// } else {
	// System.out.println("Expcetion controller null from omission field
	// locator");
	// return null;
	// }
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }

	public boolean isPrimitiveType(String func, String exp) {
		return resolver.isPrimitiveType(func, exp);
	}

	// public HashSet<Expression> instantiateField(String func, String field) {
	// return resolver.instantiateField(func, field, null);
	// }

}
