package org.leanservlet;

public class MessageUtil {
	public static String niceFieldName(String n) {
		return n.substring(0, 1).toUpperCase() + n.substring(1);
	}
}
