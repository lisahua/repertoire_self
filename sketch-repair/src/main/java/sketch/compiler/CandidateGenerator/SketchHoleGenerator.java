/**
 * @author Lisa Mar 20, 2016 RepairCandidateGenerator.java 
 */
package sketch.compiler.CandidateGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.compiler.ProgramLocator.AssignReplaceWrapper;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.bugLocator.RepairProgramUtility;

public class SketchHoleGenerator {
	private RepairProgramUtility utility = null;
	private HashMap<String, String> fileFixMap = new HashMap<String, String>();

	public SketchHoleGenerator(RepairProgramUtility repairProgramUtility) {
		utility = repairProgramUtility;
	}

	private List<AssignReplaceWrapper> createCandidate(HashMap<Function, List<StmtAssign>> bugAssign) {
		// TODO based on schema
		List<AssignReplaceWrapper> assignCandidate = new ArrayList<AssignReplaceWrapper>();
		for (Function func : bugAssign.keySet()) {
			for (StmtAssign assign : bugAssign.get(func)) {
				Type candType = utility.resolveFieldType(func, assign.getLHS().toString());
				if (candType != null) {
					String candidate = "$(" + candType.toString() + ");";
					assignCandidate.add(new AssignReplaceWrapper(candidate, assign, func));
				}
			}
		}

		return assignCandidate;
	}

	public List<String> runSketch(HashMap<Function, List<StmtAssign>> bugAssign) {

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
