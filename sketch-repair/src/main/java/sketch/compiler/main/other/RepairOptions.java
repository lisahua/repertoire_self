package sketch.compiler.main.other;

import sketch.util.cli.CliAnnotatedOptionGroup;
import sketch.util.cli.CliParameter;

/**
 * Options for the repair, such as the bound of repair candidate generation
 * 
 */
public class RepairOptions extends CliAnnotatedOptionGroup {
    public RepairOptions() {
        super("rp", "miscellaneous repair options");
    }

    @CliParameter(help = "Changes the default repair sketch that successfully generates repair from $HOME/.sketch/$Name.sk to a different value.")
    public String repairSketch = null;

    @CliParameter(help = "Change the default bound for repair candidate generation from 3 to a different value.")
    public int bound=2;

    @CliParameter(help = "Change the default file that outputs the repaired code in Sketch from $HOME/.repair/$Name.sk to a different value")
    public String outputRepair = null;
    
    @CliParameter(help = "Change the default time out seconds  for a single repair candidate checking from 60 seconds to other value.")
    public int timeout=60;
}
