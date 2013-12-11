package net.sf.ahtutils.controller.factory.ofx.security;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.StringIO;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.list.LatexListRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.xml.content.list.Item;
import org.openfuxml.xml.content.list.List;
import org.openfuxml.xml.content.list.Type;
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
	
	public void saveDescription(File f, java.util.List<Category> lRc)
	{
		try
		{
			logger.debug("Saving Reference to "+f);
			LatexListRenderer renderer = new LatexListRenderer();
			renderer.render(create(lRc),new LatexSectionRenderer(0,null));
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(f, actual.toString());
			
		}
		catch (OfxAuthoringException e) {logger.error("Something went wrong during ofx/latex transformation ",e);}
		catch (IOException e) {logger.error("Cannot save the file to "+f.getAbsolutePath(),e);}
	}
	
	public List create(java.util.List<Category> lRc)
	{
		List result = createList();
		
		for(Category category : lRc)
		{
			try {result.getItem().add(createItem(category));}
			catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
			catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		}
		
		return result;
	}
	
	private List createList()
	{
		Type type = new Type();
		type.setDescription(true);
		
		List list = new List();
		list.setType(type);
		
		return list;
	}
	
	private Item createItem(Category category) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		String description,text;
		
		try
		{
			Lang l = StatusXpath.getLang(category.getLangs(), lang);
			description = l.getTranslation();
		}
		catch (ExlpXpathNotFoundException e){description = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){description = e.getMessage();}
		
		try
		{
			Description d = StatusXpath.getDescription(category.getDescriptions(), lang);
			text = d.getValue();
		}
		catch (ExlpXpathNotFoundException e){text = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){text = e.getMessage();}		
		
		Paragraph p = new Paragraph();
		p.getContent().add(text);
		
		Item item = new Item();
		item.setName(description);
		item.getContent().add(p);
		
		return item;
	}
}