package ism.web.board.servlet;

import ism.web.board.db.DbConfig;
import ism.web.board.db.dao.UserDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class BoardInit
 * 
 * 서블릿이 시작할때 필요한 모듈들을 생성하고 초기화하는 클래스입니다.
 *
 */
@WebListener
public class BoardInit implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public BoardInit() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("애플리케이션을종료합니다.");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
         // TODO Auto-generated method stub
    	System.out.println("서블릿이 시작되었습니다");
    	
    	ServletContext ctx = event.getServletContext();
    	try {
			initDbConfig( ctx);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    /**
     * 데이터베이스 연결 모듈을 초기화합니다.
     * @param ctx
     * @throws IOException
     */
	private void initDbConfig(ServletContext ctx) throws IOException {
		// TODO Auto-generated method stub
		InputStream is = BoardInit.class.getClassLoader().getResourceAsStream("dbconn.properties");
		
		Properties props = new Properties();
		props.load(is);
		
		String url = props.getProperty("junit.demoboard.url");
		String user = props.getProperty("junit.demoboard.user");
		String password = props.getProperty("junit.demoboard.password");
		
		DbConfig config = new DbConfig(url, user, password);
		UserDao userDao = new UserDao(config);
		
		ctx.setAttribute("user-dao", userDao);
		
		
	}
	
}
