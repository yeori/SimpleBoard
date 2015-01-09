package ism.web.board;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ism.web.board.action.View;
import ism.web.board.action.json.JoinAction;
import ism.web.board.db.DbConfig;
import ism.web.board.db.dao.DaoRepository;
import ism.web.board.db.dao.IPostingDao;
import ism.web.board.db.dao.IUserDao;
import ism.web.board.db.dao.UserDao;
import ism.web.board.model.UserVO;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestJoinAction {
	MockBoardContext ctx;
	MockRequest request;
	HttpServletResponse response;
	
	UserVO user = new UserVO(1000, 
			"testid", "testid", "test@e.com", "1234", "2000");
	
	@Before
	public void setUp() throws Exception {
		DbConfig mockDbconfig = Mockito.mock(DbConfig.class);
		ServletContext sctx = Mockito.mock(ServletContext.class);
		ctx = new MockBoardContext(mockDbconfig, sctx);
		
		request = Mockito.mock(MockRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		
		param(request, "userid", "testid");
		param(request, "password", "3333");
		
		// http://stackoverflow.com/questions/2276271/how-to-make-mock-to-void-methods-with-mockito
		Mockito.doCallRealMethod()
			.when( request)
			.setAttribute(anyString(), anyObject());
		
		Mockito.doCallRealMethod()
			.when( request)
			.getAttribute(anyString());
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test_join() throws ServletException, IOException, ParseException {
		UserVO user = new UserVO(1000, 
				"testid", "testid", "test@e.com", "1234", "2000");
		
		JoinAction action = new JoinAction();
		
		IUserDao dao = ctx.userDao();
		when(dao.existsUserId(anyString())).thenReturn(false);
		when(dao.insert(any( UserVO.class))).thenReturn(user);
		
		action.process(ctx, request, response);
		JSONObject response = (JSONObject) new JSONParser()
				.parse((String)request.getAttribute("json"));
		
		assertTrue ( (Boolean)response.get("success"));
		// "user" 검증은 생략함.
	}
	
	/**
	 * userid가 이미 존재하는 경우 - json 응답의 success 값이 false
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Test
	public void existing_userid() throws ServletException, IOException, ParseException {

		JoinAction action = new JoinAction();
		
		IUserDao dao = ctx.userDao();
		when(dao.existsUserId(anyString())).thenReturn(true);
		
		action.process(ctx, request, response);
		
		JSONObject response = (JSONObject) new JSONParser()
		.parse((String)request.getAttribute("json"));
		

		assertFalse ( (Boolean)response.get("success"));
	}
	
	public static void param ( HttpServletRequest req, String key, String val) {		
		Mockito.when(req.getParameter(key)).thenReturn(val);
	}
	
	static class MockBoardContext extends BoardContext {

		private DaoRepository daoRepo = Mockito.mock(DaoRepository.class);
		private IUserDao userDao = Mockito.mock ( IUserDao.class);
		private IPostingDao postingDao = Mockito.mock(IPostingDao.class);
		
		public MockBoardContext(DbConfig mockDbconfig, ServletContext sctx) {
			super( mockDbconfig, sctx);
			Mockito.when(mockDbconfig.getDaoRepository()).thenReturn(daoRepo);
			
			Mockito.when(daoRepo.getUserDao()).thenReturn ( userDao);
			Mockito.when(daoRepo.getPostingDao()).thenReturn(postingDao);
		}
		
		public IUserDao userDao() {
			return userDao;
		}
		
		public IPostingDao postingDao() {
			return postingDao ;
		}
		
	}
	
	static abstract class MockRequest implements HttpServletRequest {
		
		private Map<String, Object> attrs ;
		
		@Override
		public void setAttribute(String name, Object o) {
			if ( attrs == null ) {
				attrs = new HashMap<String, Object>();
			}
			attrs.put(name, o);
		}
		
		@Override
		public Object getAttribute(String name) {
			return attrs.get(name);
		}
	}

}
