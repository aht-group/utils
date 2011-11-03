package net.sf.ahtutils.xml.xpath;

import java.util.List;

import net.sf.ahtutils.xml.access.AclContainer;
import net.sf.ahtutils.xml.access.RoleAutoAssign;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AccessXpath
{
	static Log logger = LogFactory.getLog(AccessXpath.class);
	
	public static synchronized RoleAutoAssign getAutoAssign(AclContainer aclContainer,String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(aclContainer);
		
		StringBuffer sb = new StringBuffer();
		sb.append("roleAutoAssign[@code='"+code+"']");
		
		@SuppressWarnings("unchecked")
		List<RoleAutoAssign> listResult = (List<RoleAutoAssign>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+RoleAutoAssign.class.getSimpleName()+" for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+RoleAutoAssign.class.getSimpleName()+" for code="+code);}
		return listResult.get(0);
	}
}