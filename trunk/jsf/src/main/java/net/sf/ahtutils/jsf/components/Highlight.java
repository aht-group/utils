package net.sf.ahtutils.jsf.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="net.sf.ahtutils.jsf.components.Highlight")
public class Highlight extends UINamingContainer
{
	final static Logger logger = LoggerFactory.getLogger(Highlight.class);
	
	public String style(String input)
	{
		logger.info("Looking for Resource "+input);
		InputStream is = this.getClass().getResourceAsStream(input);
		
		if(is!=null)
		{
			StringWriter writer = new StringWriter();
			try {
			IOUtils.copy(is, writer, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writer.toString();
		}
		else
		{
			return "Resource not found";
		}
		
	}
}