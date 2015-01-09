package ism.web.board;

import java.sql.SQLException;

public class BoardException extends RuntimeException {
	public BoardException ( String message ) {
		super(message);
	}


	public BoardException (String message, Exception cause) {
		super( message, cause);
	}
}
