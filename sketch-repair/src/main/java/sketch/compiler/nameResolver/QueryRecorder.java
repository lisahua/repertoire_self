/**
 * @author Lisa May 29, 2016 QueryRecorder.java 
 */
package sketch.compiler.nameResolver;

import java.util.HashSet;

import sketch.compiler.ast.core.typs.Type;

public class QueryRecorder {
	HashSet<QueryWrapper> queryRecords = new HashSet<QueryWrapper>();
	
	public StringBuilder getRecord(String func, int loc,Type type) {
		StringBuilder record = null;
		for (QueryWrapper query : queryRecords) {
			record = query.existNames(func, loc,type);
			if (record != null)
				return record;
		}
		return record;
	}
	
	public void addRecorder(String func, int loc, Type type, StringBuilder builder) {
		QueryWrapper query = new QueryWrapper(func, loc,type, builder);
		queryRecords.add(query);
	}
}
