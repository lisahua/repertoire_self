/**
 * @author Lisa Mar 21, 2016 RepairSketchOptions.java 
 */
package sketch.compiler.main.other;

import sketch.compiler.main.cmdline.SketchOptions;
import sketch.util.cli.SketchCliParser;

public class RepairSketchOptions extends SketchOptions {
	public RepairOptions repairOptions = new RepairOptions();
	private static RepairSketchOptions _singleton;

	public static RepairSketchOptions getSingleton() {
		return _singleton;
	}

	public RepairSketchOptions(String[] inArgs) {
		super(inArgs);
		_singleton = this;
	}

	public void parseCommandline(SketchCliParser parser) {
		super.parseCommandline(parser);
		repairOptions = new RepairOptions();
		repairOptions.parse(parser);
	}

}
