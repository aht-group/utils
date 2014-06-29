package net.sf.ahtutils.doc.ofx.qa.test;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.qa.Test;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.trancoder.html.HtmlTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxTableQaTestFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxTableQaTestFactory.class);

	private String[] headerKeys;
	
	public OfxTableQaTestFactory(Configuration config, String lang, Translations translations)
	{
		super(config,lang,translations);
	}
	
	public Table buildTestTable(Test test) throws OfxAuthoringException
	{
		
		try
		{
			Table table = new Table();
			Lang lCaption = StatusXpath.getLang(translations, "auTableCaptionQaTest", lang);
			table.setTitle(XmlTitleFactory.build(lCaption.getTranslation()+" "+test.getCode()));
			
			table.setSpecification(createTableSpecifications());
			table.setContent(createTableContent(test));
			JaxbUtil.trace(table);
			return table;
		}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	private Specification createTableSpecifications()
	{
		Columns cols = new Columns();
		cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
		cols.getColumn().add(OfxColumnFactory.flex(80));
		
		Specification specification = new Specification();
		specification.setFloat(XmlFloatFactory.build(false));
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
//		body.getRow().add(buildDescription(test));
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
			logger.trace("Steps");
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