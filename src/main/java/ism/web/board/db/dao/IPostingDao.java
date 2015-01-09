package ism.web.board.db.dao;

import ism.web.board.model.PostingVO;

import java.util.List;

public interface IPostingDao {
	
	public List<PostingVO> findAll() throws DaoException;

	public PostingVO findBySeq ( int seq) throws DaoException;
	
	public PostingVO insert(PostingVO posting) throws DaoException;
	
}
