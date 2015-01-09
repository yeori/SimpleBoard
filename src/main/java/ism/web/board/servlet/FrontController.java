package ism.web.board.servlet;

import ism.web.board.BoardContext;
import ism.web.board.action.IAction;
import ism.web.board.action.ListPostingAction;
import ism.web.board.action.ListUserAction;
import ism.web.board.action.UsderDeleteAction;
import ism.web.board.action.UserLogin;
import ism.web.board.db.dao.IUserDao;
import ism.web.board.model.UserVO;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
/*
@WebServlet(
		urlPatterns = { "*.board" }, 
		initParams = { 
				@WebInitParam(name = "key", value = "value", description = "test params")
		})
*/
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardContext bctx = (BoardContext) this.getServletContext().getAttribute("board.context");
		System.out.println(request.getRequestURI() + " : psize " + bctx.getPageSize());
		
		String ctxpath = this.getServletContext().getContextPath();
		String uri = request.getRequestURI();
		String path = uri.substring(ctxpath.length());
		System.out.println(path);
		
		BoardContext boardCtx  = (BoardContext) request.getServletContext().getAttribute("board.context");
		
		if ( path.endsWith("/users") ) {
			System.out.println("사용자 목록 출력하는 요청");
			IAction action = new ListUserAction();
			action.process(boardCtx, request, response);
		} else if ( path.endsWith("/users/delete")) {
			new UsderDeleteAction().process(boardCtx, request, response); 
		} else if ( path.endsWith("/login")) {
			new UserLogin().process(boardCtx, request, response); 
		} else if (path.endsWith("/postings")) {
			new ListPostingAction().process(boardCtx, request, response);
		}
		
//		request.getRequestDispatcher(path).forward(request, response);
		
	}

}
