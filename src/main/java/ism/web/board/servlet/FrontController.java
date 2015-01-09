package ism.web.board.servlet;

import ism.web.board.BoardContext;
import ism.web.board.action.View;
import ism.web.board.action.IAction;
import ism.web.board.action.ListPostingAction;
import ism.web.board.action.ListUserAction;
import ism.web.board.action.UsderDeleteAction;
import ism.web.board.action.UserLogin;
import ism.web.board.action.Views;
import ism.web.board.action.json.JoinAction;

import java.io.IOException;

import javax.servlet.ServletException;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		process(request, response);
	}
	
	private String parsePath ( HttpServletRequest request )  {
		String ctrl = "/ctrl";
		String ctxpath = this.getServletContext().getContextPath();
		String uri = request.getRequestURI();
		String path = uri.substring(ctxpath.length() + ctrl.length());
		
		return path ;
	}
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BoardContext bctx = (BoardContext) this.getServletContext().getAttribute("board.context");
		System.out.println(request.getRequestURI() + " : psize " + bctx.getPageSize());		
		String path = parsePath(request);
		System.out.println("Path : " + path);
		
		BoardContext boardCtx  = (BoardContext) request.getServletContext().getAttribute("board.context");
		
		View view = null;
		if ( path.endsWith("/")) {
			view = Views.FORWARD("index.jsp");
		} else if ( path.equals("/users") ) {
			System.out.println("사용자 목록 출력하는 요청");
			IAction action = new ListUserAction();
			view = action.process(boardCtx, request, response);
		} else if ( path.equals("/users/delete")) {
			view = new UsderDeleteAction().process(boardCtx, request, response); 
		} else if ( path.equals("/login")) {
			view = new UserLogin().process(boardCtx, request, response); 
		} else if (path.equals("/postings")) {
			view = new ListPostingAction().process(boardCtx, request, response);
		} else if ( path.equals("/join")) {
			// FIXME 이미 로그인한 사용자라면 아래 페이지를 보여주면 안됨.
			view = Views.FORWARD("/WEB-INF/jsp/new-member.jsp");
		} else if ( path.equals ( "/join.json")) {
			view = new JoinAction().process(boardCtx, request, response);
		}
		
		if ( view != null) {
			System.out.println("next page : " + view.getPath() + " AS " + 
					( view.isForward() ? "forwarding" : "redirection") );
			if ( view.isForward()) {
				request.getRequestDispatcher(view.getPath()).forward(request, response);
			} else {
				response.sendRedirect(view.getPath());
			}
		} else {
			System.out.println("Unknown path : " + path);
		}
		
//		request.getRequestDispatcher(path).forward(request, response);
		
	}

}
