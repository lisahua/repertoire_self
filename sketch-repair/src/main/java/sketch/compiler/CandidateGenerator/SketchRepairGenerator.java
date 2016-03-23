/**
 * @author Lisa Mar 23, 2016 SketchRepairGenerator.java 
 */
package sketch.compiler.CandidateGenerator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import sketch.compiler.ProgramLocator.AssignReplaceWrapper;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.regens.ExprRegen;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;
import sketch.compiler.main.other.SimpleSketchFilePrinter;

public class SketchRepairGenerator {
	private RepairProgramController utility = null;
	private HashMap<String, String> fileFixMap = new HashMap<String, String>();

	public SketchRepairGenerator(RepairProgramController repairProgramUtility) {
		utility = repairProgramUtility;
	}

	public List<String> runSketch(HashMap<String, List<StmtAssign>> bugAssign) {

		HashMap<String, List<StmtAssign>> assignLine = createCandidate(bugAssign);
		int index = 0;
		List<String> files = new ArrayList<String>();
		String path = utility.getSketchFile();
		for (Entry<String, List<StmtAssign>> f_entry : assignLine.entrySet()) {
			Function func = utility.getFuncMap(f_entry.getKey());
			for (StmtAssign replace : f_entry.getValue()) {
				RepairSketchReplacer replGen = new RepairSketchReplacer(replace);
				func.accept(replGen);
				try {
					String pth = path + index++;
					new SimpleSketchFilePrinter(pth).visitProgram(utility.getProgram());
					fileFixMap.put(pth, replace.toString());
					files.add(pth);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return files;
	}

	private HashMap<String, List<StmtAssign>> createCandidate(HashMap<String, List<StmtAssign>> bugAssign) {

		HashMap<String, List<StmtAssign>> assignCandidate = new HashMap<String, List<StmtAssign>>();
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
					System.out.println("===createCandidate ===" + func + "," +gen+","+ rep_assign + "," + assign.toString());
					
				}
			}
			assignCandidate.put(func, fixes);
		}
		return assignCandidate;
	}

	public HashMap<String, String> getFixPerFile() {
		return fileFixMap;
	}

}
