package org.jeesl.factory.ejb.module.workflow;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowDocument;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbWorkflowDocumentFactory<WP extends JeeslWorkflowProcess<?,?,?,?>,
										WPD extends JeeslWorkflowDocument<?,?,WP>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbWorkflowDocumentFactory.class);
	
	final Class<WPD> cDocument;
    
	public EjbWorkflowDocumentFactory(final Class<WPD> cDocument)
	{       
        this.cDocument = cDocument;
	}
	    
	public WPD build(WP process, List<WPD> list)
	{
		WPD ejb = null;
		try
		{
			ejb = cDocument.newInstance();
			ejb.setProcess(process);
			EjbPositionFactory.next(ejb,list);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}