package ism.web.board.action;

import ism.web.board.BoardContext;
import ism.web.board.db.dao.IPostingDao;
import ism.web.board.model.PostingVO;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListPostingAction implements IAction {

	@Override
	public void process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		IPostingDao posDao = ctx.getDaoRepository().getPostingDao();
		List<PostingVO> list = posDao.findAll();
		
		request.setAttribute("postings", list);
		request.getRequestDispatcher("/WEB-INF/jsp/list-postings.jsp").forward(request, response);
	}

}
