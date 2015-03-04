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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 새글을 db에 입력함.
 * 
 * @author chminseo
 *
 */
public class NewPost implements IAction {
	private Logger logger = LoggerFactory.getLogger(NewPost.class);
	
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
		
		logger.debug(String.format("title : %s, content : [%s](size:%d)",
				title, content.substring(0, 40)+"...", content.length()));

		PostingVO newPosting = new PostingVO(title, content, writer);
		IPostingDao dao = ctx.getDaoRepository().getPostingDao();
		newPosting = dao.insert(newPosting);
		return newPosting;
	}
}
