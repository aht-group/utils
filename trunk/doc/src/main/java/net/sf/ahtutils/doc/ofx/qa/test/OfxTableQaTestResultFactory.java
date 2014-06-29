package net.sf.ahtutils.doc.ofx.qa.test;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.qa.Result;
import net.sf.ahtutils.xml.qa.Test;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.table.Body;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxTableQaTestResultFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxTableQaTestResultFactory.class);
	
	public OfxTableQaTestResultFactory(Configuration config, String lang, Translations translations)
	{
		super(config,lang,translations);
	}
	
	public Table buildTestTable(Test test) throws OfxAuthoringException
	{
		
		try
		{
			Table table = new Table();
			Lang lCaption = StatusXpath.getLang(translations, "auTableCaptionQaTestResults", lang);
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
		row.getCell().add(OfxCellFactory.createParagraphCell("User"));
		row.getCell().add(OfxCellFactory.createParagraphCell("Comment"));
		
		return row;
	}
	
	private Content createTableContent(Test test)
	{
		Head head = new Head();
		head.getRow().add(createHeaderRow(test));
		
		Body body = new Body();
		if(test.isSetResults())
		{
			for(Result result : test.getResults().getResult())
			{
				body.getRow().add(buildRow(result));
			}
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row buildRow(Result result)
	{
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell(result.getStaff().getUser().getLastName()));
		row.getCell().add(OfxCellFactory.createParagraphCell(""));
		return row;
	}
	
}