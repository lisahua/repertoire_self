/**
 * @author Lisa Mar 23, 2016 SketchRepairGenerator.java 
 */
package sketch.compiler.CandidateGenerator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.regens.ExprRegen;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;
import sketch.compiler.main.other.SimpleSketchFilePrinter;

public class SketchExprGenerator extends SketchRepairGenerator {
	
	public SketchExprGenerator(RepairProgramController utility) {
		super(utility);
	}

	public List<String> runSketch(HashMap<String, List<StmtAssign>> bugAssign) {
		List<StmtAssign> assignLine = createCandidate(bugAssign);
		int index = 0;
		List<String> files = new ArrayList<String>();
		String path = utility.getSketchFile();
		for (StmtAssign f_entry : assignLine) {
			RepairSketchReplacer replGen = new RepairSketchReplacer(f_entry);
			Program prog = (Program) replGen.visitProgram(utility.getProgram());
			try {
				String pth = path + index++;
				new SimpleSketchFilePrinter(pth).visitProgram(prog);
				fileFixMap.put(pth, f_entry.toString());
				files.add(pth);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return files;
	}

	public List<StmtAssign> createCandidate(HashMap<String, List<StmtAssign>> bugAssign) {

		List<StmtAssign> assignCandidate = new ArrayList<StmtAssign>();
		for (String func : bugAssign.keySet()) {
			List<StmtAssign> fixes = new ArrayList<StmtAssign>();
			for (StmtAssign assign : bugAssign.get(func)) {
				List<VarDeclEntry> candType = utility.resolveFieldChain(func, assign.getLHS().toString());
				if (candType != null) {
					VarDeclEntry decl = candType.get(candType.size() - 1);
					Expression rhs = assign.getRHS();
					String gen = utility.genCandidate(func, decl.getTypeS());
					Expression n_rhs = new ExprRegen(rhs.getOrigin(), gen);
					StmtAssign rep_assign = new StmtAssign(assign.getLHS(), n_rhs, assign.getOp());
					fixes.add(rep_assign);
					System.out.println(
							"===createCandidate ===" + func + "," + gen + "," + rep_assign + "," + assign.toString());
				}
			}
			assignCandidate.addAll(fixes);
		}
		return assignCandidate;
	}

}
