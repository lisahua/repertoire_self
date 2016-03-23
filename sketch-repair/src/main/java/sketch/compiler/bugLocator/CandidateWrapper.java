/**
 * @author Lisa Mar 22, 2016 CandidateWrapper.java 
 */
package sketch.compiler.bugLocator;

import java.util.HashSet;

public class CandidateWrapper {

	private String type;
	private HashSet<String> values = new HashSet<String>();
	private String rootString;

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
		String rtn = "(";
		for (String s : values) {
			rtn += "." + s + "|";
		}
		rtn.substring(0, rtn.length() - 2);
		rtn += ")?";
		return rootString+ rtn;
	}
}
