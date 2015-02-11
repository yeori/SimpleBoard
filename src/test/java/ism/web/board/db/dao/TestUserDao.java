package ism.web.board.db.dao;

import static org.junit.Assert.*;
import ism.web.board.db.DbConfig;
import ism.web.board.model.UserVO;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testutils.db.DbTestHelper;
import testutils.db.MySqlQueryParser;

public class TestUserDao {
	
	static DbConfig config ;
	static DbTestHelper helper = new DbTestHelper();
	
	Connection conn = null;

	@BeforeClass
	public static void setUpClass() throws Exception {
		InputStream in = Resources.getResourceAsStream("mybatis-junit-config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
		config = new DbConfig(null, factory);
		
	}

	@Before
	public void setUp() throws Exception {
		conn = config.getSqlSessionFactory().openSession(false).getConnection();
		helper.resetDB(conn, "junit-schema-simpleboard.sql", new MySqlQueryParser());
	}
	
	@After
	public void tearDown() throws Exception {
		config.release(conn, null, null);
	}

	@Test
	public void test_get_users() {
		IUserDao userDao = new UserDao(config);
		assertEquals ( 2, userDao.findAll().size());
		
	}
	
	@Test
	public void test_get_by_seq() {
		UserDao userDao = new UserDao(config);
		UserVO user = userDao.findBySeq(5000);
		assertUserFieldNotNull(user);
	}
	@Test
	public void test_get_UserId() {
		IUserDao userDao = new UserDao(config);
		UserVO user = null;
		user = userDao.findById("james");
		assertEquals(
				new UserVO(5000, "james", "james", "james@naver.com", "", "2014-12-22 13:25:26"), 
				user);
	}
	
	/**
	 * 존재하지않는 userId를 조회했을때
	 */
	@Test
	public void test_when_no_such_userId() {
		IUserDao userDao = new UserDao(config);
		assertNull( userDao.findById("no_such_userid") );
	}
	
	@Test
	public void test_insert_new_user() {
		//'moms', '맘이', 'mam@gmail.com', 'mmmm'
		UserDao userDao = new UserDao(config);
		UserVO user = new UserVO("moms", "맘이", "mom@naver.com", "mmmm");
		user = userDao.insert(user);
		assertEquals ( 3, userDao.findAll().size());
		assertEquals ( 5002, user.getSeq().intValue());
		assertUserFieldNotNull(user);
	}
	
	@Test
	public void test_findUser() {
		UserDao dao = new UserDao(config);
		UserVO user = dao.findUser("james", "1111");
		assertUserFieldNotNull(user);
		
		assertNull ( dao.findUser("james", "invalid pass"));
		assertNull ( dao.findUser("invalid ID", "any value"));
	}

	public static void assertUserFieldNotNull(UserVO user) {
		assertNotNull ("seq is null", user.getSeq());
		assertNotNull ("nickname is null", user.getNickName());
		assertNotNull ("password is null", user.getPassword());
		assertNotNull ("userid is null", user.getUserId());
		assertNotNull ("join date is null", user.getWhenJoined());		
	}
	
	
}
