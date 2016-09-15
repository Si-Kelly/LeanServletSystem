package org.leanservlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.RequestUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet implementation class ErrorServlet
 */

public class ErrorServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(ErrorServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String uri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
		Throwable t = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		StringBuilder stackTraceString = new StringBuilder();
		if (t != null) {
			StringBuilder stackTraceHTML = new StringBuilder();
			String stackTrace = getPartialServletStackTrace(t);
			stackTraceHTML.append("<p><b>");
			stackTraceHTML.append("Exception:");
			stackTraceHTML.append("</b> <pre>");
			stackTraceHTML.append(RequestUtil.filter(stackTrace));
			stackTraceHTML.append("</pre></p>");

			stackTraceString.append(stackTrace);

			int loops = 0;
			Throwable rootCause = t.getCause();
			while (rootCause != null && (loops < 10)) {
				stackTrace = getPartialServletStackTrace(rootCause);
				stackTraceHTML.append("<p><b>");
				stackTraceHTML.append("Root Cause:");
				stackTraceHTML.append("</b> <pre>");
				stackTraceHTML.append(RequestUtil.filter(stackTrace));
				stackTraceHTML.append("</pre></p>");

				stackTraceString.append(stackTrace);

				// In case root cause is somehow heavily nested
				rootCause = rootCause.getCause();
				loops++;
			}
			request.setAttribute("org.leanservlet.error.stack_trace", stackTraceHTML.toString());
		}
		if (uri != null && uri.contains("/json/")) {
			if (response.getStatus() == HttpServletResponse.SC_OK) {
				log.debug("Setting request status to " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			log.error("Server error from json request, responding with status " + response.getStatus(), t);
			ServletUtils.writeString(response, stackTraceString.toString());
		} else if (uri != null && uri.contains("/ajax/")) {
			if (response.getStatus() == HttpServletResponse.SC_OK) {
				log.debug("Setting request status to " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			log.error("Server error from ajax request, responding with status " + response.getStatus(), t);
			ServletUtils.forwardTo(request, response, "/error.jsp");
		} else {
			log.error("Server error from page request, responding with error page.", t);
			ServletUtils.forwardTo(request, response, "/error-page.jsp");
		}
	}

	protected String getPartialServletStackTrace(Throwable t) {
		StringBuilder trace = new StringBuilder();
		trace.append(t.toString()).append('\n');
		StackTraceElement[] elements = t.getStackTrace();
		int pos = elements.length;
		for (int i = elements.length - 1; i >= 0; i--) {
			if ((elements[i].getClassName().startsWith("org.apache.catalina.core.ApplicationFilterChain"))
					&& (elements[i].getMethodName().equals("internalDoFilter"))) {
				pos = i;
				break;
			}
		}
		for (int i = 0; i < pos; i++) {
			if (!(elements[i].getClassName().startsWith("org.apache.catalina.core."))) {
				trace.append('\t').append(elements[i].toString()).append('\n');
			}
		}
		return trace.toString();
	}

}
