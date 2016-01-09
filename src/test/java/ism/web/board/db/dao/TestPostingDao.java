package ism.web.board.db.dao;

import static org.junit.Assert.*;
import ism.web.board.db.DbConfig;
import ism.web.board.model.PostingVO;
import ism.web.board.model.UserVO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import testutils.db.DbTestHelper;
import testutils.db.MySqlQueryParser;

public class TestPostingDao {
	static DbConfig config ;
	static DbTestHelper helper = new DbTestHelper();
	
	Connection conn = null;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		InputStream is = TestUserDao.class.getClassLoader().getResourceAsStream("dbconn.properties");
		assertNotNull ("fail to find dbconn.properties", is) ;
		
		Properties props = new Properties();
		props.load(is);
		
		String url = props.getProperty("junit.demoboard.url");
		String user = props.getProperty("junit.demoboard.user");
		String password = props.getProperty("junit.demoboard.password");
		
		config = new DbConfig(url, user, password);
		
		SessionFactory hbmFactory = new Configuration().configure("junit-hbm.cfg.xml").buildSessionFactory();
		config.setHbmFactory(hbmFactory);
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
	public void test_findBySeq() {
		PostingDao dao = new PostingDao(config);
		PostingVO second = dao.findBySeq(2);
		assertEquals ( 32, second.getViewCount());
	}
	
	@Test
	public void test_getwriter() {
		PostingDao dao = new PostingDao(config);
		PostingVO second = dao.findBySeq(2);
		assertNotNull ( second.getWriter());
		
		UserVO proxyUser = second.getWriter();
		Session session = config.getHbmFactory().openSession();
		session.update(proxyUser);
			session.persist(proxyUser); // 
		assertEquals ("james@naver.com", proxyUser.getEmail());
		
		session.close();
	}
	@Test
	public void test_board_findAll() {
		//IPostingDao dao = new PostingDao();
		
	}

}
