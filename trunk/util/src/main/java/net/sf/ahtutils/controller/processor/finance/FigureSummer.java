package net.sf.ahtutils.controller.processor.finance;

import java.util.List;

import net.sf.ahtutils.controller.factory.xml.finance.XmlFigureFactory;
import net.sf.ahtutils.xml.finance.Figure;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FigureSummer
{
	final static Logger logger = LoggerFactory.getLogger(FigureSummer.class);
	
	@SuppressWarnings("unchecked")
	public static Figure sum(Object o)
	{
		double sum = 0;
		JXPathContext context = JXPathContext.newContext(o);
		List<Figure> list = (List<Figure>)context.selectNodes("//figure");
		logger.trace("Elements found: "+list.size());
		for(Figure f : list)
		{
			sum=sum+f.getValue();
		}
		return XmlFigureFactory.create("xx", sum);
	}
}

