package net.sf.ahtutils.doc.ofx.qa;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.status.OfxStatusImageFactory;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Test;
import net.sf.ahtutils.xml.status.Lang;
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
import org.openfuxml.factory.table.OfxCellFactory;
import org.openfuxml.factory.table.OfxColumnFactory;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxQaAgreementTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxQaAgreementTableFactory.class);
	private static String keyCaptionPrefix = "auTableQmAgreement";
	
	private List<String> headerKeys;
	
	private String imagePathPrefix;
	public String getImagePathPrefix() {return imagePathPrefix;}
	public void setImagePathPrefix(String imagePathPrefix) {this.imagePathPrefix = imagePathPrefix;}
	
	private Aht testStatus;
	
	public OfxQaAgreementTableFactory(Configuration config, String lang, Translations translations)
	{
		super(config,lang,translations);
		
		headerKeys = new ArrayList<String>();
		headerKeys.add("auTableQaTestCode");
		headerKeys.add("auTableQaTestName");
		headerKeys.add("");
		headerKeys.add("");
	}
	
	public Table build(Category category,Aht testStatus) throws OfxAuthoringException
	{
		this.testStatus=testStatus;
		try
		{	
			Table table = toOfx(category.getTest());
			table.setId("table.qa.agreements."+category.getCode());
			
			Lang lCaption = StatusXpath.getLang(translations, "auTableQmAgreement", lang);
			table.setTitle(XmlTitleFactory.build(lCaption.getTranslation()+": "+category.getName()+" ("+category.getCode()+")"));
			
			Comment comment = XmlCommentFactory.build();
			DocumentationCommentBuilder.fixedId(comment, table.getId());
			DocumentationCommentBuilder.translationKeys(comment,config,UtilsDocumentation.keyTranslationFile);
			DocumentationCommentBuilder.tableHeaders(comment,headerKeys);
			DocumentationCommentBuilder.tableKey(comment,keyCaptionPrefix,"Table Caption Prefix");
			DocumentationCommentBuilder.doNotModify(comment);
			table.setComment(comment);
			
			return table;
		}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	public Table toOfx(List<Test> lTest)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		table.setContent(createContent(lTest));
		return table;
	}
	
	private Specification createSpecifications()
	{
		Columns cols = new Columns();
		cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
		cols.getColumn().add(OfxColumnFactory.flex(80));
		cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.center));
		cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.center));
			
		Specification specification = new Specification();
		specification.setColumns(cols);
		specification.setFloat(XmlFloatFactory.build(false));
		
		return specification;
	}
	
	private Content createContent(List<Test> lRole)
	{
		Head head = new Head();
		head.getRow().add(createHeaderRow(headerKeys));
		
		Body body = new Body();
		for(Test staff : lRole)
		{
			body.getRow().add(createRow(staff));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(Test staff)
	{
		Row row = new Row();
		
		
		try
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(staff.getCode()));
			row.getCell().add(OfxCellFactory.createParagraphCell(staff.getName()));
			row.getCell().add(OfxCellFactory.image(OfxStatusImageFactory.build(imagePathPrefix,StatusXpath.getStatus(testStatus.getStatus(), staff.getStatus().getCode()))));
			row.getCell().add(OfxCellFactory.image(OfxStatusImageFactory.build(imagePathPrefix,StatusXpath.getStatus(testStatus.getStatus(), staff.getStatement().getCode()))));
		}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		
		return row;
	}	
}