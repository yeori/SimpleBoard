package ism.web.board;

import javax.servlet.ServletContext;

import ism.web.board.db.DbConfig;
import ism.web.board.db.dao.DaoRepository;

public class BoardContext {

	private DbConfig dbConfig;
	
	private int pageSize = 5;
	
	private ServletContext servletContext;
	public BoardContext ( DbConfig dbConfig, ServletContext ctx) {
		this.dbConfig = dbConfig;
		servletContext = ctx;
	}
	
	public DbConfig getDbConfiguration() {
		return this.dbConfig;
	}
	
	public DaoRepository getDaoRepository() {
		return this.dbConfig.getDaoRepository();
	}
	
	public int getPageSize() {
		return this.pageSize;
	}

	public String getServletContextPath() {
		// TODO Auto-generated method stub
		return servletContext.getContextPath();
	}
}
