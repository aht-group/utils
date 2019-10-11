package org.jeesl.doc.word;

import java.util.HashMap;
import java.util.List;

import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ImportFormatMode;
import com.aspose.words.Row;
import com.aspose.words.RowCollection;
import com.aspose.words.Table;

import net.sf.ahtutils.xml.status.Status;

public class StatusWordRenderer extends AbstractEntityWordRenderer
{

    final static Logger logger = LoggerFactory.getLogger(StatusWordRenderer.class);

    private final Document statusDoc,templateDoc;
    private final  Entity entity;
    private HashMap<String,Container> statusContainer;
    private List<Attribute> attrbsStatusTable;

    public StatusWordRenderer(Document templateDoc,HashMap<String, Container> statusContainer, List<Attribute> attrbsStatusTable, Entity entity) throws Exception
    {
        this.statusDoc=new Document(); 
        this.templateDoc=templateDoc;

        this.attrbsStatusTable=attrbsStatusTable;
        this.statusContainer= new HashMap<String,Container>();
        this.statusContainer=statusContainer;
        this.entity=entity;
    }

    public Document render()
    {
        DocumentBuilder builder = new DocumentBuilder(statusDoc);

        for (Attribute a : attrbsStatusTable)
        { 
            Document doc = this.templateDoc;
            DocumentBuilder statusBuilder = new DocumentBuilder(doc);

            statusBuilder.moveToCell(0, 1, 1, 0);
            statusBuilder.getCurrentParagraph().getRuns().clear();
            statusBuilder.write(entity.getLangs().getLang().get(0).getTranslation());

            statusBuilder.moveToCell(0, 2, 1, 0);
            statusBuilder.getCurrentParagraph().getRuns().clear();
            statusBuilder.write(a.getCode());

            Table table = doc.getSections().get(0).getBody().getTables().get(0);
            RowCollection templateRows = table.getRows();

            int rowHelper = 0;            
            for (String s : statusContainer.keySet()) 
            {
                if (s.equals(a.getCode())&& a.getCode()!="")
                {
                    for (Status status : statusContainer.get(s).getStatus())
                    {
                        table.getRows().add((Row)templateRows.get(4).deepClone(true));     
                        statusBuilder.moveToCell(0,4+rowHelper,0,0);
                        statusBuilder.getCurrentParagraph().getRuns().clear();

                        if (status.isSetGraphic()&&status.getGraphic().getType().getCode().equals("svg")&&status.getGraphic().getFile().getData().getValue()!=null)
                        {
                            statusBuilder.write(" ");
                            renderStatusSvg(statusBuilder,(byte[])status.getGraphic().getFile().getData().getValue());
                            statusBuilder.write(" ");
                        }                               
                        else {statusBuilder.write("   ");}

                        statusBuilder.writeln(status.getCode() + " = " + status.getLangs().getLang().get(0).getTranslation());

                        statusBuilder.moveToCell(0,4+rowHelper,1,0);
                        statusBuilder.getCurrentParagraph().getRuns().clear();
                        
                        if (status.isSetDescriptions()&&status.getDescriptions().getDescription().get(0).getValue()!="")
                        { 
                            statusBuilder.write(status.getDescriptions().getDescription().get(0).getValue());
                        }
                        rowHelper++;
                    }
                }
            }
            builder.moveToDocumentEnd();
            builder.insertDocument(doc,ImportFormatMode.KEEP_SOURCE_FORMATTING);
        } 
        return  this.statusDoc;
    }
}