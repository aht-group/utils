package net.sf.ahtutils.xml.xpath;

import java.util.List;

import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.survey.Correlation;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurveyXpath
{
	final static Logger logger = LoggerFactory.getLogger(SurveyXpath.class);
	
	public static Correlation getMenuItem(Correlation correlation,Class<?> t) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		String type = t.getName();
		JXPathContext context = JXPathContext.newContext(correlation);
		
		StringBuffer sb = new StringBuffer();
		sb.append("/correlation[@type='").append(type).append("']");
		
		@SuppressWarnings("unchecked")
		List<Correlation> listResult = (List<Correlation>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Correlation.class.getSimpleName()+" for type="+type);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+View.class.getSimpleName()+" for type="+type);}
		return listResult.get(0);
	}
}