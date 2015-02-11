package ism.web.board;

import javax.servlet.ServletContext;

import ism.web.board.db.DbConfig;
import ism.web.board.db.dao.DaoRepository;

public class BoardContext {
	final public static String ATT_DAO_REPOSITORY= "daoRepository";
	private DbConfig dbConfig;
	
	private int pageSize = 5;
	
	private DaoRepository repository;
	private ServletContext servletContext;
	public BoardContext ( DbConfig dbConfig, ServletContext ctx) {
		this.dbConfig = dbConfig;
		this.servletContext = ctx;
		this.repository = (DaoRepository) ctx.getAttribute(ATT_DAO_REPOSITORY);
	}
	
	public DbConfig getDbConfiguration() {
		return this.dbConfig;
	}
	
	public DaoRepository getDaoRepository() {
		return this.repository;
	}
	
	public int getPageSize() {
		return this.pageSize;
	}

	public String getServletContextPath() {
		// TODO Auto-generated method stub
		return servletContext.getContextPath();
	}
}
