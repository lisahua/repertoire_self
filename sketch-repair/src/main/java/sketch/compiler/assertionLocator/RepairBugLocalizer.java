/**
 * @author Lisa Mar 9, 2016 RepairBugLocalizer.java 
 */
package sketch.compiler.assertionLocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import sketch.util.exceptions.SketchException;
import sketch.util.exceptions.SketchNotResolvedException;

public class RepairBugLocalizer {

	public static File handleSketchException(SketchException se, File sketchFile) {

		if (!(se instanceof SketchNotResolvedException)) {
			System.out.println("Repair-Sketch requires a SketchNotResolvedException to start with");
			return null;
		}
		SketchNotResolvedException excp = (SketchNotResolvedException) se;
		System.out.println("Repair: ");
//		String field = locateAssertionField(excp.getMessage(), sketchFile);
//		sketchFile = RepairCandidateGenerator.generateCandidaite(sketchFile, field);
		return sketchFile;
	}

	public  static String   findBuggyAssertion (String message, File sketchFile) {
		int index2 = message.indexOf(":");
		int index3 = message.indexOf("(");
		int line = Integer.parseInt(message.substring(index2 + 1, index3).trim());
		String field = locateAssertionField (sketchFile, line);
		return field;
	}

	private static String locateAssertionField(File file, int line) {
		//TODO
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int index = 1;
			String tmp = "";
			while (index < line && (tmp = reader.readLine())!=null) {
				index++;
			}
			reader.close();
			//parse assertion
			tmp.replace("assert ", "");
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
