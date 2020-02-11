package net.sf.ahtutils.doc.ofx.qa.table;

import java.util.List;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.security.Category;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.configuration.Configuration;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.util.query.xpath.StatusXpath;
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
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.XmlCellFactory;
import org.openfuxml.factory.xml.table.XmlColumnFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxQaRoleTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxQaRoleTableFactory.class);
	private static String keyCaption = "auTableQmRoles";
	
	public OfxQaRoleTableFactory(Configuration config, String lang, Translations translations)
	{
		this(config,new String[] {lang},translations);
	}
	public OfxQaRoleTableFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
	}
	
	public Table build(Category category, List<String> headerKeys) throws OfxAuthoringException, UtilsConfigurationException
	{
		try
		{
			Table table = toOfx(category.getRoles().getRole(),headerKeys);
			table.setId("table.qa.roles");
			
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			table.setTitle(XmlTitleFactory.build(StatusXpath.getLang(translations, keyCaption, langs[0]).getTranslation()));
			
			Comment comment = XmlCommentFactory.build();
			OfxCommentBuilder.fixedId(comment, table.getId());
			DocumentationCommentBuilder.translationKeys(comment,config,UtilsDocumentation.keyTranslationFile);
			DocumentationCommentBuilder.tableHeaders(comment,headerKeys);
			DocumentationCommentBuilder.tableKey(comment,keyCaption,"Table Caption");
			OfxCommentBuilder.doNotModify(comment);
			table.setComment(comment);
			
			return table;
		}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	public Table toOfx(List<Role> roles, List<String> headerKeys)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		
		table.setContent(createContent(roles,headerKeys));
		
		return table;
	}
	
	private Specification createSpecifications()
	{
		Specification specification = new Specification();
		Columns cols = new Columns();
		XmlColumnFactory.add(cols,XmlAlignmentFactory.Horizontal.left);
		cols.getColumn().add(XmlColumnFactory.flex(80));
		specification.setColumns(cols);
		specification.setFloat(XmlFloatFactory.build(false));
		return specification;
	}
	
	private Content createContent(List<Role> roles, List<String> headerKeys)
	{
		Head head = new Head();
		head.getRow().add(createHeaderRow(headerKeys));
		
		Body body = new Body();
		for(Role staff : roles)
		{
			body.getRow().add(createRow(staff));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(Role staff)
	{
		String roleName;
		String roleDesc = "";
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			roleName = StatusXpath.getLang(staff.getLangs(), langs[0]).getTranslation();
			roleDesc = StatusXpath.getDescription(staff.getDescriptions(), langs[0]).getValue();
		}
		catch (ExlpXpathNotFoundException e){roleName = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){roleName = e.getMessage();}

		Row row = new Row();
		row.getCell().add(XmlCellFactory.createParagraphCell(roleName));
		row.getCell().add(XmlCellFactory.createParagraphCell(roleDesc));
		
		return row;
	}	
}