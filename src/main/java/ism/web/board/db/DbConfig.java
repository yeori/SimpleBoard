package ism.web.board.db;

import ism.web.board.BoardException;
import ism.web.board.db.dao.DaoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.SessionFactory;

public class DbConfig {
	
	private DaoRepository daoReop ;
	
	private SessionFactory factory ;
	
	public DbConfig(DaoRepository repo, SessionFactory factory) {
		this.daoReop = repo;
		this.factory = factory;
	}
	
	public DaoRepository getDaoRepository() {
		return daoReop;
	}
	
	public SessionFactory getSqlSessionFactory() {
		return factory;
	}
	
	public Connection getConnection(boolean autoCommit) throws BoardException {
		throw new RuntimeException("mybatis 로 대체중.");
	}
	
	public void release ( Connection conn, PreparedStatement stmt, ResultSet rs) {
		if ( conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if ( stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if ( rs != null ) {
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
