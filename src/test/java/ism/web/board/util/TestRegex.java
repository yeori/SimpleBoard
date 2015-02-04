package ism.web.board.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestRegex {

	@Test
	public void test_starting_of_str() {
		String s0 = "this is banana";
		String s1 = "that is apple";
		// this 로 시작하는 문자열인가?
		Pattern starting_with_this = Pattern.compile("^this");
		Matcher  m = starting_with_this.matcher(s0);
		assertTrue ( m.find());
		
		// that으로 시작하는 문자열인가?
		Pattern starting_with_that = Pattern.compile("^that");
		assertTrue ( starting_with_that.matcher(s1).find());
		
		// banana로 끝나는 문자열인가?
		Pattern ending_with_banana = Pattern.compile("banana$");
		assertTrue ( ending_with_banana.matcher(s0).find());
	}
	
	/**
	 * 
	 */
	@Test
	public void test_cardinality () {
		
		String [] dates = {
				"2012년 11월 24일 11시 34분 21초",
				"2011년 12월 05일 09시 23분 10초",
				"2011년 08월 15일 06시 04분 23초",
				"2011년 06월 10일 14시 32분 57초",
				"2011년 06월 03일 19시 06분 03초",
				"2010년 12월 23일 14시 21분 34초",
		};
		
		// 2011년도만 뽑아내기
		String regex = "^2011" ; // or "^2011년"
		List<String> byYear2011 = filter ( dates, regex);
		assertEquals ( 4, byYear2011.size());
		
		// 12월만 뽑아내기
		regex = "^[\\d]{4}.\\s12.*";
		List<String> byMonth12 = filter ( dates, regex);
		assertEquals ( 2, byMonth12.size());
		assertEquals ( dates[1], byMonth12.get(0));
		assertEquals ( dates[5], byMonth12.get(1));
		
		// 14시에 발생한 로그
		List<String> byHour14 = filter ( dates, "14시");
		assertEquals ( 2, byHour14.size());
		
		// 
		
	}

	private List<String> filter(String[] dates, String regex) {
		Pattern p = Pattern.compile(regex);
		ArrayList<String> retList = new ArrayList<String>();
		for ( String s : dates ) {
			if ( p.matcher(s).find() ) {
				retList.add(s);
			}
		}
		return retList;
	}
}
