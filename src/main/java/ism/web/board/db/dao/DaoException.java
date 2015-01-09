package ism.web.board.db.dao;

import java.sql.SQLException;

import ism.web.board.BoardException;

public class DaoException extends BoardException {

	public DaoException ( String message) {
		super(message);
	}

	public DaoException(String message, SQLException cause) {
		// TODO Auto-generated constructor stub
		super(message, cause);
	}
}
