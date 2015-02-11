package ism.web.board.db.dao;

import ism.web.board.db.DbConfig;
import ism.web.board.model.PostingVO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class PostingDao implements IPostingDao {
	private DbConfig config;
	
	public PostingDao ( DbConfig config) {
		this.config = config;
	}
	
	/**
	 * 주어진 PK의 글을 읽어들임. 
	 * 
	 * @param seq 읽어들일 글의 PK
	 * @param updateViewCount true 이면 조회수 1 증가시킴.
	 */
	@Override
	public PostingVO findBySeq( int seq, boolean updateViewCount) throws DaoException {
		
		SqlSession session = config.getSqlSessionFactory().openSession(false);
		PostingVO posting = null;
		
		try {
			posting = findBySeq(session, seq);
			if ( updateViewCount) {
				Integer newViewCnt = posting.getViewCount() + 1;
				updateViewCount(session, seq, newViewCnt);
				posting.setViewCount(newViewCnt);
			}
			session.commit();
			return posting;
		} catch ( SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	

	PostingVO findBySeq (SqlSession session, int seq) {
		PostingVO posting = null;
		
		posting = session.selectOne("Posting.findBySeq", seq);
		return posting;
	}
	
	@Override
	public PostingVO insert(PostingVO posting) throws DaoException {
		SqlSession session = config.getSqlSessionFactory().openSession(false);
		try {
			int insertCount = session.insert("Posting.newPosting", posting);
			if ( insertCount != 1) {
				throw new SQLException("새글 삽입 실패 : " + posting);
			}
			session.commit();
			return findBySeq( session, posting.getSeq());
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

	@Override
	public List<PostingVO> findAll() throws DaoException {
		SqlSession session = config.getSqlSessionFactory().openSession(false);
		try {
			return session.selectList("Posting.findAll");
		} finally {
			session.close();
		}
	}
	
	void updateViewCount(SqlSession session, int seq, int viewCount) throws SQLException {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("postingId", seq);
		params.put("viewCount", viewCount);
		int updateCount = session.update("Posting.updateViewCount", params);
		
		if ( updateCount != 1) {
			throw new SQLException("조회수 갱신 실패 : posting[" + seq + "] count[" + viewCount + "]");
		}
		
	}
	@Override
	public boolean updateViewCount(int seq, int viewCount) {
		SqlSession session = config.getSqlSessionFactory().openSession(false);
		
		try {
			updateViewCount(session, seq, viewCount);
			session.commit();
			return true;
		} catch ( SQLException e) {
			session.rollback();
			throw new DaoException(e.getMessage(), e);
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