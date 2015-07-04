package ism.web.board.db.dao;

import ism.web.board.db.DbConfig;
import ism.web.board.model.UserVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDao implements IUserDao {
	private DbConfig config ;
	private SessionFactory hbmFactory ;
	public UserDao(DbConfig config) {
		this.config = config;
		hbmFactory = config.getHbmFactory();
		System.out.println("hbm factory : " + hbmFactory);
	}
	
	@Override
	public List<UserVO> findAll() throws DaoException {
		// "moms", "맘이", "mom@naver.com", "mmmm"
		Session session = hbmFactory.openSession();
		Query query = session.createQuery("from UserVO");
		List<UserVO> users = query.list();
		session.close();
		return users;
	}

	public UserVO findBySeq ( int seq) throws DaoException {
		
		UserVO uservo = null;
		Session session = hbmFactory.openSession();
		Query query = session.createQuery("from UserVO where seq = :seq");
		query.setInteger("seq", seq);
		uservo = (UserVO) query.uniqueResult();
		
		return uservo;
	}
	
	@Override
	public UserVO findById(String userId) throws DaoException {
		
		UserVO user = null;
		Session session = hbmFactory.openSession();
		Query query = session.createQuery("from UserVO where userId = :uid");
		query.setString("uid", userId);
		user = (UserVO) query.uniqueResult();
		session.close();
		return user;
	}
	
	@Override
	public UserVO insert(UserVO newUser) throws DaoException {
		Session session = hbmFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(newUser);
		tx.commit();
		session.close();
		return newUser;
	}

	@Override
	public boolean exists(String userSeq) throws DaoException {
		// TODO 구현 안되었음
		return false;
	}

	@Override
	public void update(UserVO user) throws DaoException {
		Session session = hbmFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(user);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(UserVO user) throws DaoException {
		Session session = hbmFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(user);
		tx.commit();
		session.close();
	}

	@Override
	public UserVO findUser(String usernm, String pw) {
		// TODO 임시로 구현함.
		return new UserVO();
	}

}
