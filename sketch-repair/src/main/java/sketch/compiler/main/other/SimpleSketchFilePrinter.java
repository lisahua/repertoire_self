/**
 * @author Lisa Mar 20, 2016 SimpleSketchFilePrinter.java 
 */
package sketch.compiler.main.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import sketch.compiler.passes.printers.SimpleCodePrinter;

public class SimpleSketchFilePrinter  extends SimpleCodePrinter {
	public SimpleSketchFilePrinter(String file) throws FileNotFoundException {
		this(new FileOutputStream(new File(file)), false);
	}

	public SimpleSketchFilePrinter(OutputStream os, boolean printLibraryFunctions) {
		super (os,false);
      
	}
	
}
