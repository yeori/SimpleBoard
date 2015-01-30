package ism.web.board.action;

import ism.web.board.BoardContext;
import ism.web.board.db.dao.IPostingDao;
import ism.web.board.model.PostingVO;
import ism.web.board.util.BoardUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 특정 글 조회 요청을 처리함.
 * 글 조회요청 uri는 다음과 같은 형식이어야함.
 * 
 * '/postings/39993'
 * '/postings/12'
 * 
 * @see BoardUtil#isViewPostingPath(String)
 */
public class ViewPosting implements IAction {
	@Override
	public View process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pid = parsePostingId ( request) ;
		IPostingDao postingDao = ctx.getDaoRepository().getPostingDao();
		
		PostingVO posting = postingDao.findBySeq(pid);
		
		View next = null;
		if ( posting == null ) {
			next = Views.FORWARD("/WEB-INF/jsp/404.jsp");
		} else {
			request.setAttribute("posting", posting);
			posting.setViewCount(posting.getViewCount()+1);
			postingDao.updateViewCount ( posting.getSeq(), posting.getViewCount() );
			next = Views.FORWARD("/WEB-INF/jsp/view-posting.jsp");
		}
		return next;
	}

	private int parsePostingId(HttpServletRequest request) {
		String path = request.getRequestURI();
		String [] parts = path.split("/");
		return Integer.parseInt(parts[parts.length-1]);
	}

}
