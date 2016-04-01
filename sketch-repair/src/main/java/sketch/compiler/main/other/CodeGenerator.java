/**
 * @author Lisa Mar 29, 2016 TestNodeToJava.java 
 */
package sketch.compiler.main.other;

import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.TempVarGen;
import sketch.compiler.codeGenerator.NodesToSuperCpp;
import sketch.compiler.codeGenerator.NodesToSuperH;

public class CodeGenerator {

	public void testJavaWriter(Program prog) {
		TempVarGen varGen = new TempVarGen();
		String path = "/Users/lisahua/Documents/lisa/project/spr/git-repo/repo/sketch/linkedl/";

		// NodesToJava javaWriter = new NodesToJava(false, varGen);
		// javaWriter.visitProgram(prog);

		String hcode = (String) prog.accept(new NodesToSuperH(path + "test.h"));
		String ccode = (String) prog.accept(new NodesToSuperCpp(varGen, path + "test.cpp"));

		System.out.println("\n ==== test hcode superH =====" + hcode);

		System.out.println("\n ==== test cpp code superC =====" + ccode);
	}
}
