/**
 * @author Lisa Mar 21, 2016 OutputSketchFile.java 
 */
package sketch.compiler.main.other;

import static sketch.util.DebugOut.printDebug;
import static sketch.util.DebugOut.printError;
import static sketch.util.DebugOut.printNote;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.TempVarGen;
import sketch.compiler.codegenerators.NodesToCUDA;
import sketch.compiler.codegenerators.NodesToSuperCTest;
import sketch.compiler.codegenerators.NodesToSuperCpp;
import sketch.compiler.codegenerators.NodesToSuperH;
import sketch.compiler.eqTransfer.RepairMapper;
import sketch.compiler.main.passes.OutputCCode;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.compiler.passes.lowering.EliminateMultiDimArrays;
import sketch.compiler.passes.printers.SimpleCodePrinter;
import sketch.compiler.passes.spmd.ChangeGlobalStateType;
import sketch.compiler.passes.structure.ContainsCudaCode;

public class OutputSketchCode extends OutputCCode {
	private String file = "";

	public OutputSketchCode(TempVarGen varGen, RepairSketchOptions options) {
		super(varGen, options);
		String file = options.repairOptions.outputRepair;
		if (file == null) {
			file = options.sketchName;
			file = file.substring(0, file.indexOf(".")) + ".repair";
			options.repairOptions.outputRepair = file;
		}
		this.file = file;
	}

	@Override
	public Program visitProgramInner(Program prog) {
		if (prog == null) {
			printError("Final code generation encountered error, skipping output");
			return prog;
		}

		final boolean tprintPyStyle = options.feOpts.tprintPython != null;

		Program pprog = (Program) prog
				.accept(new EliminateMultiDimArrays(!options.feOpts.killAsserts, new TempVarGen()));
		pprog = (Program) pprog.accept(new ChangeGlobalStateType());

		if (!options.feOpts.outputCode && !options.feOpts.noOutputPrint) {
			try {
//				RepairMapper.checkMapping(prog);
//				System.out.println("[check mapping]");
				prog.accept(new SimpleSketchFilePrinter(file));
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else if (!options.feOpts.noOutputPrint) {
			String resultFile = SequentialSketchMain.getOutputFileName(options);
			String hcode = (String) pprog.accept(new NodesToSuperH(resultFile));
			String ccode = (String) pprog.accept(new NodesToSuperCpp(varGen, resultFile));
			if (new ContainsCudaCode().run(prog)) {
				String cucode = (String) prog
						.accept(new NodesToCUDA(varGen, options.feOpts.outputDir + resultFile + ".cu", tprintPyStyle));
				printDebug("CUDA code", cucode);
			}
			try {
				{
					Writer outWriter = new FileWriter(options.feOpts.outputDir + resultFile + ".h");
					outWriter.write(hcode);
					outWriter.flush();
					outWriter.close();
					outWriter = new FileWriter(options.feOpts.outputDir + resultFile + ".cpp");
					outWriter.write(ccode);
					outWriter.flush();
					outWriter.close();
				}
				if (options.feOpts.outputTest) {
					String testcode = (String) pprog.accept(new NodesToSuperCTest(resultFile));
					final String outputFname = options.feOpts.outputDir + resultFile + "_test.cpp";
					Writer outWriter = new FileWriter(outputFname);
					outWriter.write(testcode);
					outWriter.flush();
					outWriter.close();
					writeRunScript(options.feOpts.outputDir + "script", resultFile, resultFile + "_test.cpp\n");
					printNote("Wrote test harness to", outputFname);
				} else {
					if (options.feOpts.outputScript) {
						writeRunScript(options.feOpts.outputDir + "script", resultFile, resultFile + "_test.cpp\n");
					}
				}
			} catch (java.io.IOException e) {
				throw new RuntimeException(e);
			}
		}
		return prog;
	}

	void writeRunScript(String name, String resultFile, String drivername) throws IOException {
		Writer outWriter2 = new FileWriter(name);
		outWriter2.write("#!/bin/sh\n");
		outWriter2.write("if [ -z \"$SKETCH_HOME\" ];\n" + "then\n"
				+ "echo \"You need to set the \\$SKETCH_HOME environment variable to be the path to the SKETCH distribution; This is needed to find the SKETCH header files needed to compile your program.\" >&2;\n"
				+ "exit 1;\n" + "fi\n");
		String clargs = "";
		for (String c : options.nativeArgs) {
			clargs += " " + c;
		}
		outWriter2.write("g++ -I \"$SKETCH_HOME/include\" " + clargs + "-o " + resultFile + " " + resultFile + ".cpp "
				+ drivername);

		outWriter2.write("./" + resultFile + "\n");
		outWriter2.flush();
		outWriter2.close();
	}
}
