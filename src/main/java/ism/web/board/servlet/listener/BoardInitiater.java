package ism.web.board.servlet.listener;

import ism.web.board.BoardContext;
import ism.web.board.BoardException;
import ism.web.board.db.DbConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class BoardInitiater
 *
 */
@WebListener
public class BoardInitiater implements ServletContextListener {
	
	
    /**
     * Default constructor. 
     */
    public BoardInitiater() {
    }

    
    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
    	ServletContext ctx = sce.getServletContext();
    	try {
			DbConfig dbConfig = initDBConfig(ctx);
			BoardContext boardConfig = new BoardContext(dbConfig, ctx);
			ctx.setAttribute("board.context", boardConfig);
			
		} catch (BoardException e) {
			throw e; // throw new RuntimeException("fail to init db configration.", e);
		}
    }
    
    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }

    private DbConfig initDBConfig(ServletContext ctx) throws BoardException {
    	String dbconn_filepath = ctx.getInitParameter("file.dbconfig");
    	InputStream in = this.getClass().getClassLoader().getResourceAsStream(dbconn_filepath);
    	
    	Properties props = new Properties();
		try {
			props.load(in);
			String url = props.getProperty("deploy.demoboard.url");
			String user = props.getProperty("deploy.demoboard.user");
			String password = props.getProperty("deploy.demoboard.password");
			
			DbConfig config = new DbConfig(url, user, password);
			return config;
		} catch (Exception e) {
			throw new BoardException("fail to init db configrations. check the file " + dbconn_filepath, e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
}
