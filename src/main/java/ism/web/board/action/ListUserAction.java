package ism.web.board.action;

import ism.web.board.BoardContext;
import ism.web.board.db.dao.IUserDao;
import ism.web.board.model.UserVO;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserAction implements IAction {
	
	@Override
	public void process(BoardContext ctx, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		IUserDao userDao = ctx.getDaoRepository().getUserDao();
		List<UserVO> users = userDao.findAll();
		request.setAttribute("users", users);
		request.getRequestDispatcher("/WEB-INF/jsp/list-user.jsp").forward(request, response);
	}
}
