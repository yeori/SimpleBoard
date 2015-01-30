package ism.web.board.util;

import java.util.regex.Pattern;

public class BoardUtil {

//	private static Pattern PATTERN_VIEW_POSTING = Pattern.compile("^/postings/\\w+/\\d+");
	private static Pattern PATTERN_VIEW_POSTING = Pattern.compile("^/postings/\\d+");
	/**
	 * path가 '/posting/:number' 형식인지 판단함.
	 * @param path
	 * @return
	 */
	public static boolean isViewPostingPath ( String path) {
		return PATTERN_VIEW_POSTING.matcher(path).find();
	}
	
	/**
	 * '/' 로 path를 잘라서 배열 형태로 반환함.
	 * ex) "/postings/java/39993" : {"postings", "java", "39993" }
	 * @param path
	 * @return
	 */
	public static String[] splitPath(String path) {
		if ( path.startsWith("/")) {
			path = path.substring(1);
		}
		return path.split("/");
	}
}
