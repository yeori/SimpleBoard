package ism.web.board.db.dao;

import ism.web.board.db.DbConfig;

public class DaoRepository {
	private IUserDao userDao;
	private IPostingDao postingDao;
	
	public DaoRepository ( DbConfig config) {
		userDao = new UserDao(config);
		postingDao = new PostingDao(config);
	}
	
	public IUserDao getUserDao() {
		return userDao;
	}
	
	public IPostingDao getPostingDao() {
		return postingDao;
	}
}
