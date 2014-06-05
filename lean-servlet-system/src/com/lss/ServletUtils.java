package com.lss;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

public class ServletUtils {

	public static void forwardTo(HttpServletRequest request, HttpServletResponse response, String nextJSP) throws ServletException {
		try {
			request.getServletContext().getRequestDispatcher(nextJSP).forward(request, response);
		} catch (IOException e) {
			LogFactory.getLog(ServletUtils.class).error("IOException thrown while forwarding to JSP", e);
		}
	}

	public static void writeObject(HttpServletResponse response, Object obj) {
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(new Gson().toJson(obj));
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void redirect(HttpServletRequest request, HttpServletResponse response, String nextJSP) {
		try {
			response.sendRedirect(request.getContextPath() + "/" + nextJSP);
		} catch (IOException e) {
			LogFactory.getLog(ServletUtils.class).error("IOException thrown while forwarding to JSP", e);
		}
	}

}
