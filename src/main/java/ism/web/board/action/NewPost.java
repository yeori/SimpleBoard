package ism.web.board.action;

import ism.web.board.BoardContext;
import ism.web.board.db.dao.IPostingDao;
import ism.web.board.model.PostingVO;
import ism.web.board.model.UserVO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
/**
 * 새글을 db에 입력함.
 * 
 * @author chminseo
 *
 */
public class NewPost implements IAction {
	@SuppressWarnings("unchecked")
	@Override
	public View process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PostingVO newPosting = writeToDB(ctx, request, response);
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("nextUrl", "/postings/" + newPosting.getSeq());
		request.setAttribute("json", json.toJSONString());
		return Views.FORWARD("/WEB-INF/jsp/json/json-response.jsp");
	}

	private PostingVO writeToDB(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) {
		UserVO writer = (UserVO) request.getSession(false).getAttribute("user");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		System.out.println("title : " + title);
		System.out.println("content : " + content);
		PostingVO newPosting = new PostingVO(title, content, writer);
		IPostingDao dao = ctx.getDaoRepository().getPostingDao();
		newPosting = dao.insert(newPosting);
		return newPosting;
	}
}
