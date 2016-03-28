/**
 * @author Lisa Mar 21, 2016 RepairSketchOptions.java 
 */
package sketch.compiler.main.other;

import sketch.compiler.main.cmdline.SketchOptions;
import sketch.util.cli.SketchCliParser;

public class RepairSketchOptions extends SketchOptions {
	public RepairOptions repairOptions = new RepairOptions();
	private static RepairSketchOptions _singleton;
	private String[] inArgs = null;

	public static RepairSketchOptions getSingleton() {
		return _singleton;
	}

	public RepairSketchOptions(String[] inArgs) {
		super(inArgs);
		this.inArgs = inArgs;
		_singleton = this;
	}

	public void parseCommandline(SketchCliParser parser) {
		super.parseCommandline(parser);
		repairOptions = new RepairOptions();
		repairOptions.parse(parser);
	}

	public RepairSketchOptions clone() {
		return new RepairSketchOptions(inArgs);

	}
}
