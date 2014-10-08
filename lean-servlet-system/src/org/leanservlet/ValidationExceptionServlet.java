package org.leanservlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.leanservlet.bind.ValidationException;

/**
 * Servlet implementation class ValidationExceptionServlet
 */
public class ValidationExceptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(ValidationExceptionServlet.class);

	private void handleException(HttpServletRequest request, HttpServletResponse response) {

		ValidationException ve = (ValidationException) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		log.debug("Validation error:", ve);
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		ServletUtils.writeObject((HttpServletResponse) response, ve.getValidations());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleException(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleException(request, response);
	}

}
