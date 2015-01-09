package testutils.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * 쿼리 파일 내에서 각각의 쿼리문을 추출해서 반환합니다.
 * 
 * 하나의 쿼리문은 여러줄에 걸쳐있을 수 있기 때문에 쿼리의 종결을 나타내는 ';'를 단위로 쿼리를 잘라냅니다.
 * 
 * @author chminseo
 *
 */
public class MySqlQueryParser implements SqlParser{

	private boolean isSingleLineComment(String line) {
		return line.startsWith("--") || line.startsWith("#");
	}
	
	/**
	 * sql 파일 스트림을 읽어서 유의미한 query의 list를 반환.
	 * @param is sql file instream
	 * @return list of query to be executed
	 * @throws IOException when input stream is not valid(mostly null)
	 */
	@Override
	public List<Sql> parse(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

		String line = null;
		ArrayList<Sql> queryList = new ArrayList<Sql>();
		
		int linenum = 0;
		Sql sql = new Sql(linenum);
		
		while ((line = br.readLine()) != null) {
			if ( line.trim().length() == 0 ) {
				continue;
			}
			
			// skip single-line comment
			if ( isSingleLineComment(line.trim())) {
				continue;
			}
			line = DbUtils.rtrim(line);
			sql.addLine(line);
			linenum ++ ;
			
			if ( line.endsWith(";")) {
				queryList.add(sql);
				sql = new Sql(linenum);
			}
		}
		
		return queryList;
	}
	
}