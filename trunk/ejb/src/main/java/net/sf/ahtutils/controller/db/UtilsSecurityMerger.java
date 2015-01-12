package net.sf.ahtutils.controller.db;

import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.xpath.SecurityXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsSecurityMerger
{
	final static Logger logger = LoggerFactory.getLogger(UtilsSecurityMerger.class);
	
    
    public UtilsSecurityMerger()
	{       

	}
	
	public void mergeViews(Access fileVersion, Security restVersion)
	{
		try
		{
			for(Category cAcl : fileVersion.getCategory())
			{
				net.sf.ahtutils.xml.security.Category cRest = SecurityXpath.getCategory(restVersion,cAcl.getCode());
				cAcl.setLangs(cRest.getLangs());
				cAcl.setDescriptions(cRest.getDescriptions());

				if(cAcl.isSetViews())
				{
					for(View vAcl : cAcl.getViews().getView())
					{
						
					}
				}
			}
		}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
	}
}