package ism.web.board.db.dao;

import java.util.HashMap;
import java.util.Map;

public class DaoRepository {
	private Map<String, IDao<?>> daoMap = new HashMap<String, IDao<?>>();
	public DaoRepository () {
		
	}
	
	public IUserDao getUserDao() {
		return (IUserDao) daoMap.get("userDao");
	}
	
	public IPostingDao getPostingDao() {
		return (IPostingDao) daoMap.get("postingDao");
	}

	public <T extends IDao<?>> void registerDao(T dao) {
		daoMap.put(dao.getName(), dao);
	}
}
