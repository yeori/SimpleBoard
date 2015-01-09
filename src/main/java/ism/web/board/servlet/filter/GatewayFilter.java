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
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		HttpServletRequest req = ( HttpServletRequest) request;
		HttpServletResponse res = ( HttpServletResponse)response;
		
		ServletContext ctx = fConfig.getServletContext();
		String ctxpath = ctx.getContextPath();
		String uri = req.getRequestURI();
		String path = uri.substring(ctxpath.length());
		
		System.out.println("gateway filter : " + uri + " " + uri + ", path " + path );
		if ( path.startsWith("/static") || path.endsWith(".jsp") || path.endsWith(".html") ) {			
			chain.doFilter(request, response);
		} else {
			req.getRequestDispatcher("/ctrl" + path ).forward(req, res);
			
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.fConfig = fConfig;
		fConfig.getServletContext();
		
		
	}

}
