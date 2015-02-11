package ism.web.board.db.dao;

import ism.web.board.model.PostingVO;

import java.util.List;

public interface IPostingDao extends IDao<IPostingDao> {
	
	public List<PostingVO> findAll() throws DaoException;

	public PostingVO findBySeq ( int seq, boolean updateViewCount) throws DaoException;
	
	public PostingVO insert(PostingVO posting) throws DaoException;

	/**
	 * 조회수를 변경함(글을 조회할때 사용됨)
	 * @param seq
	 * @param viewCount
	 */
	public boolean updateViewCount(int seq, int viewCount);
	
}
