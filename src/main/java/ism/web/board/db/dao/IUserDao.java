package ism.web.board.db.dao;

import ism.web.board.model.UserVO;

import java.util.List;

public interface IUserDao {
	public List<UserVO> findAll() throws DaoException;
	/**
	 * pk인 seq로 user를 조회함.
	 * @param seq
	 * @return
	 * @throws DaoException
	 */
	public UserVO findBySeq(int seq) throws DaoException ;
	/**
	 * userId 로 user를 조회함.
	 * @param userId
	 * @return
	 * @throws DaoException
	 */
	public UserVO findById(String userId) throws DaoException;
	/**
	 * 새로운 user를 추가함.
	 * @param newUser
	 * @return
	 * @throws DaoException
	 */
	public UserVO insert ( UserVO newUser) throws DaoException ;
	
	public boolean exists(String userSeq) throws DaoException;
	
	public void update(UserVO user) throws DaoException;
	
	public void delete(UserVO user) throws DaoException;
	
	public UserVO findUser(String usernm, String pw);
	public boolean existsUserId(String userId);
}
