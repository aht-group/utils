package org.jeesl.factory.txt.system.io.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.jeesl.factory.ejb.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.interfaces.model.io.report.xlsx.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.io.report.xlsx.JeeslReportSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtIoGroupFactory<SHEET extends JeeslReportSheet<?,?,?,?,GROUP,?>,
								GROUP extends JeeslReportColumnGroup<?,?,SHEET,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtIoGroupFactory.class);
		
	@SuppressWarnings("unused")
	private String localeCode;
	
	public TxtIoGroupFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	@SuppressWarnings({"unchecked" })
	public Map<GROUP,List<String>> toContextKeys(SHEET sheet, JXPathContext context)
	{
		Map<GROUP,List<String>> map = new HashMap<GROUP,List<String>>();
		for(GROUP g : EjbIoReportColumnGroupFactory.toListVisibleGroups(sheet))
		{
			if(g.getQueryColumns()!=null && !g.getQueryColumns().trim().isEmpty())
			{
				List<String> list = new ArrayList<String>();
				
				Iterator<Object> dynamicIterator = context.iterate(g.getQueryColumns().trim());
				while (dynamicIterator.hasNext())
		        {
		        	Object s = dynamicIterator.next();
//		        	logger.info(s);
		        	list.add(s.toString());
		        }	
				map.put(g,list);
			}
		}
		return map;
	}
}