package testutils.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class DbTestHelper{
	private static QueryExecutor executor ;

	/**
	 * 데이터 베이스 스키마(쿼리 파일)로 테스트용 데이터베이스를 초기화합니다.
	 * 
	 * @param conn
	 * @param queryFilePath
	 * @param parser
	 * @throws IOException
	 * @throws SQLException
	 */
	public void resetDB(Connection conn, String queryFilePath, SqlParser parser) throws IOException, SQLException {
		InputStream in =DbTestHelper.class
				.getClassLoader()
				.getResourceAsStream(queryFilePath);
		if ( in == null ) {
			throw new SQLException("NULL query stream. ClassLoader failed to find the given file : " + queryFilePath);
		}
		executor = new QueryExecutor(conn);
		executor.runQueries(in, parser);
		in.close();
	}
	
}
