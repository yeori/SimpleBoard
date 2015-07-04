package ism.web.board.servlet.listener;

import ism.web.board.BoardContext;
import ism.web.board.BoardException;
import ism.web.board.db.DbConfig;
import ism.web.board.db.dao.DaoRepository;
import ism.web.board.db.dao.PostingDao;
import ism.web.board.db.dao.UserDao;
import ism.web.board.util.HibernateUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application Lifecycle Listener implementation class BoardInitiater
 *
 */
@WebListener
public class BoardInitiater implements ServletContextListener {
	private Logger logger = LoggerFactory.getLogger(BoardInitiater.class);
	
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
    	logger.info("게시판 웹 애플리케이션 준비");
    	try {
//    		SqlSessionFactory factory = initMybatisSessionFactory(ctx);
    		SessionFactory hbmSessionFactory = HibernateUtil.getSessionFactory();
    		DaoRepository repository = initDaoRepository(ctx);
    		DbConfig dbConfig = initDBConfig(repository, hbmSessionFactory, ctx);
    		registerDaos(dbConfig, repository);
    		
			BoardContext boardConfig = new BoardContext(dbConfig, ctx);
			ctx.setAttribute("board.context", boardConfig);
			
		} catch (BoardException e) {
			throw e; // throw new RuntimeException("fail to init db configration.", e);
		}
    	
    	logger.info("게시판 웹 에플리케이션 준비 완료");
    }
    
    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
    
//    private SqlSessionFactory initMybatisSessionFactory(ServletContext ctx) {
//    	ClassLoader cl = this.getClass().getClassLoader();
//    	InputStream in = cl.getResourceAsStream("mybatis-config.xml");
//    	SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
//    	return factory;
//    }
    
    private DaoRepository initDaoRepository(ServletContext ctx) {
    	DaoRepository repo = new DaoRepository();
    	
    	ctx.setAttribute(BoardContext.ATT_DAO_REPOSITORY, repo);
    	
    	return repo;
    }

    private DbConfig initDBConfig(
    		DaoRepository repo, 
    		SessionFactory factory, ServletContext ctx) throws BoardException {
    	String dbconn_filepath = ctx.getInitParameter("file.dbconfig");
    	InputStream in = this.getClass().getClassLoader().getResourceAsStream(dbconn_filepath);
    	
    	Properties props = new Properties();
		try {
			props.load(in);
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
