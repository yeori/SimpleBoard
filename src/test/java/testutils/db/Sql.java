package testutils.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sql {
	List<String> lines = new ArrayList<String>();
	private int linenum ;
	
	public Sql ( int linenum ) {
		this.linenum = linenum;
	}

	public void addLine(String line) {
		lines.add(line);
	}

	public String getQuery() {
		String query = "";
		if ( lines.size() == 1 ) {
			query = lines.get(0);
		} else {
			StringBuilder sb = new StringBuilder();
			Iterator<String> itr = lines.iterator();
			while ( itr.hasNext()) {
				sb.append(itr.next() + QueryExecutor.NL);
			}
			query = sb.toString();
		}
		
		return query;
	}
	
	public String toString( ) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("line: " + linenum);
		sb.append(QueryExecutor.NL + "QUERY: [" + lines.get(0)  + " ... ");
		
		if ( lines.size() > 1) {
			sb.append(QueryExecutor.NL + "     " + lines.get(lines.size()-1));
		}
		
		sb.append("]");
		
		return sb.toString();
	}
}
