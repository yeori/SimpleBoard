package ism.web.board.action;

import ism.web.board.BoardContext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsderDeleteAction implements IAction {

	@Override
	public View process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ctx.getDaoRepository().getUserDao().delete(null);
		response.sendRedirect("....");
		
		return null ; // FIXME action forward 도입 중
		
	}

}
