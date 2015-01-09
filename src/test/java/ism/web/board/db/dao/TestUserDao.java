package ism.web.board.db.dao;

import static org.junit.Assert.*;
import ism.web.board.db.DbConfig;
import ism.web.board.model.UserVO;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

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
	//TestUserDaoData data = new TestUserDaoData(null);
	@BeforeClass
	public static void setUpClass() throws Exception {
		InputStream is = TestUserDao.class.getClassLoader().getResourceAsStream("dbconn.properties");
		
		//InputStream iss = TestUserDao.class.getResourceAsStream("dbcon.properties");
		assertNotNull ("fail to find dbconn.properties", is) ;
		Properties p = new Properties();
		p.load(is);
		
//		p.getProperty("");
//		Properties props = new Properties();
//		props.load(is);
		
		String url = p.getProperty("junit.demoboard.url");
		String user = p.getProperty("junit.demoboard.user");
		String password = p.getProperty("junit.demoboard.password");
		
		config = new DbConfig(url, user, password);
		
	}

	@Before
	public void setUp() throws Exception {
		conn = config.getConnection(false);
		helper.resetDB(config.getConnection(false), "junit-schema-simpleboard.sql", new MySqlQueryParser());
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
		UserVO user = null;
		assertNull( userDao.findById("no_such_userid") );
	}
	
	@Test
	public void test_insert_new_user() {
		//'moms', '맘이', 'mam@gmail.com', 'mmmm'
		IUserDao userDao = new UserDao(config);
		UserVO user = new UserVO("moms", "맘이", "mom@naver.com", "mmmm");
		user = userDao.insert(user);
		assertEquals ( 3, userDao.findAll().size());
		assertEquals ( 5002, user.getSeq().intValue());
	}

	public static void assertUserFieldNotNull(UserVO user) {
		assertNotNull ( user.getSeq());
		assertNotNull ( user.getNickName());
		assertNotNull ( user.getPassword());
		assertNotNull ( user.getUserId());
		assertNotNull ( user.getWhenJoined());		
	}
	
	
}
