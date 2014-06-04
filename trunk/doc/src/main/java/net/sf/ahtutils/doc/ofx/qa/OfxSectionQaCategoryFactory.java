package net.sf.ahtutils.doc.ofx.qa;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Test;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Cell;
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
import org.openfuxml.trancoder.html.HtmlTranscoder;
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
		section.getContent().add(buildTestTable(test));
		
		return section;
	}
	
	private Table buildTestTable(Test test)
	{
		Table table = new Table();
		table.setSpecification(createTableSpecifications());
		table.setContent(createTableContent(test));
		JaxbUtil.trace(table);
		return table;
	}
	
	private Specification createTableSpecifications()
	{
		Columns cols = new Columns();
		cols.getColumn().add(OfxColumnFactory.percentage(20));
		cols.getColumn().add(OfxColumnFactory.percentage(80));
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		
		return specification;
	}
	
	protected Row createHeaderRow(Test test)
	{
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell("Test Case"));
		row.getCell().add(OfxCellFactory.createParagraphCell(test.getCode()));
		
		return row;
	}
	
	private Content createTableContent(Test test)
	{
		Head head = new Head();
		head.getRow().add(createHeaderRow(test));
		
		Body body = new Body();
		body.getRow().add(buildTitle(test));
		body.getRow().add(buildReference(test));
		body.getRow().add(buildDescription(test));
		body.getRow().add(buildPreCondition(test));
		body.getRow().add(buildSteps(test));
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row buildTitle(Test test)
	{
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell("Title"));
		row.getCell().add(OfxCellFactory.createParagraphCell(test.getName()));
		return row;
	}
	
	private Row buildDescription(Test test)
	{
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell("Description"));
		if(test.isSetDescription() && test.getDescription().isSetValue())
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(test.getDescription().getValue()));
		}
		else
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(""));
		}
		return row;
	}
	
	private Row buildReference(Test test)
	{
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell("Reference"));
		if(test.isSetReference() && test.getReference().isSetValue())
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(test.getReference().getValue()));
		}
		else
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(""));
		}
		return row;
	}
	
	private Row buildPreCondition(Test test)
	{
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell("Pre-Condition"));
		if(test.isSetPreCondition() && test.getPreCondition().isSetValue())
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(test.getPreCondition().getValue()));
		}
		else
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(""));
		}
		return row;
	}
	
	private Row buildSteps(Test test)
	{
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell("Test Steps"));
		if(test.isSetSteps() && test.getSteps().isSetValue())
		{
			logger.info("Steps");
			HtmlTranscoder transcoder = new HtmlTranscoder();
			transcoder.transcode(test.getSteps().getValue());
			Cell cell = OfxCellFactory.build();
			cell.getContent().addAll(transcoder.transcode(test.getSteps().getValue()).getContent());
			row.getCell().add(cell);
		}
		else
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(""));
		}
		return row;
	}
}