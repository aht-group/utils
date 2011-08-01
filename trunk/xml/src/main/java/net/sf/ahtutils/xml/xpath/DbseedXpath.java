package net.sf.ahtutils.xml.xpath;

import java.util.List;

import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.ahtutils.xml.dbseed.Seed;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DbseedXpath
{
	static Log logger = LogFactory.getLog(DbseedXpath.class);
	
	public static synchronized Seed getSeed(Db dbSeed, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(dbSeed);
		
		StringBuffer sb = new StringBuffer();
		sb.append("seed[@code='"+code+"']");
		
		@SuppressWarnings("unchecked")
		List<Seed> listResult = (List<Seed>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Seed.class.getSimpleName()+" for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Seed.class.getSimpleName()+" for code="+code);}
		return listResult.get(0);
	}
}