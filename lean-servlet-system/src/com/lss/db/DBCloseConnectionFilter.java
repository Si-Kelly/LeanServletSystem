package com.lss.db;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * A WebFilter that simply closes the open DBconnection.
 * 
 * @author Si
 * 
 */
@WebFilter(urlPatterns = "*")
public class DBCloseConnectionFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
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
