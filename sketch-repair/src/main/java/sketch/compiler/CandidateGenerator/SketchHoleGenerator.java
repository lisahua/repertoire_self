/**
 * @author Lisa Mar 20, 2016 RepairCandidateGenerator.java 
 */
package sketch.compiler.CandidateGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.ProgramLocator.AssignReplaceWrapper;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.bugLocator.RepairProgramController;
import sketch.compiler.bugLocator.VarDeclEntry;

public class SketchHoleGenerator {
	private RepairProgramController utility = null;
	private HashMap<String, String> fileFixMap = new HashMap<String, String>();

	public SketchHoleGenerator(RepairProgramController repairProgramUtility) {
		utility = repairProgramUtility;
	}

	private List<AssignReplaceWrapper> createCandidate(HashMap<String, List<StmtAssign>> bugAssign) {
		// TODO based on schema
		for (String func:bugAssign.keySet()) {
			for (StmtAssign assign: bugAssign.get(func))
				System.out.println("===createCandidate check==="+func+","+assign.toString());
		}
		
		List<AssignReplaceWrapper> assignCandidate = new ArrayList<AssignReplaceWrapper>();
		for (String func : bugAssign.keySet()) {
			for (StmtAssign assign : bugAssign.get(func)) {
				System.out.println("===createCandidate ==="+func+","+assign.toString());
				List<VarDeclEntry> candType = utility.resolveFieldChain(func, assign.getLHS().toString());
				if (candType != null) {
					VarDeclEntry decl = candType.get(candType.size()-1);
					String candidate = "$(" + decl.getTypeS() + ");";
					assignCandidate.add(new AssignReplaceWrapper(candidate, assign, func));
				}
			}
		}

		return assignCandidate;
	}

	public List<String> runSketch(HashMap<String, List<StmtAssign>> bugAssign) {

		List<AssignReplaceWrapper> assignLine = createCandidate(bugAssign);
		int index = 0;
		List<String> files = new ArrayList<String>();
		String path = utility.getSketchFile();
		for (AssignReplaceWrapper replace : assignLine) {
			try {
				String f = path + index++;
				files.add(f);
				PrintWriter writer = new PrintWriter(f);
				BufferedReader reader = new BufferedReader(new FileReader(utility.getSketchFile()));
				String msg = replace.getAssign().getCx().toString();
				msg = msg.substring(msg.lastIndexOf(":") + 1).trim();
				int id = Integer.parseInt(msg);
				String line = "";
				int count = 0;
				while ((line = reader.readLine()) != null) {
					if (++count == id) {
						String l = line.substring(0, line.indexOf("=") + 1);
						writer.println(l + replace.getReplace());
						
						fileFixMap.put(f, l+replace.getReplace()+" at "+replace.getAssign().getCx());
					} else
						writer.println(line);
				}
				reader.close();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return files;
	}
	
	public HashMap<String,String> getFixPerFile() {
//		for (String key : fileFixMap.keySet())
//			System.out.println("hole "+key + "," + fileFixMap.get(key));
		return fileFixMap;
	}
}
