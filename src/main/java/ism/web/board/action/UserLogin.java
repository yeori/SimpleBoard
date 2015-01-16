package ism.web.board.action;

import ism.web.board.BoardContext;
import ism.web.board.db.dao.IUserDao;
import ism.web.board.model.UserVO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

/** 로그인 처리 
 * 
 * @reponse json
 * @author sangmin
 *
 */
public class UserLogin implements IAction {

	@SuppressWarnings("unchecked")
	@Override
	public View process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		IUserDao dao = ctx.getDaoRepository().getUserDao();
		String usernm = request.getParameter("user");
		String pw = request.getParameter("password");
		System.out.println("user="+usernm+"password="+pw);
		
		UserVO user = dao.findUser( usernm, pw);
		
		View view = null;
		JSONObject json = new JSONObject();
		if ( user != null ) {
			System.out.println(" 로그인 성공 : "); // FIXME 로그 관리 필요.
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			json.put("success", Boolean.TRUE);
			json.put("nextUrl", ctx.getServletContextPath());
			
		} else {
			json.put("success", Boolean.FALSE);
			json.put("ecode", "e4000");
		}
		
		request.setAttribute("json", json.toJSONString());
		view = Views.FORWARD("/WEB-INF/jsp/json/json-response.jsp");
		return view;
		
	}
}
