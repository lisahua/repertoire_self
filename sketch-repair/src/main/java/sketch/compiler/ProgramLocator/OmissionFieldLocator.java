/**
 * @author Lisa Mar 21, 2016 AssignFieldLocator.java 
 */
package sketch.compiler.ProgramLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sketch.compiler.CandidateGenerator.RepairSketchInsertionReplacer;
import sketch.compiler.CandidateGenerator.RepairSketchReplacer;
import sketch.compiler.CandidateGenerator.SketchRepairCollector;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprField;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;
import sketch.compiler.passes.printers.SimpleCodePrinter;

public class OmissionFieldLocator extends SuspiciousStmtLocator {
	RepairProgramController controller;
	List<List<StmtAssign>> genAssign;

	public OmissionFieldLocator(RepairProgramController utility) {
		super(utility);
	}

	public List<StmtAssign> findSuspiciousStmtInMethod(List<VarDeclEntry> sField, String func) {
		List<StmtAssign> assigns = new ArrayList<StmtAssign>();
		HashSet<String> lhsList = new HashSet<String>();
		// VarDeclEntry suspField = sField.get(sField.size() - 1);
		for (VarDeclEntry suspField : sField) {
			System.out.println("=======Suspcious field omission field====== " + "," + func + "," + suspField);
			HashSet<Expression> fields = utility.instantiateField(func, suspField.getName());

			for (Expression exp : fields) {
				assigns.add(new StmtAssign(exp, exp, 0));
				String[] tokens = exp.toString().split("\\.");
				String rep = "";
				// TODO hacky!!
				for (String s : tokens) {
					if (s.contains("_")) {
						String[] tks = s.split("_");
						for (int i = 0; i < tks.length; i++)
							rep += tks[i] + "_";
						rep += tks[tks.length - 1];
						rep += ".";
					} else
						rep += s;
				}
				lhsList.add(rep);
				System.out.println("==omission field lhsList " + func + "," + rep);
			}
		}
		return assigns;
	}

	public boolean repairInMethod(List<VarDeclEntry> sField, String func) {
		HashSet<Expression> lhs_set = new HashSet<Expression>();
		for (VarDeclEntry decl : sField) {
			List<HashSet<String>> lhs_S = utility.genCandidateList(func, decl.getTypeS());
			for (HashSet<String> set : lhs_S) {
				for (String s : set) {
					Expression n_lhs = null;
					String[] token = s.split("\\.");
					FENode node = null;
					n_lhs = new ExprVar(node, token[0]);
					int len = token.length;
					for (int i = 1; i < len; i++) {
						n_lhs = new ExprField(null, n_lhs, token[i]);
					}
					lhs_set.add(n_lhs);
				}
			}
		}

		for (Expression exp : lhs_set) {
			System.out.println("===omission field exp lhs ==="+exp);
			StmtAssign ass = new StmtAssign(exp, exp, 0);
			controller = writeToFile(func, ass);
			String[] tokens = ass.getLHS().toString().split("\\.");
			String rep = "";
			// TODO hacky!!
			for (String s : tokens) {
				if (s.contains("_")) {
					String[] tks = s.split("_");
					for (int i = 0; i < tks.length; i++)
						rep += tks[i] + "_";
					rep += tks[tks.length - 1];
					rep += ".";
				} else
					rep += s;
			}
			List<StmtAssign> new_all_ass = new ArrayList<StmtAssign>();
			List<StmtAssign> ass_fromProg = controller.getAssignMap().get(func);
			for (StmtAssign assign : ass_fromProg) {
				 System.out.println("==omission field return assigns " +
				 func
				 + ",assign " + assign);
//				if (assign.getLHS().toString().equals(rep)) {
					new_all_ass.add(assign);
//				}
			}

			SketchRepairCollector genCollector = new SketchRepairCollector(controller);
			genAssign = genCollector.createCandidate(func, new_all_ass);
			for (List<StmtAssign> ga : genAssign) {	
				for (StmtAssign a : ga) {
					RepairSketchReplacer replGen = new RepairSketchReplacer(a);
					Program prog = (Program) replGen.visitProgram(controller.getProgram());
					System.out.println("====omission field run sketch ===" + func+" "+a);
					new SimpleCodePrinter().visitProgram(prog);
					if (controller.solveSketch(prog)) {
						System.out.println("====SketchExprGenerator === successful solve");
						return true;
					}
				}
			}
		}

		return false;
	}

	private RepairProgramController writeToFile(String func, StmtAssign assigns) {
		RepairSketchInsertionReplacer replacer = new RepairSketchInsertionReplacer(func, assigns);
		return utility.writeFile(replacer);
	}

	public boolean runSketch(List<StmtAssign> bugAssign, Program prog) {

		System.out.println("====omission field run sketch before replace==="
				+ new SimpleCodePrinter().visitProgram(controller.getProgram()));
		for (List<StmtAssign> ass : genAssign) {
			System.out.println("====omission field run sketch ===" + ass);
			for (StmtAssign a : ass) {
				RepairSketchReplacer replGen = new RepairSketchReplacer(a);
				prog = (Program) replGen.visitProgram(controller.getProgram());
				if (utility.solveSketch(prog)) {
					System.out.println("====SketchExprGenerator === successful solve");
					return true;
				}
			}
		}
		return false;
	}
}
