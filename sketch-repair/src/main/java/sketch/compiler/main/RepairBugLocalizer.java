/**
 * @author Lisa Mar 9, 2016 RepairBugLocalizer.java 
 */
package sketch.compiler.main;

import java.io.File;

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
		String field = locateAssertionField(excp.getMessage(), sketchFile);
		sketchFile = RepairCandidateGenerator.generateCandidaite(sketchFile, field);
		return sketchFile;
	}

	private static String locateAssertionField(String message, File sketchFile) {
		int index2 = message.indexOf(":");
		int index3 = message.indexOf("(");
		int line = Integer.parseInt(message.substring(index2 + 1, index3).trim());
		String field = findBuggyField(sketchFile, line);
		return field;
	}

	private static String findBuggyField(File file, int line) {
		//TODO
		return "";
	}

}
