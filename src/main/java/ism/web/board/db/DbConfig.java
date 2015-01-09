package ism.web.board.db;

import ism.web.board.BoardException;
import ism.web.board.db.dao.DaoRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class DbConfig {
	private String url;
	private String user;
	private String password;
	
	private BasicDataSource ds = new BasicDataSource();
	
	private DaoRepository daoReop ;
	public DbConfig(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		
//		ds.addConnectionProperty("url", url);
//		ds.addConnectionProperty("user", user);
//		ds.addConnectionProperty("password", password);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(password);
		ds.setDefaultAutoCommit(false);
		ds.setInitialSize(10);
		ds.setDriverClassName("org.mariadb.jdbc.Driver");
		
		initDaoRepository();
		
	}
	
	private void initDaoRepository() {
		this.daoReop = new DaoRepository(this);
	}
	
	public DaoRepository getDaoRepository() {
		return daoReop;
	}
	public String getUrl() {
		return url;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
	
	public Connection getConnection(boolean autoCommit) throws BoardException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
//			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			throw new BoardException("fail to create new connection ", e);
		}
	}
	
	public void release ( Connection conn, PreparedStatement stmt, ResultSet rs) {
		if ( conn != null) {
//			try {
//				conn.clearWarnings();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if ( stmt != null) {
//			try {
//				if ( !stmt.isClosed() ) {
//					stmt.clearBatch();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			try {
//				if ( !stmt.isClosed() ) {
//					stmt.clearParameters();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			try {
//				if ( !stmt.isClosed() ) {
//					stmt.clearWarnings();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if ( rs != null ) {
//			try {
//				stmt.clearBatch();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			try {
//				stmt.clearParameters();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			try {
//				stmt.clearWarnings();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	public Object getUserDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
