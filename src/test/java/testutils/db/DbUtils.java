package testutils.db;

public class DbUtils {
	public static String rtrim(String s) {
		checkValidString(s);
		
		int p = s.length();
		
		while ( Character.isWhitespace(s.charAt(p-1))){
			p -= 1;
		}
		return s.substring(0, p);
	}
	
	static void checkValidString(String s) {
		if ( s == null || s.trim().length() == 0 ){
			throw new IllegalArgumentException("invalid string : [" + s + "]");
		}
	}
	
}
