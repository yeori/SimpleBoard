package ism.web.board.action;
/**
 * 응답 페이지로의 이동 방법을 결정해줍니다.
 * 
 * - REDIRECT : 클라이언트에게 재접속할 URL을 응답으로 전송해줘야 함.
 * - FORWARD  : 주어진 PATH를 응답으로 전송해주어야 함.
 * 
 * @author chminseo
 *
 */
public class View {

	private boolean redirect ;
	
	private String path ;
	
	public View(){}
	public View(String path, boolean redirect) {
		this.path = path;
		this.redirect = redirect;
	}

	public  void setRedirect(String path) {
		redirect = true;
		this.path = path;
	}
	
	public void setFoward(String path) {
		redirect = false;
		this.path = path;
	}
	
	public boolean isRedirect() {
		return redirect;
	}
	
	public boolean isForward(){
		return !redirect;
	}

	public String getPath() {
		return this.path;
	}
	@Override
	public String toString() {
		return "[" + (redirect?"REDIRECT":"FORWARD") + " to '" + path + "']";
	}
	
	
}