package ism.web.board.db.dao;

import ism.web.board.db.DbConfig;
import ism.web.board.model.PostingVO;
import ism.web.board.model.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostingDao implements IPostingDao {
	private DbConfig config;
	
	public PostingDao ( DbConfig config) {
		this.config = config;
	}
	
	private int getPk( ResultSet rs) throws SQLException {
		
		if ( rs.next()) {
			return rs.getInt(1);
		} else {
			throw new SQLException("[error 4002] fail to get PK ");
		}
	}
	
	@Override
	public PostingVO findBySeq( int seq) throws DaoException {
		String query = "SELECT seq, title, content, views, when_created, fk_writer FROM postings WHERE seq = ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = config.getConnection(false);
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, seq);
			rs = stmt.executeQuery();
		
			PostingVO posting = null;
			if ( rs.next()) {
				posting = asPosting(rs);
			}
			return posting;
		} catch (SQLException e) {
			throw new DaoException("[error 4000] fail to read all postings", e);
		} finally {
			config.release(conn, stmt, rs);
		}
	}
	@Override
	public PostingVO insert(PostingVO posting) throws DaoException {
		String query = "INSERT INTO postings (title, content, fk_writer) VALUES (?, ?, ?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = config.getConnection(false);
			stmt = conn.prepareStatement(query);
			stmt.setString( 1, posting.getTitle());
			stmt.setString( 2, posting.getContent());
			stmt.setInt( 3, posting.getWriter().getSeq());
			
			int cnt = stmt.executeUpdate();
			if ( cnt != 1) {
				throw new DaoException("[error 4003] fail to insert new posting : update count is " + cnt );
			}
			
			int pk = getPk ( stmt.getGeneratedKeys());
			conn.commit();
			
			
			return findBySeq(pk) ;
		} catch (SQLException e) {
			throw new DaoException("[error 4001] fail to insert new posting :" + posting + ", query : " + stmt , e);
		} finally {
			config.release(conn, stmt, rs);
		}
	}

	private PostingVO asPosting( ResultSet rs) throws SQLException {
		int seq = rs.getInt("seq");
		String title = rs.getString("title");
		String content = rs.getString("content");
		int viewCount = rs.getInt("views");
		String whenCreated = rs.getString("when_created");
		UserVO writer = config.getDaoRepository().getUserDao().findBySeq(rs.getInt("fk_writer"));
		PostingVO posting = new PostingVO(seq, title, content, viewCount, whenCreated, writer);
		return posting;
	}
	@Override
	public List<PostingVO> findAll() throws DaoException {
		String query = "SELECT seq, title, content, views, when_created, fk_writer FROM postings";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = config.getConnection(false);
			stmt = conn.prepareStatement(query);
			
			rs = stmt.executeQuery();
			
			List<PostingVO> postings = new ArrayList<PostingVO>();
			while ( rs.next()) {
				postings.add(asPosting(rs));
			}
			return postings;
		} catch (SQLException e) {
			throw new DaoException("[error 4000] fail to read all postings", e);
		} finally {
			config.release(conn, stmt, rs);
		}
	}
	
}