package net.sf.ahtutils.doc.ofx.qa;

import java.util.List;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.qa.Qa;
import net.sf.ahtutils.xml.security.Staff;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxQaTeamTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxQaTeamTableFactory.class);
	private static String keyCaption = "auTableQmTeamCaption";
	
	public OfxQaTeamTableFactory(Configuration config, String lang, Translations translations)
	{
		super(config,lang,translations);
	}
	
	public Table build(Qa qa, List<String> headerKeys) throws OfxAuthoringException
	{
		try
		{
			Table table = toOfx(qa.getStaff(),headerKeys);
			table.setId("table.qa.team");
			table.setTitle(XmlTitleFactory.build(StatusXpath.getLang(translations, keyCaption, lang).getTranslation()));
			
			Comment comment = XmlCommentFactory.build();
			DocumentationCommentBuilder.fixedId(comment, table.getId());
			DocumentationCommentBuilder.translationKeys(comment,config,UtilsDocumentation.keyTranslationFile);
			DocumentationCommentBuilder.tableHeaders(comment,headerKeys);
			DocumentationCommentBuilder.tableKey(comment,keyCaption,"Table Caption Prefix");
			DocumentationCommentBuilder.doNotModify(comment);
			table.setComment(comment);
			
			return table;
		}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	public Table toOfx(List<Staff> lStaff, List<String> headerKeys)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications(headerKeys.size()));
		
		table.setContent(createContent(lStaff,headerKeys));
		
		return table;
	}
	
	private Specification createSpecifications(int columns)
	{
		Columns cols = new Columns();
		if(columns==2)
		{
			cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
			cols.getColumn().add(OfxColumnFactory.flex(60));
		}
		else if(columns==3)
		{
			cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
			cols.getColumn().add(OfxColumnFactory.flex(35));
			cols.getColumn().add(OfxColumnFactory.flex(40));
		}
		else if(columns==4)
		{
			cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
			cols.getColumn().add(OfxColumnFactory.flex(30));
			cols.getColumn().add(OfxColumnFactory.flex(25));
			cols.getColumn().add(OfxColumnFactory.flex(25));
		}
		else {logger.warn("Columns "+columns+" NYI");}
			
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		
		return specification;
	}
	
	private Content createContent(List<Staff> lStaff, List<String> headerKeys)
	{
		Head head = new Head();
		head.getRow().add(createHeaderRow(headerKeys));
		
		Body body = new Body();
		for(Staff staff : lStaff)
		{
			body.getRow().add(createRow(staff));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(Staff staff)
	{
		String roleName;
		
		try
		{
			roleName = StatusXpath.getLang(staff.getRole().getLangs(), lang).getTranslation();
		}
		catch (ExlpXpathNotFoundException e){roleName = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){roleName = e.getMessage();}

		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell(roleName));
		row.getCell().add(OfxCellFactory.createParagraphCell(staff.getUser().getFirstName()+" "+staff.getUser().getLastName()));
		
		if(staff.isSetResponsible()){row.getCell().add(OfxCellFactory.createParagraphCell(staff.getResponsible().getLabel()));}
		else{row.getCell().add(OfxCellFactory.createParagraphCell(""));}
		
		if(staff.isSetType()){row.getCell().add(OfxCellFactory.createParagraphCell(staff.getType().getLabel()));}
		else{row.getCell().add(OfxCellFactory.createParagraphCell(""));}
		
		return row;
	}	
}