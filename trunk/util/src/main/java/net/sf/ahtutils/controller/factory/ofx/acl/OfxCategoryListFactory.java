package net.sf.ahtutils.controller.factory.ofx.acl;

import net.sf.ahtutils.xml.access.RoleCategory;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.xml.content.list.Item;
import org.openfuxml.xml.content.list.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxCategoryListFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxCategoryListFactory.class);
		
	private String lang;
	
	public OfxCategoryListFactory(String lang)
	{

		this.lang=lang;
	}
	
	public List create(java.util.List<RoleCategory> lRc)
	{
		List result = createList();
		
		for(RoleCategory rc : lRc)
		{
			try {result.getItem().add(createItem(rc));}
			catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
			catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		}
		
		return result;
	}
	
	private List createList()
	{
		List result = new List();
		
		return result;
	}
	
	private Item createItem(RoleCategory rc) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Lang l = StatusXpath.getLang(rc.getLangs(), lang);
		Description d = StatusXpath.getDescription(rc.getDescriptions(), lang);
		
		Paragraph p = new Paragraph();
		p.getContent().add(d.getValue());
		
		Item item = new Item();
		item.setName(l.getTranslation());
		item.getContent().add(p);
		
		return item;
	}
	
	
}