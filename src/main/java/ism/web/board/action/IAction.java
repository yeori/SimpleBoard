package ism.web.board.action;

import ism.web.board.BoardContext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAction {

	public abstract void process(BoardContext ctx, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

}