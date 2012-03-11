package net.sf.ahtutils.xml.xpath;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.xml.report.Jr;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportXpath
{
	final static Logger logger = LoggerFactory.getLogger(ReportXpath.class);
	
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
	
	public static synchronized Report getReport(Reports reports, String id) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(reports);
		
		@SuppressWarnings("unchecked")
		List<Report> listResult = (List<Report>)context.selectNodes("report[@id='"+id+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Report.class.getSimpleName()+" for id="+id);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Report.class.getSimpleName()+" for id="+id);}
		return listResult.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized ArrayList<Jr> getSubreports(Reports reports, String id, String format)
	{
		JXPathContext context = JXPathContext.newContext(reports);
		return (ArrayList<Jr>) context.getValue("jr[parent::parent::report/id='" +id +"' and parent::media/@type='" +format +"' and @type='sr']");
	}
}