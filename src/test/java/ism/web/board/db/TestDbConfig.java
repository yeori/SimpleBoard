package ism.web.board.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.junit.Test;

import testutils.db.DbTestHelper;
import testutils.db.MySqlQueryParser;
import junit.framework.TestCase;

public class TestDbConfig extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void test_getconnection() throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("dbconn.properties");
		Properties props = new Properties();
		props.load(is);
		
		String url = props.getProperty("junit.demoboard.url");
		String user = props.getProperty("junit.demoboard.user");
		String password = props.getProperty("junit.demoboard.password");
		DbConfig config = new DbConfig(url, user, password);
		
		Connection conn = config.getConnection(false);
		config.release(conn, null, null);
		
	}

}
