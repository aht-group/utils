package net.sf.ahtutils.doc.security;

import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.content.list.Type;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.renderer.latex.content.list.LatexListRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxCategoryListFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxCategoryListFactory.class);
	
	public OfxCategoryListFactory(Configuration config,String lang, Translations translations)
	{
		super(config,lang,translations);
	}
	
	public String saveDescription(java.util.List<Category> lRc) throws OfxAuthoringException
	{
		try
		{
			LatexListRenderer renderer = new LatexListRenderer(false);
			renderer.render(create(lRc),new LatexSectionRenderer(0,null));
			StringWriter sw = new StringWriter();
			renderer.write(sw);
			return sw.toString();
		}
		catch (IOException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	public List create(java.util.List<Category> lRc)
	{
		Comment comment = XmlCommentFactory.build();
		DocumentationCommentBuilder.doNotModify(comment);
		
		List list = createList();
		list.setComment(comment);
		
		for(Category category : lRc)
		{
			try {list.getItem().add(createItem(category));}
			catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
			catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		}
		
		return list;
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