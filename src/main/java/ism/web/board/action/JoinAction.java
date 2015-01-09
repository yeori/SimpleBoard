package ism.web.board.action;

import ism.web.board.BoardContext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 새로운 회원 가입 요청을 처리하는 액션
 * 
 * 여기서는 요청에 대한 응답을 json으로 하도록 합니다.
 * 
 * 
 *
 */
public class JoinAction implements IAction {

	@Override
	public View process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
