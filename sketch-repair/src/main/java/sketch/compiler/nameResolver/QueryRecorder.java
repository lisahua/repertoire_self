/**
 * @author Lisa May 29, 2016 QueryRecorder.java 
 */
package sketch.compiler.nameResolver;

import java.util.HashMap;
import java.util.HashSet;

import sketch.compiler.ast.core.typs.Type;

public class QueryRecorder {
	HashSet<QueryWrapper> queryRecords = new HashSet<QueryWrapper>();
	
	public HashMap<Type, HashSet<String>> getRecord(String func, int loc) {
		HashMap<Type, HashSet<String>> record = null;
		for (QueryWrapper query : queryRecords) {
			record = query.existNames(func, loc);
			if (record != null)
				return record;
		}
		return record;
	}
	
	public void addRecorder(String func, int loc, HashMap<Type, HashSet<String>> deviatedNames) {
		QueryWrapper query = new QueryWrapper(func, loc, deviatedNames);
		queryRecords.add(query);
	}
}
