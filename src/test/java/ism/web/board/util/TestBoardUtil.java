package ism.web.board.util;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBoardUtil {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String path = "/postings/recent/188";
		Pattern p = Pattern.compile("^/postings/\\w+/\\d+");
		Matcher m = p.matcher(path);
		assertTrue ( m.find() );
	}
	
	@Test
	public void check_posting_view_path() {
		assertFalse (BoardUtil.isViewPostingPath("/postings"));
		assertFalse (BoardUtil.isViewPostingPath("/postings/not-a-number"));
		assertTrue  (BoardUtil.isViewPostingPath("/postings/3993"));
	}

}
