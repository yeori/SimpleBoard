package ism.web.board.db.dao;

import ism.web.board.db.DbConfig;
import ism.web.board.model.PostingVO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PostingDao implements IPostingDao {
	private DbConfig config;
	private SessionFactory factory ;
	public PostingDao ( DbConfig config) {
		this.config = config;
		factory = config.getSqlSessionFactory();
	}
	
	/**
	 * 주어진 PK의 글을 읽어들임. 
	 * 
	 * @param seq 읽어들일 글의 PK
	 * @param updateViewCount true 이면 조회수 1 증가시킴.
	 */
	@Override
	public PostingVO findBySeq( int seq, boolean updateViewCount) throws DaoException {
		
		Session session = factory.openSession();
		
		try {
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("from PostingVO where seq = :seq" );
			query.setParameter("seq", seq);
			PostingVO posting = (PostingVO) query.uniqueResult();
			posting.setViewCount(posting.getViewCount()+1);
			session.save(posting);
			
			// to prevent error "could not iniitialize proxy - no Session"
			session.update(posting.getWriter());
			
			tx.commit();
			return posting;
		} finally {
			session.close();
		}
	}
	

//	PostingVO findBySeq (Session session, int seq) {
//		PostingVO posting = null;
//		
//		posting = session.selectOne("Posting.findBySeq", seq);
//		return posting;
//	}
	
	@Override
	public PostingVO insert(PostingVO posting) throws DaoException {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(posting);
			tx.commit();
			return posting;
		} finally {
			session.close();
		}
	}

	@Override
	public List<PostingVO> findAll() throws DaoException {
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			
			Query q = session.createQuery("from PostingVO");
			List<PostingVO> postings = q.list();
			for (int i = 0; i < postings.size(); i++) {
				postings.get(i).getWriter().getEmail();
			}
			tx.commit();
			return postings;
		} finally {
			session.close();
		}
	}
	
//	void updateViewCount(Session session, int seq, int viewCount) {
//		Map<String, Integer> params = new HashMap<String, Integer>();
//		params.put("postingId", seq);
//		params.put("viewCount", viewCount);
//	
////		int updateCount = session.update("Posting.updateViewCount", params);
//		session.createQuery("from PostingVO where seq = ")
//		
//		if ( updateCount != 1) {
//			throw new SQLException("조회수 갱신 실패 : posting[" + seq + "] count[" + viewCount + "]");
//		}
//		
//	}
	@Override
	public boolean updateViewCount(int seq, int viewCount) {
		Session session = factory.openSession();
		
		try {
			Query q = session.createQuery("update PostingVO" +
					" set viewCount = :vc" +
					" where seq = :seq");
			q.setParameter("vc", viewCount);
			q.setParameter("seq", seq);
			
//			updateViewCount(session, seq, viewCount);
			return q.executeUpdate() == 1 ;
		} finally {
			session.close();
		}
	}

	@Override
	public String getName() {
		return "postingDao";
	}

	@Override
	public IPostingDao getDao() {
		return this;
	}
	
}