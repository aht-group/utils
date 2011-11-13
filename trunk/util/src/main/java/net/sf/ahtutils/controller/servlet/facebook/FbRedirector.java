package net.sf.ahtutils.controller.servlet.facebook;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.ahtutils.xml.cloud.facebook.App;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.xml.net.Url;
import net.sf.exlp.xml.xpath.NetXpath;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FbRedirector
{
	public static enum Code{app,canvas,fbauth}
	
	static Log logger = LogFactory.getLog(FbRedirector.class);
	
	private App app;
	
	public FbRedirector(App app)
	{
		this.app=app;
	}
	
	public void toFbOauth(HttpServletResponse response)
	{
		try
		{
			Url url = NetXpath.getUrl(app.getRedirect().getUrl(), Code.fbauth.toString());
			StringBuffer sb = new StringBuffer();
			sb.append("http://www.facebook.com/dialog/oauth/?");
			sb.append("scope=").append(app.getScope());
			sb.append("&client_id=").append(app.getAppId());
			sb.append("&redirect_uri=").append(url.getValue());
			
			redirect(response, sb.toString());
		}
		catch (ExlpXpathNotFoundException e) {logger.error(e);}
		catch (ExlpXpathNotUniqueException e) {logger.error(e);}
	}
	
	public void toApp(HttpServletResponse response)
	{
		try
		{
			Url url = NetXpath.getUrl(app.getRedirect().getUrl(), Code.app.toString());
			redirect(response, url.getValue());
		}
		catch (ExlpXpathNotFoundException e) {logger.error(e);}
		catch (ExlpXpathNotUniqueException e) {logger.error(e);}
	}
	
	public void toCanvas(HttpServletResponse response)
	{
		try
		{
			Url url = NetXpath.getUrl(app.getRedirect().getUrl(), Code.canvas.toString());
			redirect(response, url.getValue());
		}
		catch (ExlpXpathNotFoundException e) {logger.error(e);}
		catch (ExlpXpathNotUniqueException e) {logger.error(e);}
	}
	
	private void redirect(HttpServletResponse response, String url)
	{
		logger.debug("Redirecting to "+url);
		try {response.sendRedirect(response.encodeRedirectURL(url));}
		catch (IOException e) {logger.error(e);}
	}
}
