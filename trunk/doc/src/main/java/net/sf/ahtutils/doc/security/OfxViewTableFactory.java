package net.sf.ahtutils.doc.security;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import net.sf.ahtutils.controller.factory.ofx.security.AbstractOfxSecurityTabelFactory;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

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
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxViewTableFactory extends AbstractOfxSecurityTabelFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxViewTableFactory.class);
		
	public OfxViewTableFactory(String lang, Translations translations)
	{
		super(lang,translations);
	}
	
	public void saveDescription(File f, List<View> lViews, String[] headerKeys)
	{
		try
		{
			logger.warn("Saving Reference to "+f);
			LatexTableRenderer tableRenderer = new LatexTableRenderer();
			
			Table table = toOfx(lViews,headerKeys);
			JaxbUtil.trace(table);
			tableRenderer.render(table);
			
			StringWriter actual = new StringWriter();
			tableRenderer.write(actual);
			
			
			StringIO.writeTxt(f, actual.toString());
			
/*			List<String> headers = new ArrayList<String>();
			headers.add("a");
			headers.add("b");
			DoubleLineTexLongTable dltlt = new DoubleLineTexLongTable(f.getParent(),f.getName());
			dltlt.setHeader("c|c", headers);
			
			String[] test = {"a","bb"};
			for(int i=0;i<100;i++)
			{
			dltlt.addDataRow(test);
			}
			dltlt.setFooter();
			dltlt.write();
			logger.info("f: "+f.getAbsolutePath());
*/		}
		catch (OfxAuthoringException e) {logger.error("Something went wrong during ofx/latex transformation ",e);}
		catch (IOException e) {logger.error("Cannot save the file to "+f.getAbsolutePath(),e);}
		System.exit(-1);
	}
	
	public Table toOfx(List<View> lViews, String[] headerKeys)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		
		table.setContent(createContent(lViews,headerKeys));
		
		return table;
	}
	
	private Specification createSpecifications()
	{
		Columns cols = new Columns();
		cols.getColumn().add(OfxColumnFactory.createCol(10));
		cols.getColumn().add(OfxColumnFactory.createCol(20));
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		
		return specification;
	}
	
	private Content createContent(List<View> lViews, String[] headerKeys)
	{	
		Head head = new Head();
		head.getRow().add(createHeaderRow(headerKeys));
		
		Body body = new Body();
		for(View view : lViews)
		{
			body.getRow().add(createRow(view));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(View view)
	{
		String code,description;
		
		try
		{
			Lang l = StatusXpath.getLang(view.getLangs(), lang);
			code = l.getTranslation();
		}
		catch (ExlpXpathNotFoundException e){code = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){code = e.getMessage();}
		
		try
		{
			Description d = StatusXpath.getDescription(view.getDescriptions(), lang);
			description = d.getValue();
		}
		catch (ExlpXpathNotFoundException e){description = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){description = e.getMessage();}		
		
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell(code));
		row.getCell().add(OfxCellFactory.createParagraphCell(description));
		return row;
	}	
}