package org.jeesl.factory.ejb.module.workflow;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbWorkflowLinkFactory<RE extends JeeslRevisionEntity<?,?,?,?,?,?>,
									WL extends JeeslWorkflowLink<WF,RE>,
									WF extends JeeslWorkflow<?,?,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbWorkflowLinkFactory.class);
	
	final Class<WL> cLink;
    
	public EjbWorkflowLinkFactory(final Class<WL> cLink)
	{       
        this.cLink = cLink;
	}
	    
	public WL build(RE entity, WF workflow, JeeslWithWorkflow<WF> object)
	{
		WL ejb = null;
		try
		{
			ejb = cLink.newInstance();
			ejb.setEntity(entity);
			ejb.setWorkflow(workflow);
			ejb.setRefId(object.getId());
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}