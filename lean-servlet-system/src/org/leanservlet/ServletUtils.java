package org.leanservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

public class ServletUtils {
	private static String JSP_ROOT = "";

	public static void setJSPRoot(String root) {
		JSP_ROOT = root;
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		return request.getRequestURI().contains("/ajax");
	}

	public static boolean isJSONRequest(HttpServletRequest request) {
		return request.getRequestURI().contains("/json");
	}

	public static void forwardTo(HttpServletRequest request, HttpServletResponse response, String nextJSP) throws ServletException {
		try {
			request.getServletContext().getRequestDispatcher(JSP_ROOT + nextJSP).forward(request, response);
		} catch (IOException e) {
			LogFactory.getLog(ServletUtils.class).error("IOException thrown while forwarding to JSP", e);
		}
	}

	public static void writeObject(HttpServletResponse response, Object obj) {
		PrintWriter pw;
		try {
			response.setContentType("application/json");
			pw = response.getWriter();
			pw.write(new Gson().toJson(obj));
			pw.flush();
		} catch (IOException e) {
			LogFactory.getLog(ServletUtils.class).error("IOException thrown while writing JSON to response", e);
		}

	}

	public static void redirect(HttpServletRequest request, HttpServletResponse response, String redirectURL) {
		try {
			response.sendRedirect(request.getContextPath() + "/" + redirectURL);
		} catch (IOException e) {
			LogFactory.getLog(ServletUtils.class).error("IOException thrown while redirecting", e);
		}
	}

	public static void writeString(HttpServletResponse response, String string) {
		try {
			response.getWriter().write(string);
			response.getWriter().flush();
		} catch (IOException e) {
			LogFactory.getLog(ServletUtils.class).error("IOException thrown while writing a String to response", e);
		}
	}

}
