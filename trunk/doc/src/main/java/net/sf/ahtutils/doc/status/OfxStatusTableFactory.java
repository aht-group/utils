package net.sf.ahtutils.doc.status;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.security.AbstractOfxSecurityFactory;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
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
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxStatusTableFactory extends AbstractOfxSecurityFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxStatusTableFactory.class);
	
	private static String keyCaption = "auTableStatusCaption";
	
	private int[] colWidths = {10,20,30};

	public OfxStatusTableFactory(Configuration config, String lang, Translations translations)
	{
		super(config,lang,translations);		
	}
	
	public String saveTable(String id, List<Status> lStatus, String[] headerKeys) throws OfxAuthoringException
	{
		try
		{	
			String captionKey = id;
			
			while(captionKey.indexOf(".")>0)
			{
				int index = captionKey.indexOf(".");
				captionKey = captionKey.substring(0,index-1)+captionKey.substring(index+1, index+2).toUpperCase()+captionKey.substring(index+2, captionKey.length());
			}
			captionKey = captionKey.substring(0, 1).toUpperCase()+captionKey.substring(1, captionKey.length());
			captionKey = keyCaption+captionKey;
			id = "table.status."+id;
			
			Comment comment = XmlCommentFactory.build();
			DocumentationCommentBuilder.fixedId(comment, id);
			DocumentationCommentBuilder.translationKeys(comment,config,UtilsDocumentation.keyTranslationFile);
			DocumentationCommentBuilder.tableHeaders(comment,headerKeys);
			DocumentationCommentBuilder.tableKey(comment,captionKey,"Table Caption");
			DocumentationCommentBuilder.doNotModify(comment);
			
			Table table = toOfx(lStatus,headerKeys);
			table.setId(id);
			table.setComment(comment);
			
			Lang lCaption = StatusXpath.getLang(translations, captionKey, lang);
			table.setTitle(XmlTitleFactory.build(lCaption.getTranslation()));
			
			LatexTableRenderer renderer = new LatexTableRenderer();
			renderer.render(table);
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			return actual.toString();
			
		}
		catch (IOException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	public Table toOfx(List<Status> lStatus, String[] headerKeys)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		
		try{table.setContent(createContent(lStatus, headerKeys));}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		
		return table;
	}
	
	private Specification createSpecifications()
	{
		Columns cols = new Columns();
		for(int i : colWidths)
		{
			cols.getColumn().add(OfxColumnFactory.createCol(i));
		}
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		
		return specification;
	}
	
	private Content createContent(List<Status> lStatus, String[] headerKeys) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Row row = new Row();
		for(String headerKey : headerKeys)
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getLang(translations, headerKey, lang).getTranslation()));
		}
		
		Head head = new Head();
		head.getRow().add(row);
		
		Body body = new Body();
		
		for(Status status : lStatus)
		{
			body.getRow().add(createRow(status));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(Status status) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{		
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell(status.getCode()));
		
		row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getLang(status.getLangs(), lang).getTranslation()));
		row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getDescription(status.getDescriptions(), lang).getValue()));
		
		return row;
	}
	
	public int[] getColWidths() {return colWidths;}
	public void setColWidths(int[] colWidths) {this.colWidths = colWidths;}
}