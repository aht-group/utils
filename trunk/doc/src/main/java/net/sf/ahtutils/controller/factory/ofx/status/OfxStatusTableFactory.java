package net.sf.ahtutils.controller.factory.ofx.status;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.StringIO;

import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Columns;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Specification;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.table.OfxCellFactory;
import org.openfuxml.factory.table.OfxColumnFactory;
import org.openfuxml.renderer.processor.latex.content.table.LatexGridTableRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxStatusTableFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxStatusTableFactory.class);
		
	private List<Status> lStatus;
	private final String[] headerKeys;
	private Translations translations;
	
	public OfxStatusTableFactory(List<Status> lStatus, String[] headerKeys, Translations translations)
	{
		this.lStatus=lStatus;
		this.headerKeys=headerKeys;
		this.translations=translations;
	}
	
	public void saveTable(File f, String lang)
	{
		try
		{
			logger.debug("Saving Reference to "+f);
			LatexGridTableRenderer renderer = new LatexGridTableRenderer();
			renderer.render(toOfx(lang));
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(f, actual.toString());
			
		}
		catch (OfxAuthoringException e) {logger.error("Something went wrong during ofx/latex transformation ",e);}
		catch (IOException e) {logger.error("Cannot save the file to "+f.getAbsolutePath(),e);}
	}
	
	public Table toOfx(String lang)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		
		try{table.setContent(createContent(lang));}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		
		return table;
	}
	
	private Specification createSpecifications()
	{
		Columns cols = new Columns();
		cols.getColumn().add(OfxColumnFactory.createCol(10));
		cols.getColumn().add(OfxColumnFactory.createCol(20));
		cols.getColumn().add(OfxColumnFactory.createCol(30));
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		
		return specification;
	}
	
	private Content createContent(String targetLang) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Row row = new Row();
		for(String headerKey : headerKeys)
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getLang(translations, headerKey, targetLang).getTranslation()));
		}
		
		Head head = new Head();
		head.getRow().add(row);
		
		Body body = new Body();
		
		for(Status status : lStatus)
		{
			body.getRow().add(createRow(targetLang,status));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(String targetLang, Status status) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{		
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell(status.getCode()));
		
		row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getLang(status.getLangs(), targetLang).getTranslation()));
		row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getDescription(status.getDescriptions(), targetLang).getValue()));
		
		return row;
	}	
}