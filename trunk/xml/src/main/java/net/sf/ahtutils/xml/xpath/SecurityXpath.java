package net.sf.ahtutils.xml.xpath;

import java.util.List;

import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.View;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityXpath
{
	final static Logger logger = LoggerFactory.getLogger(SecurityXpath.class);
	
	public static synchronized View getMenuItem(Access access,String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(access);
		
		StringBuffer sb = new StringBuffer();
		sb.append("//view[@code='").append(code).append("']");
		
		@SuppressWarnings("unchecked")
		List<View> listResult = (List<View>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+View.class.getSimpleName()+" for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+View.class.getSimpleName()+" for code="+code);}
		return listResult.get(0);
	}
}