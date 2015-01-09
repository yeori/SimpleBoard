package ism.web.board.action.json;

import ism.web.board.BoardContext;
import ism.web.board.action.IAction;
import ism.web.board.action.View;
import ism.web.board.action.Views;
import ism.web.board.db.dao.DaoException;
import ism.web.board.db.dao.IUserDao;
import ism.web.board.model.UserVO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
/**
 * 새로운 회원 가입 요청을 처리하는 액션
 * 
 * 여기서는 요청에 대한 응답을 json으로 하도록 합니다.
 * 
 * 
 *
 */
public class JoinAction implements IAction {
	private final String jsonPage = "/WEB-INF/jsp/json/json-response.jsp";
	
	@SuppressWarnings("unchecked")
	@Override
	public View process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("userid");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		JSONObject json = new JSONObject();
		// COMMENT parameter 들을 검증하는
		// 지루한 작업들을 다른데로 몰아넣는게 좋음.
		if ( id == null || id.trim().length() == 0 ) {
			return writeError(request, json, "부적합한 id [" + id + "]");
		}
		
		if ( password == null || password.trim().length() == 0 ) {
			return writeError ( request, json, 
					"부적합한 password [" + password + "]");
		}
		
		IUserDao dao = ctx.getDaoRepository().getUserDao();
		
		if ( dao.existsUserId(id) ) {
			return writeError(request, json, "중복 id [" + id + "]");
		}
		
		UserVO newUser = new UserVO(id, id, email, 
				password);
		try {
			json.put("success", Boolean.TRUE);
			json.put("href", "/");
			newUser = dao.insert(newUser);
			JSONObject jsUser = new JSONObject();
			jsUser.put("seq", newUser.getSeq().intValue());
			jsUser.put("userid", newUser.getUserId());
			jsUser.put("nickname", newUser.getNickName());
			jsUser.put("password", newUser.getPassword());
			jsUser.put("when-joined", newUser.getWhenJoined());
			json.put("user", jsUser);
			System.out.println(newUser);
			
			request.setAttribute("json", json.toJSONString());
			return Views.FORWARD(jsonPage);
			
		} catch (DaoException e) {
			e.printStackTrace();
			return writeError ( request, json, e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private View writeError (HttpServletRequest request, 
			JSONObject json, 
			String error) {
		json.put("success", false);
		json.put("cause", error);
		
		request.setAttribute("json", json.toJSONString());
		return Views.FORWARD("/WEB-INF/jsp/json/json-response.jsp");
	}

}
