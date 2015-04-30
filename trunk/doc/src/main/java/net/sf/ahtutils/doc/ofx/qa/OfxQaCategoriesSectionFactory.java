package net.sf.ahtutils.doc.ofx.qa;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Qa;
import net.sf.ahtutils.xml.status.Translations;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxQaCategoriesSectionFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxQaCategoriesSectionFactory.class);

	public OfxQaCategoriesSectionFactory(Configuration config, String lang, Translations translations)
	{
		super(config,lang,translations);
	}
	
	public Section build(Qa qa, String path) throws OfxAuthoringException
	{
		Section section = XmlSectionFactory.build();
		section.setContainer(true);
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		section.getContent().add(comment);
		
		for(Category c : qa.getCategory())
		{
			if(!c.getCode().equals("T"))
			{
				section.getContent().add(buildCategory(c,path));
			}
		}
		
		return section;
	}
	
	private Section buildCategory(Category c, String path)
	{
		Section section = XmlSectionFactory.build();
		section.setContainer(true);
		section.setInclude(path+"/"+c.getCode());
		return section;
	}
	
}