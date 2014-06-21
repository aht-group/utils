package net.sf.ahtutils.doc.ofx.qa;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.qa.test.OfxTableQaTestFactory;
import net.sf.ahtutils.doc.ofx.qa.test.OfxTableQaTestResultFactory;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Test;
import net.sf.ahtutils.xml.status.Translations;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.table.OfxCellFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxSectionQaCategoryFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSectionQaCategoryFactory.class);

	private String[] headerKeys;
	private OfxTableQaTestFactory fOfxTableTest;
	private OfxTableQaTestResultFactory fOfxTableTestResult;
	
	public OfxSectionQaCategoryFactory(Configuration config, String lang, Translations translations)
	{
		super(config,lang,translations);
		fOfxTableTest = new OfxTableQaTestFactory(config,lang,translations);
		fOfxTableTestResult = new OfxTableQaTestResultFactory(config,lang,translations);
	}
	
	public Section build(Category category) throws OfxAuthoringException
	{
		Section section = XmlSectionFactory.build();

		section.getContent().add(XmlTitleFactory.build(category.getName()));
		
		Comment comment = XmlCommentFactory.build();
		DocumentationCommentBuilder.doNotModify(comment);
		section.getContent().add(comment);
		
		for(Test test : category.getTest())
		{
			section.getContent().add(buildSection(test));
		}
		
		return section;
	}
	
	private Section buildSection(Test test) throws OfxAuthoringException
	{
		Section section = XmlSectionFactory.build();
		section.getContent().add(XmlTitleFactory.build(test.getName()));
		
		if(test.isSetDescription() && test.getDescription().isSetValue())
		{
			Paragraph p = XmlParagraphFactory.build();
			p.getContent().add(test.getDescription().getValue());
			section.getContent().add(p);
		}
		
		section.getContent().add(fOfxTableTest.buildTestTable(test));
		section.getContent().add(fOfxTableTestResult.buildTestTable(test));
		
		return section;
	}
}