package ism.web.board.db.dao;

import static org.junit.Assert.*;
import ism.web.board.db.DbConfig;
import ism.web.board.model.PostingVO;
import ism.web.board.model.UserVO;
import ism.web.board.util.HibernateUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
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
	private static SessionFactory factory;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		String path = "junit-hibernate.cfg.xml";
		factory = HibernateUtil.getSessionFactory(path);
		
		config = new DbConfig(null, factory);
		
	}
	
	@Before
	public void setUp() throws Exception {
		Session session = config.getSqlSessionFactory().openSession();
		
		conn = null;
		session.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				try {
					helper.resetDB(connection, 
							"junit-schema-simpleboard.sql", 
							new MySqlQueryParser());
					connection.commit();
				} catch (IOException e) {
					throw new SQLException("fail to test case for junit", e);
				}
			}
		});
	}
	
	@After
	public void tearDown() throws Exception {
		config.release(conn, null, null);
	}

	@Test
	public void test_board_findAll() {
		//IPostingDao dao = new PostingDao();
		PostingDao dao = new PostingDao(config);
		
		List<PostingVO> postings = dao.findAll();
		assertEquals(3, postings.size());
		assertEquals(5000, postings.get(0).getWriter().getSeq().intValue());
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
		PostingVO p = dao.findBySeq(newPosting.getSeq(), false);
		assertPostingFieldNotNull(p);
		assertEquals ( 4, p.getSeq().intValue());
	}
	
	@Test
	public void test_update_viewcount() {
		int postingId = 2;
		PostingDao dao = new PostingDao (config) ;
		PostingVO posting = dao.findBySeq(postingId, true);
		assertEquals (33, posting.getViewCount().intValue());
		
		PostingVO posting2 = dao.findBySeq(postingId, false);
		assertEquals ( 33, posting2.getViewCount().intValue());
		
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
