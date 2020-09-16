package org.jeesl.jsf.functions;

import org.apache.commons.lang.StringEscapeUtils;

public final class StringEscape {
	 public static String escapeJavaScript(String value)
	 {
		return StringEscapeUtils.escapeJavaScript(value);
	 }

	 public static String escapeJava(String value)
	 {
		return StringEscapeUtils.escapeJava(value);
	 }

	 public static String escapeHtml(String value)
	 {
		return StringEscapeUtils.escapeHtml(value);
	 }
}
