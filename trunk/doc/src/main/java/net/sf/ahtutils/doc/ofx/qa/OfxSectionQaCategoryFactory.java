package net.sf.ahtutils.doc.ofx.qa;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Test;
import net.sf.ahtutils.xml.status.Translations;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.table.OfxCellFactory;
import org.openfuxml.factory.table.OfxColumnFactory;
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
	
	public OfxSectionQaCategoryFactory(Configuration config, String lang, Translations translations)
	{
		super(config,lang,translations);
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
	
	private Section buildSection(Test test)
	{
		Section section = XmlSectionFactory.build();
		section.getContent().add(XmlTitleFactory.build(test.getName()));
		
		Paragraph p = XmlParagraphFactory.build();
		p.getContent().add("Test");
		section.getContent().add(p);
		section.getContent().add(toOfx(test));
		
		return section;
	}
	
	public Table toOfx(Test test)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		table.setContent(createContent(test));
		return table;
	}
	
	private Specification createSpecifications()
	{
		Columns cols = new Columns();
		cols.getColumn().add(OfxColumnFactory.flex(30));
		cols.getColumn().add(OfxColumnFactory.flex(60));
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		
		return specification;
	}
	
	protected Row createHeaderRow(Test test)
	{
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell(test.getCode()));
		row.getCell().add(OfxCellFactory.createParagraphCell(test.getName()));
		
		return row;
	}
	
	private Content createContent(Test test)
	{
		Head head = new Head();
		head.getRow().add(createHeaderRow(test));
		
		Body body = new Body();
		body.getRow().add(createDescription(test));
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createDescription(Test test)
	{
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell("Desc"));
		row.getCell().add(OfxCellFactory.createParagraphCell(test.getCode()));
		return row;
	}	
	
}