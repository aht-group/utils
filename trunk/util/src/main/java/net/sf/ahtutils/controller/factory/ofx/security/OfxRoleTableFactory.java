package net.sf.ahtutils.controller.factory.ofx.security;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import net.sf.ahtutils.xml.access.Role;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
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

public class OfxRoleTableFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxRoleTableFactory.class);
		
	private String lang;
	private Translations translations;
	
	public OfxRoleTableFactory(String lang, Translations translations)
	{
		this.lang=lang;
		this.translations=translations;
	}
	
	public void saveDescription(File f, List<Role> lRoles, String[] headerKeys)
	{
		try
		{
			logger.debug("Saving Reference to "+f);
			LatexGridTableRenderer renderer = new LatexGridTableRenderer();
			renderer.render(toOfx(lRoles,headerKeys));
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(f, actual.toString());
		}
		catch (OfxAuthoringException e) {logger.error("Something went wrong during ofx/latex transformation ",e);}
		catch (IOException e) {logger.error("Cannot save the file to "+f.getAbsolutePath(),e);}
	}
	
	public Table toOfx(List<Role> lRoles, String[] headerKeys)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		
		try{table.setContent(createContent(lRoles,headerKeys));}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		
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
	
	private Content createContent(List<Role> lRoles, String[] headerKeys) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Row row = new Row();
		for(String headerKey : headerKeys)
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getLang(translations, headerKey, lang).getTranslation()));
		}
		
		Head head = new Head();
		head.getRow().add(row);
		
		Body body = new Body();
		for(Role role : lRoles)
		{
			body.getRow().add(createRow(role));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(Role role) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Lang l = StatusXpath.getLang(role.getLangs(), lang);
		Description d = StatusXpath.getDescription(role.getDescriptions(), lang);
		
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell(l.getTranslation()));
		row.getCell().add(OfxCellFactory.createParagraphCell(d.getValue()));
		return row;
	}	
}