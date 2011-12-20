package net.sf.ahtutils.controller.factory.ofx.security;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.StringIO;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.content.ofx.table.Columns;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Specification;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.table.OfxCellFactory;
import org.openfuxml.factory.table.OfxColumnFactory;
import org.openfuxml.renderer.processor.latex.content.table.LatexGridTableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxViewTableFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxViewTableFactory.class);
		
	private String lang;
	
	public OfxViewTableFactory(String lang)
	{
		this.lang=lang;
	}
	
	public void saveDescription(File f, List<View> lViews)
	{
		try
		{
			logger.debug("Saving Reference to "+f);
			LatexGridTableFactory renderer = new LatexGridTableFactory();
			renderer.render(toOfx(lViews));
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(f, actual.toString());
			
		}
		catch (OfxAuthoringException e) {logger.error("Something went wrong during ofx/latex transformation ",e);}
		catch (IOException e) {logger.error("Cannot save the file to "+f.getAbsolutePath(),e);}
	}
	
	public Table toOfx(List<View> lViews)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		table.setContent(createContent(lViews));
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
	
	private Content createContent(List<View> lViews)
	{
		Body body = new Body();
		for(View view : lViews)
		{
			try {body.getRow().add(createRow(view));}
			catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
			catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		}
		
		Content content = new Content();
		content.getBody().add(body);
		return content;
	}
	
	private Row createRow(View view) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Lang l = StatusXpath.getLang(view.getLangs(), lang);
		Description d = StatusXpath.getDescription(view.getDescriptions(), lang);
		
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell(l.getTranslation()));
		row.getCell().add(OfxCellFactory.createParagraphCell(d.getValue()));
		return row;
	}
	
	
	
}