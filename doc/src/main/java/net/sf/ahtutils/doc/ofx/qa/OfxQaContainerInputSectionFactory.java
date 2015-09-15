package net.sf.ahtutils.doc.ofx.qa;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FilenameUtils;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Qa;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.survey.Template;

public class OfxQaContainerInputSectionFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxQaContainerInputSectionFactory.class);

	public OfxQaContainerInputSectionFactory(Configuration config, String lang, Translations translations)
	{
		this(config,new String[] {lang},translations);
	}
	public OfxQaContainerInputSectionFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
	}
	
	// FR Categories
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
	
	// NFR Sections
	public Section build(Template template, String path) throws OfxAuthoringException
	{
		Section section = XmlSectionFactory.build();
		section.setContainer(true);
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		section.getContent().add(comment);
		
		for(net.sf.ahtutils.xml.survey.Section s : template.getSection())
		{
			for(String lang : langs)
			{
				Section sub = XmlSectionFactory.build();
				sub.setContainer(true);
				sub.setLang(lang);
				sub.setInclude(FilenameUtils.normalize(lang+"/"+path+"/"+s.getPosition()));
				section.getContent().add(sub);
			}
		}
		
		return section;
	}
}