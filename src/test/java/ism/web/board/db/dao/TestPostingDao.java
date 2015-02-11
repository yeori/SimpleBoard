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

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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
	public void test_board_findAll() {
		//IPostingDao dao = new PostingDao();
		PostingDao dao = new PostingDao(config);
		assertEquals(3, dao.findAll().size());
		
	}
	
	@Test
	public void test_find_by_seq() {
		PostingDao dao = new PostingDao(config);
		
		PostingVO posting = dao.findBySeq(2, false);
		assertPostingFieldNotNull(posting);

		UserVO writer = posting.getWriter();
		TestUserDao.assertUserFieldNotNull(writer);
		assertEquals("james", writer.getNickName()); 
	}
	
	@Test
	public void test_insert_new_posting() {
		PostingDao dao = new PostingDao (config) ;
		PostingVO newPosting = new PostingVO("demo", "good", james()); 
		newPosting = dao.insert(newPosting);
		assertPostingFieldNotNull(newPosting);
		assertEquals ( 4, newPosting.getSeq().intValue());
	}
	
	@Test
	public void test_update_viewcount() {
		int postingId = 2;
		PostingDao dao = new PostingDao (config) ;
		PostingVO posting = dao.findBySeq(postingId, true);
		assertEquals (33, posting.getViewCount().intValue());
		
		posting = dao.findBySeq(postingId, false);
		assertEquals ( 33, posting.getViewCount().intValue());
		
	}

	private UserVO james() {
		UserVO user = new UserVO();
		user.setSeq(5000);
		return user ;
	}
	
	public static void assertPostingFieldNotNull(PostingVO posting) {
		assertNotNull ("seq is null", posting.getSeq());
		assertNotNull ("title is null", posting.getTitle());
		assertNotNull ("content is null", posting.getContent());
		assertNotNull ("whenCreated is null", posting.getWhenCreated());
		assertNotNull ("view count is null", posting.getViewCount());
		assertNotNull ("writer is null", posting.getWriter());		
	}

}
