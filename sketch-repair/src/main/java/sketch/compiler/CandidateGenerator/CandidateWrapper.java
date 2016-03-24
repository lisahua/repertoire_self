/**
 * @author Lisa Mar 22, 2016 CandidateWrapper.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.HashSet;

public class CandidateWrapper {

	private String type;
	private HashSet<String> values = new HashSet<String>();
	private HashSet<String> roots = new HashSet<String>();

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
		if (roots.size() == 0)
			return values;
		HashSet<String> new_s = new HashSet<String>();
		for (String rt : roots)
			for (String s : values) {
				new_s.add(rt + "." + s);
			}
		return new_s;
	}

	public void setValues(HashSet<String> values) {
		this.values = values;
	}

	public void addValue(String s) {
		values.add(s);
	}

	public void setRootStringList(HashSet<String> value) {
		roots = value;
	}

	public String toString() {
		HashSet<String> result = getValues();
		StringBuilder builder = new StringBuilder();
		for (String s : result)
			builder.append(s + ",");
		return builder.toString();
	}
}
