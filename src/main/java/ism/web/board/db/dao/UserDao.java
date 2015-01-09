package ism.web.board.db.dao;

import ism.web.board.db.DbConfig;
import ism.web.board.model.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {
	private DbConfig config ;
	public UserDao(DbConfig config) {
		this.config = config;
	}

	/**
	 * ResultSet에서 UserVO 인스턴스를 생성함.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private UserVO readUser(ResultSet rs) throws SQLException {
		int seq = rs.getInt("seq");
		String userId = rs.getString("userid");
		String nickName = rs.getString("nickname");
		String email = rs.getString("email");
		String password = rs.getString("password");
		String when_joined = rs.getString("when_joined");
		
		UserVO user = new UserVO(seq, userId, nickName, email, password, when_joined);
		return user;
	}
	
	@Override
	public List<UserVO> findAll() throws DaoException {
		// "moms", "맘이", "mom@naver.com", "mmmm"
		String sql = "SELECT seq, userid, nickname, email, password, when_joined FROM users";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = config.getConnection(false);
		try {
			List<UserVO> users = new ArrayList<UserVO>();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while ( rs.next()) {
				users.add( readUser(rs));
			}
			
			return users;
		} catch (SQLException e) {
			throw new DaoException("[error 5004] fail to read all users", e);
		} finally {
			config.release(conn, stmt, null);
		}
	}

	public UserVO findBySeq ( int seq) throws DaoException {
		String sql = "SELECT seq, userid, nickname, email, password, when_joined FROM users WHERE seq =  ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = config.getConnection(false);
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, seq);
			
			rs = stmt.executeQuery();
			if ( rs.next()) {
				UserVO user = readUser(rs);
				return user;
			} else {
				return null ;
			}
			
		} catch (SQLException e) {
			throw new DaoException("[error 5004] fail to read all users", e);
		} finally {
			config.release(conn, stmt, rs);
		}
	}
	
	@Override
	public UserVO findById(String userId) throws DaoException {
		String sql = "SELECT seq, userid, nickname, email, password, when_joined FROM users WHERE userId =  ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = config.getConnection(false);
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			
			rs = stmt.executeQuery();
			if ( rs.next()) {
				UserVO user = readUser(rs);
				return user;
			} else {
				return null ;
			}
			
		} catch (SQLException e) {
			throw new DaoException("[error 5004] fail to read all users", e);
		} finally {
			config.release(conn, stmt, rs);
		}
	}
	
	private int readPk( ResultSet rs) throws SQLException {
		if ( rs.next()) {
			return rs.getInt(1);
		} else {
			throw new DaoException("[error 5002] fail to read PK after inserting new User ");
		}
	}

	@Override
	public UserVO insert(UserVO newUser) throws DaoException {
		// "moms", "맘이", "mom@naver.com", "mmmm"
		String sql = "INSERT INTO users (userid, nickname, email, `password`) VALUES (?, ?, ?, ?)";
		Connection conn= null;
		PreparedStatement stmt = null;
		
		try {
			conn = config.getConnection(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, newUser.getUserId());
			stmt.setString(2, newUser.getNickName());
			stmt.setString(3, newUser.getEmail());
			stmt.setString(4, newUser.getPassword());
			
			int cnt = stmt.executeUpdate();
			
			if ( cnt != 1 ){
				throw new DaoException("[error 5003] fail to insert new user : " + newUser);
			}
			int pk = readPk( stmt.getGeneratedKeys());
			newUser.setSeq(pk);
			conn.commit();
			return newUser;
		} catch (SQLException e) {
			throw new DaoException ("[error 5001] fail to insert new user", e);
		} finally {
			config.release(conn, stmt, null);
		}
		
	}

	@Override
	public boolean exists(String userSeq) throws DaoException {
		// TODO 구현 안되었음
		return false;
	}

	@Override
	public void update(UserVO user) throws DaoException {
		// TODO 구현 안되었음

	}

	@Override
	public void delete(UserVO user) throws DaoException {
		// TODO 구현 안되었음

	}

	@Override
	public UserVO findUser(String usernm, String pw) {
		// TODO 임시로 구현함.
		return new UserVO();
	}

}
