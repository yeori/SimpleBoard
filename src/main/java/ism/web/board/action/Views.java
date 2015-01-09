package ism.web.board.action;

public class Views {
	final public static View FORWARD ( String path) {
		return new View(path, false);
	}
	
	final public static View REDIRECT ( String path) {
		return new View(path, true);
	}
}
