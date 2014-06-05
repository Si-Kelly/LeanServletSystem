package com.lss;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Servlet implementation class ErrorServlet
 */
@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(ErrorServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String uri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
		Throwable t = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

		if (uri != null && uri.contains("/ajax/")) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			log.debug("Server error from AJAX request, responding with Error code " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t);
			ServletUtils.forwardTo(request, response, "/error.jsp");
		} else {
			log.debug("Server error from page request, responding with error page.", t);
			ServletUtils.forwardTo(request, response, "/error-page.jsp");
		}
	}

}
