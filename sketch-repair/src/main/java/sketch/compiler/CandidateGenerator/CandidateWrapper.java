/**
 * @author Lisa Mar 22, 2016 CandidateWrapper.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.HashSet;
import java.util.Set;

public class CandidateWrapper {

	private String type;
	private HashSet<String> values = new HashSet<String>();

	public CandidateWrapper(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HashSet<String> getValues() {
		return values;
	}

	public void setValues(HashSet<String> values) {
		this.values = values;
	}

	public void addValue(String s) {
		values.add(s);
	}

	public void addValue(Set<String> roots, String s) {
		for (String root : roots)
			values.add(root + "." + s);
	}


	public String toString() {
		HashSet<String> result = getValues();
		StringBuilder builder = new StringBuilder();
		for (String s : result)
			builder.append(s + ",");
		return builder.toString();
	}
}
