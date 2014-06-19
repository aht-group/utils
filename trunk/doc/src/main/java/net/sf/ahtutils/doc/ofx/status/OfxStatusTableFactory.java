package net.sf.ahtutils.doc.ofx.status;

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
import org.openfuxml.content.media.Image;
import org.openfuxml.content.media.Media;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Cell;
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
import org.openfuxml.factory.xml.layout.XmlHeightFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.ofx.layout.XmlLayoutFactory;
import org.openfuxml.factory.xml.ofx.layout.XmlLineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxStatusTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxStatusTableFactory.class);
	
	private static String keyCaption = "auTableStatusCaption";
	
	private int[] colWidths;
	private boolean customColWidths;
	private int[] colWidths3 = {10,20,30};
	private int[] colWidths4 = {10,20,30};
	
	private String imagePathPrefix;
	public String getImagePathPrefix() {return imagePathPrefix;}
	public void setImagePathPrefix(String imagePathPrefix) {this.imagePathPrefix = imagePathPrefix;}
	
	private boolean withParent;
	public boolean isWithParent() {return withParent;}
	public void setWithParent(boolean withParent) {this.withParent = withParent;}

	private boolean withCode;
	public boolean isWithCode() {return withCode;}
	public void setWithCode(boolean withCode) {this.withCode = withCode;}
	
	private boolean withIcon;
	public boolean isWithIcon() {return withIcon;}
	public void setWithIcon(boolean withIcon) {this.withIcon = withIcon;}
	
	private boolean withName;
	public boolean isWithName() {return withName;}
	public void setWithName(boolean withName) {this.withName = withName;}
	
	private boolean withDescription;
	public boolean isWithDescription() {return withDescription;}
	public void setWithDescription(boolean withDescription) {this.withDescription = withDescription;}
	
	private Aht xmlP;

	public OfxStatusTableFactory(Configuration config, String lang, Translations translations)
	{
		super(config,lang,translations);
		customColWidths=false;
		
		withParent = false;
		withIcon = false;
		withCode = false;
		withName = true;
		withDescription = true;
	}
	
	public void activateParents(Aht parents)
	{
		withParent = true;
		xmlP = parents;
	}
	
	public Table buildLatexTable(String id, Aht xmlStatus, String[] headerKeys) throws OfxAuthoringException, UtilsConfigurationException
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
			if(withParent){headerKeys[0]=headerKeys[0]+""+captionKey;}
			captionKey = keyCaption+captionKey;
			id = "table.status."+id;
			
			Comment comment = XmlCommentFactory.build();
			DocumentationCommentBuilder.fixedId(comment, id);
			DocumentationCommentBuilder.translationKeys(comment,config,UtilsDocumentation.keyTranslationFile);
			DocumentationCommentBuilder.tableHeaders(comment,headerKeys);
			DocumentationCommentBuilder.tableKey(comment,captionKey,"Table Caption");
			DocumentationCommentBuilder.doNotModify(comment);
			
			Table table = toOfx(xmlStatus,headerKeys);
			table.setId(id);
			table.setComment(comment);
			
			Lang lCaption = StatusXpath.getLang(translations, captionKey, lang);
			table.setTitle(XmlTitleFactory.build(lCaption.getTranslation()));
			
			return table;
		}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	public Table toOfx(Aht xmlStatus, String[] headerKeys) throws UtilsConfigurationException
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		
		try{table.setContent(createContent(xmlStatus, headerKeys));}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		
		return table;
	}
	
	private Content createContent(Aht xmlStatus, String[] headerKeys) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException, UtilsConfigurationException
	{
		Row row = new Row();
		for(String headerKey : headerKeys)
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getLang(translations, headerKey, lang).getTranslation()));
		}
		
		Head head = new Head();
		head.getRow().add(row);
		
		Body body = new Body();
		
		String previousParentString = "-1";
		boolean firstRow = true;
		for(Status status : xmlStatus.getStatus())
		{
			String parentString = null;
			if(withParent)
			{
				if(!status.isSetParent()){throw new UtilsConfigurationException("A parent is exprected for the status:"+status.getCode());}
				Status parent = StatusXpath.getStatus(xmlP.getStatus(), status.getParent().getCode());
				parentString = StatusXpath.getLang(parent.getLangs(), lang).getTranslation();
				if(parentString.equals(previousParentString)){parentString="";}
				else{previousParentString=parentString;}
			}
			boolean line = !firstRow && withParent && parentString.length()>0;
			body.getRow().add(createRow(status,parentString,line));
			
			firstRow=false;
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
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
		
		if(withIcon)
		{
			Cell cell = new Cell();
			cell.getContent().add(buildImage(status));
			row.getCell().add(cell);
		}
		
		row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getLang(status.getLangs(), lang).getTranslation()));
		row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getDescription(status.getDescriptions(), lang).getValue()));
		
		return row;
	}
	
	private Image buildImage(Status status)
	{
		int index = status.getImage().lastIndexOf(".");
		String name = status.getImage().substring(0,index);
		
		StringBuffer sb = new StringBuffer();
		sb.append(imagePathPrefix).append("/");
		sb.append(name);
		sb.append(".svg");
		logger.info(sb.toString());
		
		Media media = new Media();
		media.setSrc(sb.toString());
		media.setDst(name);
		
		Image image = new Image();
		image.setMedia(media);
		image.setHeight(XmlHeightFactory.em(1));
		return image;
	}
	
	private Specification createSpecifications() throws UtilsConfigurationException
	{
		logger.debug("customColWidths: "+customColWidths);
		if(!customColWidths)
		{
			if(withParent){colWidths=colWidths4;}
			else{colWidths=colWidths3;}
		}
		logger.debug("colums.length: "+colWidths.length);
		if(withParent && colWidths.length!=4){throw new UtilsConfigurationException("Need 4 column widths");}
		
		int flexWidth=0;
		Columns cols = new Columns();
		
		if(withParent)
		{
			int w=20;flexWidth=flexWidth+w;
			cols.getColumn().add(OfxColumnFactory.flex(w));
		}
		
		if(withIcon)
		{
			cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.center));
		}
		
		if(withCode)
		{
			cols.getColumn().add(OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
		}
		
		if(withName)
		{
			int w=20;flexWidth=flexWidth+w;
			cols.getColumn().add(OfxColumnFactory.flex(w));
		}
		
		if(withDescription)
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
}