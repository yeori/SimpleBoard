package ism.web.board.db.mybatis;

import static org.junit.Assert.*;

import ism.web.board.model.UserVO;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMybatisInit {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_mybatis_init() throws IOException {
		// 1. load and create session
		Class<?> cls = this.getClass() ;
		InputStream in = cls.getResourceAsStream("/mybatis-config.xml");
		SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(in);
		SqlSession session = sf.openSession();
		
		// 2. querying
		UserVO user = session.selectOne("User.userBySeq", 5001);
		assertEquals ( "kongkong@gmail.com", user.getEmail());
		
		user = session.selectOne("User.userById", "happy");
		assertEquals ( "happy", user.getNickName());
		
		// 3. closing session
		session.close();
		in.close();
	}
}
