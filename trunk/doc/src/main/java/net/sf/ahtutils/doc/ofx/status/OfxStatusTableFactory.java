package net.sf.ahtutils.doc.ofx.status;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.layout.Layout;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Column;
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
import org.openfuxml.factory.xml.ofx.layout.XmlLayoutFactory;
import org.openfuxml.factory.xml.ofx.layout.XmlLineFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxStatusTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxStatusTableFactory.class);
	
	public static enum Code {parent,icon,code,name,description}
	
	public static final String translationKeyName = "auStatusTableName";
	public static final String translationKeyDescription = "auStatusTableDescription";
	
	private static String keyCaption = "auTableStatusCaption";
	
	
	private int[] colWidths;
	private boolean customColWidths;
	private int[] colWidths3 = {10,20,30};
	private int[] colWidths4 = {10,20,30};
	
	private String imagePathPrefix;
	public String getImagePathPrefix() {return imagePathPrefix;}
	public void setImagePathPrefix(String imagePathPrefix) {this.imagePathPrefix = imagePathPrefix;}
	
	private Map<Code,Boolean> renderColumn;
	private Map<Code,Column> columnDefinition;
	private Map<Code,String> headers;
	
	private Aht xmlP;

	public OfxStatusTableFactory(Configuration config, String lang, Translations translations)
	{
		super(config,lang,translations);
		imagePathPrefix = config.getString("doc.ofx.imagePathPrefix");
		customColWidths=false;
		
		columnDefinition = new Hashtable<Code,Column>();
		renderColumn = new Hashtable<Code,Boolean>();
		renderColumn.put(Code.parent, false);
		renderColumn.put(Code.icon, false);
		renderColumn.put(Code.code, false);
		renderColumn.put(Code.name, true);
		renderColumn.put(Code.description, true);
		
		headers = new Hashtable<Code,String>();
		headers.put(Code.parent, "auStatusTableParent");
		headers.put(Code.code, "auStatusTableCode");
		headers.put(Code.name, translationKeyName);
		headers.put(Code.description, translationKeyDescription);
	}
	
	public void activateParents(Aht parents)
	{
		renderColumn.put(Code.parent, true);
		xmlP = parents;
	}
	
	public Table buildLatexTable(String id, Aht xmlStatus) throws OfxAuthoringException, UtilsConfigurationException
	{
		try
		{	
			String captionKey = id;
			
			while(captionKey.indexOf(".")>0)
			{
				int index = captionKey.indexOf(".");
				captionKey = captionKey.substring(0,index)+captionKey.substring(index+1, index+2).toUpperCase()+captionKey.substring(index+2, captionKey.length());
			}
			captionKey = captionKey.substring(0, 1).toUpperCase()+captionKey.substring(1, captionKey.length());
			
			if(renderColumn.get(Code.parent))
			{
				headers.put(Code.parent, headers.get(Code.parent)+""+captionKey);
			}
			
			captionKey = keyCaption+captionKey;
			id = "table.status."+id;
			
			Comment comment = XmlCommentFactory.build();
			DocumentationCommentBuilder.fixedId(comment, id);
			DocumentationCommentBuilder.translationKeys(comment,config,UtilsDocumentation.keyTranslationFile);
			DocumentationCommentBuilder.tableHeaders(comment,headers);
			DocumentationCommentBuilder.tableKey(comment,captionKey,"Table Caption");
			DocumentationCommentBuilder.doNotModify(comment);
			
			Table table = toOfx(xmlStatus);
			table.setId(id);
			table.setComment(comment);
			
			Lang lCaption = StatusXpath.getLang(translations, captionKey, lang);
			table.setTitle(XmlTitleFactory.build(lCaption.getTranslation()));
			
			return table;
		}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	public Table toOfx(Aht xmlStatus) throws UtilsConfigurationException
	{
		Table table = null;
		try
		{
			table = new Table();
			table.setSpecification(createSpecifications());
			table.setContent(createContent(xmlStatus));
			table.getContent().setHead(buildHead());
		}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		
		return table;
	}
	
	private Head buildHead()
	{
		Row row = new Row();
		
		for(Code code : Code.values())
		{
			logger.trace(code+ " "+renderColumn.get(code));
			if(renderColumn.get(code))
			{
				if(headers.containsKey(code))
				{
					try
					{
						row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getLang(translations, headers.get(code), lang).getTranslation()));
					}
					catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
					catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
				}
				else
				{
					row.getCell().add(OfxCellFactory.build());
				}
			}
		}
		
		Head head = new Head();
		head.getRow().add(row);
		return head;
	}
	
	private Content createContent(Aht xmlStatus) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException, UtilsConfigurationException
	{
		Body body = new Body();
		
		String previousParentString = "-1";
		boolean firstRow = true;
		for(Status status : xmlStatus.getStatus())
		{
			String parentString = null;
			if(renderColumn.get(Code.parent))
			{
				if(!status.isSetParent()){throw new UtilsConfigurationException("A parent is exprected for the status:"+status.getCode());}
				Status parent = StatusXpath.getStatus(xmlP.getStatus(), status.getParent().getCode());
				parentString = StatusXpath.getLang(parent.getLangs(), lang).getTranslation();
				if(parentString.equals(previousParentString)){parentString="";}
				else{previousParentString=parentString;}
			}
			boolean line = !firstRow && renderColumn.get(Code.parent) && parentString.length()>0;
			body.getRow().add(createRow(status,parentString,line));
			
			firstRow=false;
		}
		
		Content content = new Content();
		content.getBody().add(body);
		
		return content;
	}
	
	private Row createRow(Status status, String parentString, boolean line) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{		
		Row row = new Row();
		
		if(line)
		{
			Layout layout = XmlLayoutFactory.build();
			layout.getLine().add(XmlLineFactory.buildTop());
			row.setLayout(layout);
		}
		
		if(parentString!=null)
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(parentString));
		}
		
//		row.getCell().add(OfxCellFactory.createParagraphCell(status.getCode()));
		
		if(renderColumn.get(Code.icon))
		{
			row.getCell().add(OfxCellFactory.image(OfxStatusImageFactory.build(imagePathPrefix,status)));
		}
		
		row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getLang(status.getLangs(), lang).getTranslation()));
		row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getDescription(status.getDescriptions(), lang).getValue()));
		
		return row;
	}
	
	private Specification createSpecifications() throws UtilsConfigurationException
	{
		logger.trace("customColWidths: "+customColWidths);
		if(!customColWidths)
		{
			if(renderColumn.get(Code.parent)){colWidths=colWidths4;}
			else{colWidths=colWidths3;}
		}
		logger.debug("colums.length: "+colWidths.length);
		if(renderColumn.get(Code.parent) && colWidths.length!=4){throw new UtilsConfigurationException("Need 4 column widths");}
		
		int flexWidth=0;
		Columns cols = new Columns();
		
		if(renderColumn.get(Code.parent))
		{
			int w=20;flexWidth=flexWidth+w;
			cols.getColumn().add(OfxColumnFactory.flex(w));
		}
		
		if(renderColumn.get(Code.icon))
		{
			cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.center));
		}
		
		if(renderColumn.get(Code.code))
		{
			cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
		}
		
		Code code = Code.name;
		if(renderColumn.get(code))
		{
			if(columnDefinition.containsKey(code))
			{
				cols.getColumn().add(columnDefinition.get(code));
			}
			else
			{
				int w=30;flexWidth=flexWidth+w;
				Column c = OfxColumnFactory.flex(w);
				c.getWidth().setNarrow(true);
				cols.getColumn().add(c);
			}		
		}
		
		if(renderColumn.get(Code.description))
		{
			cols.getColumn().add(OfxColumnFactory.flex(100-flexWidth));
		}
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		return specification;
	}
	
	public void setColWidths(int[] colWidths)
	{
		customColWidths=true;
		this.colWidths = colWidths;
	}
	
	public void renderColumn(Code code, boolean render){renderColumn(code,render,null);}
	public void renderColumn(Code code, boolean render,Column definition)
	{
		renderColumn.put(code, render);
		if(definition!=null){columnDefinition.put(code, definition);}
	}
}