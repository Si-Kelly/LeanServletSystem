package org.leanservlet.db;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * A WebFilter that simply closes the open DBconnection.
 * 
 * @author Si
 * 
 */
public class DBCloseConnectionFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(req, response);
		} finally {
			DB.closeOpenConnection(req);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
