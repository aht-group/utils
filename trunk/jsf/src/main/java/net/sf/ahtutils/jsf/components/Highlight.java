package net.sf.ahtutils.jsf.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="net.sf.ahtutils.jsf.components.Highlight")
public class Highlight extends UINamingContainer
{
	final static Logger logger = LoggerFactory.getLogger(Highlight.class);
	
	public String text(String input, int from, int to)
	{
		logger.trace("Looking for Resource "+input);
		logger.trace("from:"+from+" "+"to:"+to);
		InputStream is = this.getClass().getResourceAsStream(input);
		
		if(is!=null)
		{
			StringWriter writer = new StringWriter();
			
			if(from!=0 || to !=-1)
			{
				try
				{
					List<String> list = IOUtils.readLines(is, "UTF-8");
					for(int i=0;i<list.size();i++)
					{
						if(i>=from && i<=to)
						{
							String s = list.get(i);
							writer.write(s+IOUtils.LINE_SEPARATOR_UNIX);
						}
					}
				}
				catch (IOException e){logger.error(e.getMessage());e.printStackTrace();}
			}
			else
			{			
				try {IOUtils.copy(is, writer, "UTF-8");}
				catch (IOException e){logger.error(e.getMessage());e.printStackTrace();}
			}		
			String s = writer.toString();
			try {writer.close();} catch (IOException e) {e.printStackTrace();}
			return s;
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append("Resource not found: ").append(IOUtils.LINE_SEPARATOR_UNIX);
			sb.append(input).append(IOUtils.LINE_SEPARATOR_UNIX);
			sb.append(" must be available in the classpath");
			return sb.toString();
		}
	}
	
	public String tab(int tab)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<tab;i++)
		{
			sb.append(" ");
		}
		return sb.toString();
	}
}