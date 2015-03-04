package ism.web.board.servlet.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class GatewayFilter
 */
/*
@WebFilter(
		dispatcherTypes = {DispatcherType.REQUEST }
					, 
		urlPatterns = { "/*" }, 
		initParams = { 
				@WebInitParam(name = "key", value = "value")
		})
*/
public class GatewayFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(GatewayFilter.class);
    private FilterConfig fConfig;

	/**
     * Default constructor. 
     */
    public GatewayFilter() {
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

		// pass the request along the filter chain
		HttpServletRequest req = ( HttpServletRequest) request;
		HttpServletResponse res = ( HttpServletResponse)response;
		
		ServletContext ctx = fConfig.getServletContext();
		String ctxpath = ctx.getContextPath();
		String uri = req.getRequestURI();
		String path = uri.substring(ctxpath.length()).trim();
		
		if ( path.endsWith("/")) {
			// TODO 요청 url이 '/' 로 끝나는 경우 제거해줘야 하는데
			//      메인 페이지 요청( http://url.com/ ) 인 경우는 제거하면 안됨.
			path = path.substring(0, path.length()-1);
			logger.debug("stripped '/' : " + path);
		}
		
		logger.debug("path[" + path + "]");
		if ( path.equals("") || 
				path.startsWith("/static") 
				|| path.endsWith(".jsp") 
				|| path.endsWith(".html") ) {
			logger.debug("static  resources[{}]", path);
			chain.doFilter(request, response);
		} else {
			logger.debug("servlet resources[{}]", path);
			req.getRequestDispatcher("/ctrl" + path ).forward(req, res);
			
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig = fConfig;
		fConfig.getServletContext();
		
		
	}

}
