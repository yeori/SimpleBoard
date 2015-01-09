package testutils.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
/**
 * 쿼리 파일에서 각각의 쿼리를 추출해서 실행하는 역할을 합니다.
 * 
 * 
 * @author chminseo
 *
 */
public class QueryExecutor {
	public static String NL = System.getProperty("line.separator");
	
	private Connection conn ;
	public QueryExecutor(Connection conn) {
		this.conn = conn;
	}
	
//	void createDatabase(String cloneddbName) {
//		Statement stmt = null;
//		String dropSql = "DROP DATABASE IF EXISTS `" + cloneddbName + "`;";
//		String createSql = "CREATE DATABASE IF NOT EXISTS `" + cloneddbName + "`;";
//		try {
//			stmt = conn.createStatement();
//			stmt.execute(dropSql);
//			stmt.execute(createSql);
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			close(null, stmt, null);
//		}
//	}

	private void close(Connection conn, Statement stmt, ResultSet rs) {
		if ( conn != null) {
			try {
				conn.clearWarnings();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if ( stmt != null ) {
			try {
				stmt.clearWarnings();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if ( rs != null) {
			try {
				rs.clearWarnings();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 하나의 쿼리를 실행함.
	 * @param query
	 */
	public void executeQuery(String query) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(query);
//			System.out.println("Query U[" + stmt.getUpdateCount() + "] : " + query);
//			System.out.println("      " + stmt.getWarnings());
			conn.commit();
		} catch (SQLException e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			close( null, stmt, null);
		}
	}

	/**
	 * 주어진 sql file 의 스트림을 모두 실행함.
	 * @param in
	 * @throws IOException
	 * @throws SQLException
	 */
	public void runQueries(InputStream in, SqlParser parser) throws IOException, SQLException {
		List<Sql> queries = parser.parse(in);
		Iterator<Sql> itr = queries.iterator();
		while ( itr.hasNext()) {
			Sql sql = itr.next();
			executeQuery(sql.getQuery());
		}
		conn.commit();
	}
}
