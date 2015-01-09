package ism.web.board.action;

import ism.web.board.BoardContext;
import ism.web.board.db.dao.IUserDao;
import ism.web.board.model.UserVO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** 로그인 처리
 * 
 * @author sangmin
 *
 */
public class UserLogin implements IAction {

	private String getContextPath(BoardContext ctx) {
		return ctx.getServletContextPath();
	}
	@Override
	public View process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		IUserDao dao = ctx.getDaoRepository().getUserDao();
		String usernm = request.getParameter("user");
		String pw = request.getParameter("password");
		System.out.println("user="+usernm+"password="+pw);
		
		UserVO user = dao.findUser( usernm, pw);
		
		
		String ctxPath = getContextPath(ctx);
		if ( user != null ) {
			String url = ctxPath + "/login_ok.jsp";
			System.out.println(" 로그인 성공 : " + url);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect( url);
		}
		
		return null; // FIXME action forward 구현중
		
	}

}
