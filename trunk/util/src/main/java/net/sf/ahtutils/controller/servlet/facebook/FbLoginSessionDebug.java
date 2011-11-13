package net.sf.ahtutils.controller.servlet.facebook;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;

public class FbLoginSessionDebug
{
	@SuppressWarnings("rawtypes")
	public static void debug(HttpSession session, Log logger)
	{
		logger.debug("Debugging FbLoginSession Parameter (line numbers are inaccurate)");
		Enumeration e = session.getAttributeNames();
		while(e.hasMoreElements())
		{
			String key = (String)e.nextElement();
			logger.debug(key+": "+session.getAttribute(key));
		}
	}
}
