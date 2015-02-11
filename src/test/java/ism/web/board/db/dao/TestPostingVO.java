package ism.web.board.db.dao;

import static org.junit.Assert.*;
import ism.web.board.db.DbConfig;
import ism.web.board.model.PostingVO;
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
import org.junit.Test;

import testutils.db.DbTestHelper;
import testutils.db.MySqlQueryParser;

public class TestPostingVO {
	static DbConfig config ;
	static DbTestHelper helper = new DbTestHelper();
	static PostingDao dao;
	static String NOT_USED = null;
	Connection conn = null;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		InputStream in = Resources.getResourceAsStream("mybatis-junit-config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
		config = new DbConfig(null, factory);
		dao = new PostingDao(config);
	}

	@Before
	public void setUp() throws Exception {
		conn = config.getSqlSessionFactory().openSession().getConnection();
		helper.resetDB(conn, "junit-schema-simpleboard.sql", new MySqlQueryParser());
	}
	
	@After
	public void tearDown() throws Exception {
		config.release(conn, null, null);
	}
	
	@Test
	public void test_findAll() throws Exception {
		assertEquals ( 3, dao.findAll().size() );
	}
	
	@Test
	public void test_insert_new_posting() throws Exception {
		String title = "insert new posting";
		String content = "new posting with single quot(')";
		UserVO writer = new UserVO(5000, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED);
		PostingVO posting = new PostingVO(title, content, writer);
		
		posting = dao.insert(posting);
		
		assertPostingFieldNotNull(posting);
	}
	
	public static void assertPostingFieldNotNull(PostingVO posting) {
		assertNotNull ( posting.getSeq());
		assertNotNull ( posting.getTitle());
		assertNotNull ( posting.getContent());
		assertNotNull ( posting.getViewCount());
		assertNotNull ( posting.getWhenCreated());
		assertNotNull ( posting.getWriter());
		TestUserDao.assertUserFieldNotNull(posting.getWriter());
	}
	
}
