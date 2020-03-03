package org.jeesl.util.filter.xml.system.io;

import java.util.List;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.XlsSheet;

public class XmlReportFilter
{
	final static Logger logger = LoggerFactory.getLogger(XmlReportFilter.class);
	
	public static XlsSheet fSheet(Report report, String code) throws JeeslNotFoundException
	{
		if(!report.isSetXlsWorkbook()) {throw new JeeslNotFoundException("Now workbook");}
		else if(!report.getXlsWorkbook().isSetXlsSheets()){throw new JeeslNotFoundException("Now sheets in workbook");}
		else {return fSheet(report.getXlsWorkbook().getXlsSheets().getXlsSheet(),code);}
	}
	public static XlsSheet fSheet(List<XlsSheet> sheets, String code) throws JeeslNotFoundException
	{	
		for(XlsSheet sheet : sheets)
		{
			logger.info("Checking sheet "+sheet.getCode());
			if(sheet.getCode().equals(code)) {return sheet;}
		}
		throw new JeeslNotFoundException("No sheet for code:"+code);
	}
}