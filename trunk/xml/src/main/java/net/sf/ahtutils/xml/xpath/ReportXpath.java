package net.sf.ahtutils.xml.xpath;

import java.util.List;

import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReportXpath
{
	static Log logger = LogFactory.getLog(ReportXpath.class);
	
	public static synchronized Media getMedia(List<Media> list, String type) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Report report = new Report();
		report.getMedia().addAll(list);
		JXPathContext context = JXPathContext.newContext(report);
		
		@SuppressWarnings("unchecked")
		List<Media> listResult = (List<Media>)context.selectNodes("media[@type='"+type+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Media.class.getSimpleName()+" for type="+type);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Media.class.getSimpleName()+" for type="+type);}
		return listResult.get(0);
	}
}