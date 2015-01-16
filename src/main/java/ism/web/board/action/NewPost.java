package ism.web.board.action;

import ism.web.board.BoardContext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class NewPost implements IAction {
	@SuppressWarnings("unchecked")
	@Override
	public View process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		System.out.println("title : " + title);
		System.out.println("content : " + content);
		// 아직 글쓰기 로직은 포함안된 상태
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("cause", "e4002");
		request.setAttribute("json", json.toJSONString());
		return Views.FORWARD("/WEB-INF/jsp/json/json-response.jsp");
	}



}
