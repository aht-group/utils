package org.jeesl.factory.ejb.module.workflow;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbWorkflowFactory<AP extends JeeslWorkflowProcess<?,?,?,WS>,
								WS extends JeeslWorkflowStage<?,?,AP,?,?,?,?>,
								WF extends JeeslWorkflow<AP,WS,?,?>

>
{
	final static Logger logger = LoggerFactory.getLogger(EjbWorkflowFactory.class);
	
	final Class<WF> cWorkflow;
    
	public EjbWorkflowFactory(final Class<WF> cWorkflow)
	{       
        this.cWorkflow = cWorkflow;
	}
	    
	public WF build(AP process)
	{
		WF ejb = null;
		try
		{
			ejb = cWorkflow.newInstance();
			ejb.setProcess(process);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}