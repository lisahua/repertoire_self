/**
 * @author Lisa Mar 20, 2016 SketchEqTranfer.java 
 */
package sketch.compiler.eqTransfer;

import java.io.BufferedReader;
import java.io.FileReader;

import sketch.compiler.ast.core.Program;

public class SketchEqTranfer {
	
private String origin = "";
private Program  prog = null;
	public SketchEqTranfer (String origin, Program prog) {
		this.origin = origin;
		this.prog = prog;
	}

	public String transferSketch(String fix) {
		try {
			BufferedReader oReader = new BufferedReader(new FileReader(origin));
			
			//I need 
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//TODO delete dest file 
		return "";
	}
	
}
