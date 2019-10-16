package org.jeesl.doc.word;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entities;
import org.jeesl.model.xml.system.revision.Entity;
import org.jeesl.util.query.xpath.RevisionXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.ImportFormatMode;
import com.aspose.words.Paragraph;
import com.aspose.words.RowCollection;
import com.aspose.words.Style;
import com.aspose.words.StyleCollection;
import com.aspose.words.StyleType;
import com.aspose.words.Table;
import com.aspose.words.Underline;

import net.sf.ahtutils.xml.status.Status;

public class EntityWordRenderer extends AbstractEntityWordRenderer
{
	final static Logger logger = LoggerFactory.getLogger(EntityWordRenderer.class);
	
	private final Document entityDoc,template,templateTable,templateTable2,tableDoc;
	private final Container categories;
	private final Container relationTypes;
	private final Entities entities;
    private HashMap<String,Container> statusContainer;
	
	public EntityWordRenderer(Document templateDoc,Document templateTableDoc, Entities entities, Container categories, Container relationTypes) throws Exception
	{
		this.entityDoc=templateDoc;	
		this.template=templateDoc;
		this.templateTable=templateTableDoc;
		this.templateTable2=templateTableDoc;
	    this.tableDoc= new Document();
		this.categories=categories;
		this.relationTypes=relationTypes;
		this.entities=entities;
		this.statusContainer=new HashMap<String,Container>();
	}

    public Document render(Entity entity, String savingDirectory, boolean renderStatus) throws Exception 
	{
		DocumentBuilder docBuilder = new DocumentBuilder(entityDoc);						
		Map<String, String> replacementTags = new HashMap<String, String>();	
		List<String> keys= new ArrayList<>();
		
//		StyleCollection tableStyles = entityDoc.getStyles().getByStyleIdentifier(StyleType.TABLE).getStyles();
//		for (Style s : tableStyles) {logger.info(s.getName());}
		
		keys.add("_ENTITY_");replacementTags.put("_ENTITY_", entity.getLangs().getLang().get(0).getTranslation());
		keys.add("_CATEGORY_");replacementTags.put("_CATEGORY_", categoryForCode(categories, entity.getCategory().getCode()));
		keys.add("_CLASS1_");replacementTags.put("_CLASS1_",	packageShrinker(entity.getCode().replace(FilenameUtils.getExtension(entity.getCode()), ""), 5));
		keys.add("_CLASS2_");replacementTags.put("_CLASS2_", FilenameUtils.getExtension(entity.getCode()));
		keys.add("_DESCRIPTION_");replacementTags.put("_DESCRIPTION_", entity.getDescriptions().getDescription().get(0).getValue().toString());
			
		for (String s : keys)
		{
		    docBuilder.moveToDocumentStart();
		    if (s.equals("_CLASS2_")) {docBuilder.getFont().setBold(true);}
		    try{entityDoc.getRange().replace(s, replacementTags.get(s).toString(),new FindReplaceOptions(FindReplaceDirection.FORWARD));}
		    catch (Exception e1) {e1.printStackTrace();}
		    if (s.equals("_CLASS2_")) {docBuilder.getFont().setBold(false);};
		}
		
		List<Attribute> attrbs = new ArrayList<Attribute>();
		for (Attribute a:entity.getAttribute()){if(a.isBean()){attrbs.add(a);}}	
		
		boolean makerStatusTable = false;
		List<Attribute> attrbsStatusTable = new ArrayList<Attribute>();
		
		
		Table templateTable = template.getSections().get(0).getBody().getTables().get(0);
	    RowCollection templateRows = templateTable.getRows();
		
		Table table = entityDoc.getSections().get(0).getBody().getTables().get(0);
		RowCollection rows = table.getRows();
		 
		int rowHelper = 0; 
		for (int i=0;i<=attrbs.size()-1;i++) 
		{							
			Attribute a = attrbs.get(i);				
			int cellHelperRow5=0;
			for (Cell c : rows.get(5+rowHelper).getCells())
			{
				c.getLastParagraph().getRuns().clear();
				docBuilder.moveTo(c.getFirstParagraph());				
				if (cellHelperRow5==0){c.getParagraphs().get(0).getRuns().clear();docBuilder.write(a.getCode());}
				if (cellHelperRow5==1) 
				{
				    if (a.getRelation()!=null && a.getRelation().isSetEntity()){docBuilder.write(relationTypeForCode(relationTypes, a.getRelation().getType().getCode()));}
				    else if (a.getRelation()==null || !a.getRelation().isSetEntity())
				    {
				        if (a.getType().getCode().equals("bool")) {docBuilder.write("boolean");}
				        if (a.getType().getCode().equals("text")) {docBuilder.write("string");}
                        if (a.getType().getCode().equals("numberLong")) {docBuilder.write("long");}
                        if (a.getType().getCode().equals("date")) {docBuilder.write("date");}
                        if (a.getType().getCode().equals("numberInteger")) {docBuilder.write("integer");}
                        if (a.getType().getCode().equals("long")) {docBuilder.write("long");}
                        if (a.getType().getCode().equals("numberDouble")) {docBuilder.write("double");}
                        if (a.getType().getCode().equals("numberDoubleAmount")) {docBuilder.write("double");}
                    }
				}	
				if (cellHelperRow5 == 2 && a.getDescriptions().getDescription().get(0).getValue().toString() != "")
				{
                    logger.info("paragraphs count: " + c.getParagraphs().getCount());
				    docBuilder.write(a.getDescriptions().getDescription().get(0).getValue().trim());
				    if (a.getRelation()!=null && a.getRelation().isSetEntity())
				    {		
				        docBuilder.writeln();
				        Entity e = RevisionXpath.getEntity(entities, a.getRelation().getEntity().getCode());
				        docBuilder.getFont().setColor(Color.gray);docBuilder.getFont().setItalic(true);
				        docBuilder.write("("+relationTypeForCode(relationTypes, a.getRelation().getType().getCode())+" to "+e.getLangs().getLang().get(0).getTranslation()+")");
				        docBuilder.getFont().setColor(Color.black);docBuilder.getFont().setItalic(false);
				    }	
				    
				    if (renderStatus) 
				    {
				        if (a.getRelation()!=null && a.getRelation().isSetDocOptionsTable()){makerStatusTable=true;attrbsStatusTable.add(a);}

				        if (a.getRelation() !=null && a.getRelation().isSetDocOptionsInline())  
				        {
				            for (String s : statusContainer.keySet()) 
				            {
				                if (s.equals(a.getCode()))
				                {
				                    docBuilder.getFont().setUnderline(Underline.SINGLE);
				                    docBuilder.writeln("Status-Table:");
				                    docBuilder.getFont().setUnderline(Underline.NONE);
				                    for (Status status : statusContainer.get(s).getStatus())
				                    {
				                        if (status.isSetGraphic()&&status.getGraphic().getType().getCode().equals("svg")&&status.getGraphic().getFile().getData().getValue()!=null)
				                        {
				                            docBuilder.write(" ");
				                            renderStatusSvg(docBuilder,(byte[])status.getGraphic().getFile().getData().getValue());
				                            docBuilder.write(" ");
				                        }				                
				                        else {docBuilder.write("   "); }
				                        docBuilder.writeln(status.getCode()+" = "+status.getLangs().getLang().get(0).getTranslation());
				                        if (status.isSetDescriptions() && status.getDescriptions().getDescription().get(0).getValue()!="")
				                        {
				                            docBuilder.getFont().setColor(Color.gray);docBuilder.getFont().setItalic(true);docBuilder.getFont().setSize(7);
				                            docBuilder.write(" (" + status.getDescriptions().getDescription().get(0).getValue() +")");
				                            docBuilder.getFont().setColor(Color.black);docBuilder.getFont().setItalic(false);docBuilder.getFont().setSize(9);
				                        }
//				                        docBuilder.writeln();
				                    }
				                }
				            }
				        }
				    }
				}
				cellHelperRow5++;
			}
			
			int cellHelperRow6=0;
			for (Cell c : rows.get(6+rowHelper).getCells())
			{
			    c.getParagraphs().get(0).getRuns().clear();;docBuilder.moveTo(c.getFirstParagraph());
				if (cellHelperRow6==0)
				{
				    docBuilder.write(a.getLangs().getLang().get(0).getTranslation().toString());
				}
				cellHelperRow6++;
			}
			table.appendChild(templateRows.get(5).deepClone(true));table.appendChild(templateRows.get(6).deepClone(true));	
			rowHelper=rowHelper+2;
		}		
		table.getLastRow().remove();table.getLastRow().remove();
		
		if (renderStatus&&makerStatusTable)
		{
            docBuilder.moveToDocumentEnd();           
            StatusWordRenderer statusRenderer = new StatusWordRenderer(this.templateTable, this.statusContainer, attrbsStatusTable, entity);
		    docBuilder.insertDocument(statusRenderer.render(),ImportFormatMode.KEEP_SOURCE_FORMATTING);
		}
		return entityDoc;
	}

    public Document render(Entity entity, String absolutePath, HashMap<String, Container> statusContainer, boolean renderStatus) throws Exception {this.statusContainer = statusContainer;Document doc = render(entity, absolutePath, renderStatus);return doc;}
    
}