package ism.web.board.servlet.listener;

import ism.web.board.BoardContext;
import ism.web.board.BoardException;
import ism.web.board.db.DbConfig;
import ism.web.board.db.dao.DaoRepository;
import ism.web.board.db.dao.PostingDao;
import ism.web.board.db.dao.UserDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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
    		SqlSessionFactory factory = initMybatisSessionFactory(ctx);
    		DaoRepository repository = initDaoRepository(ctx);
    		DbConfig dbConfig = initDBConfig(repository, factory, ctx);
    		registerDaos(dbConfig, repository);
    		
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
    
    private SqlSessionFactory initMybatisSessionFactory(ServletContext ctx) {
    	ClassLoader cl = this.getClass().getClassLoader();
    	InputStream in = cl.getResourceAsStream("mybatis-config.xml");
    	SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
    	return factory;
    }
    
    private DaoRepository initDaoRepository(ServletContext ctx) {
    	DaoRepository repo = new DaoRepository();
    	
    	ctx.setAttribute(BoardContext.ATT_DAO_REPOSITORY, repo);
    	
    	return repo;
    }

    private DbConfig initDBConfig(DaoRepository repo, SqlSessionFactory factory, ServletContext ctx) throws BoardException {
    	String dbconn_filepath = ctx.getInitParameter("file.dbconfig");
    	InputStream in = this.getClass().getClassLoader().getResourceAsStream(dbconn_filepath);
    	
    	Properties props = new Properties();
		try {
			props.load(in);
//			String url = props.getProperty("deploy.demoboard.url");
//			String user = props.getProperty("deploy.demoboard.user");
//			String password = props.getProperty("deploy.demoboard.password");
//			
			DbConfig config = new DbConfig(repo, factory);
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
    
    private void registerDaos(DbConfig config, DaoRepository repository) {
    	repository.registerDao(new UserDao(config));
    	repository.registerDao(new PostingDao(config));
    }
}
