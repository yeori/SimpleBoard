package ism.web.board.action.json;

import ism.web.board.BoardContext;
import ism.web.board.BoardException;
import ism.web.board.ParamException;
import ism.web.board.action.IAction;
import ism.web.board.action.View;
import ism.web.board.db.dao.DaoException;
import ism.web.board.db.dao.IUserDao;
import ism.web.board.db.dao.UserDao;
import ism.web.board.model.UserVO;

import java.io.IOException;
import java.util.HashMap;

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

	@SuppressWarnings("unchecked")
	@Override
	public View process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("userid");
		String password = request.getParameter("password");
		
		// COMMENT parameter 들을 검증하는
		// 지루한 작업들을 다른데로 몰아넣는게 좋음.
		if ( id == null || id.trim().length() == 0 ) {
			throw new ParamException ( "부적합한 id [" + id + "]");
		}
		if ( password == null || password.trim().length() == 0 ) {
			throw new ParamException ( "부적합한 password [" + password + "]");			
		}
		IUserDao dao = ctx.getDaoRepository().getUserDao();
		
		if ( dao.existsUserId(id) ) {
			throw new BoardException("[가입] 중복 id [" + id + "]");
		}
		
		UserVO newUser = new UserVO(id, "Test Nick", "TestMail@mail.com", 
				password);
		JSONObject json = new JSONObject();
		try {
			json.put("success", Boolean.TRUE);
			newUser = dao.insert(newUser);
			JSONObject jsUser = new JSONObject();
			jsUser.put("seq", newUser.getSeq().intValue());
			jsUser.put("userid", newUser.getUserId());
			jsUser.put("nickname", newUser.getNickName());
			jsUser.put("password", newUser.getPassword());
			jsUser.put("when-joined", newUser.getWhenJoined());
			json.put("user", jsUser);
			
		} catch (DaoException e) {
			json.put("success", Boolean.FALSE);
			json.put("cause", e.getMessage());
		}
		
		
		return null;
	}

}
