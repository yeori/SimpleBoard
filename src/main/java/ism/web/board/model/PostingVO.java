package ism.web.board.model;

public class PostingVO {
	private Integer seq;
	private String title;
	private String content;
	private int viewCount;
	private String whenCreated;
	private UserVO writer;
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getWhenCreated() {
		return whenCreated;
	}
	public void setWhenCreated(String whenCreated) {
		this.whenCreated = whenCreated;
	}
	public UserVO getWriter() {
		return writer;
	}
	public void setWriter(UserVO writer) {
		this.writer = writer;
	}
	public PostingVO(Integer seq, String title, String content, int viewCount,
			String whenCreated, UserVO writer) {
		super();
		this.seq = seq;
		this.title = title;
		this.content = content;
		this.viewCount = viewCount;
		this.whenCreated = whenCreated;
		this.writer = writer;
	}
	public PostingVO(String title, String content, UserVO writer) {
		this.title = title;
		this.content = content;
		this.viewCount = 0;
		this.writer = writer;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((seq == null) ? 0 : seq.hashCode());
		result = prime * result + ((writer == null) ? 0 : writer.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostingVO other = (PostingVO) obj;
		if (seq == null) {
			if (other.seq != null)
				return false;
		} else if (!seq.equals(other.seq))
			return false;
		if (writer == null) {
			if (other.writer != null)
				return false;
		} else if (!writer.equals(other.writer))
			return false;
		return true;
	}
	
	
}
