/**
 * @author Lisa Mar 21, 2016 RepairSketchOptions.java 
 */
package sketch.compiler.main;

import java.io.File;
import java.util.Arrays;

import sketch.compiler.main.cmdline.SketchOptions;
import sketch.util.cli.SketchCliParser;

public class RepairSketchOptions extends SketchOptions {
	RepairOptions repairOptions = new RepairOptions();
	public RepairSketchOptions(String[] inArgs) {
		super(inArgs);
		// TODO Auto-generated constructor stub
	}
	
	public void parseCommandline(SketchCliParser parser) {
        super.parseCommandline(parser);
        repairOptions.parse(parser);
    }

}
