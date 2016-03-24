/**
 * @author Lisa Mar 22, 2016 CandidateWrapper.java 
 */
package sketch.compiler.CandidateGenerator;

import java.util.HashSet;

public class CandidateWrapper {

	private String type;
	private HashSet<String> values = new HashSet<String>();
	private String rootString = null;
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
		if (rootString == null)
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

	public String getValueString() {
		if (values.size() == 0)
			return "";
		String rtn = "";
		for (String s : values) {
			rtn += s + "|";
		}
		return rtn;
	}

	public void setRootString(String value) {
		rootString = value;
	}

	public void setRootStringList(HashSet<String> value) {
		roots = value;
	}

	public String getRootString() {
		return rootString;
	}

	public String toString() {
		if (rootString != null)
			return rootString;
		switch (values.size()) {
		case 0:
			return "";
		case 1:
			for (String s : values) {
				return "." + s;
			}
		}
		String rtn = "";
		for (String s : values) {
			rtn += "." + s + "|";
		}
		return rootString + rtn;
	}
}
