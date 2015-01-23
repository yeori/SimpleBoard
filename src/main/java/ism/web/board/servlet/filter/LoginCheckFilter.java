package ism.web.board.servlet.filter;

import ism.web.board.action.View;
import ism.web.board.action.Views;
import ism.web.board.model.UserVO;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginCheckFilter
 */
//@WebFilter(
//		urlPatterns = { "/write" }, 
//		initParams = { 
//				@WebInitParam(name = "key", value = "value", description = "test params")
//		})
public class LoginCheckFilter implements Filter {

    private ServletContext ctx;

	/**
     * Default constructor. 
     */
    public LoginCheckFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		System.out.println("ddddddddddddddd");
		// pass the request along the filter chain
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		UserVO user = getUserInSession ( req ) ;
		
		View view = null;
		if ( user == null ) {
		// 로그인하지 않고 접근한 경우
			String path  = parsePath(req);
			System.out.println(path);
			view = moveToLoginPage(req, res);
			res.sendRedirect(view.getPath());
		} else {
			
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.ctx = fConfig.getServletContext();
	}
	
	private String parsePath ( HttpServletRequest request )  {
		String ctxpath = ctx.getContextPath();
		System.out.println("ctxpath :"  + ctxpath); 
		String uri = request.getRequestURI();
		String path = uri.substring(ctxpath.length());
		
		return path ;
	}
	
	private UserVO getUserInSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		// 생성된 session이 없으면 user는 당연히 없음.
		if ( session == null ) {
			return null;
		}
		return (UserVO) session.getAttribute("user");
	}
	
	
	private View moveToLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String urlToGo = parsePath(request);
		
		String queryPart = request.getQueryString();
		if ( queryPart != null) {
			urlToGo += "?" + queryPart ;
		}
		urlToGo = URLEncoder.encode(urlToGo, "UTF-8");
		
		String login = "/SimpleBoard/login" + "?target=" + urlToGo;
		System.out.println("[LOGIN-REQUIRED]" + login);
//		response.sendRedirect(login);
		return Views.REDIRECT(login);
	}

}
