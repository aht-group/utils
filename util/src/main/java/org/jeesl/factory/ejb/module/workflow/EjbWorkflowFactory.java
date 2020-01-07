package org.jeesl.factory.ejb.module.workflow;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbWorkflowFactory<AP extends JeeslWorkflowProcess<?,?,?,WS>,
								WS extends JeeslWorkflowStage<?,?,AP,?,?,?,?>,
								AW extends JeeslWorkflow<AP,WS,?>

>
{
	final static Logger logger = LoggerFactory.getLogger(EjbWorkflowFactory.class);
	
	final Class<AW> cWorkflow;
    
	public EjbWorkflowFactory(final Class<AW> cWorkflow)
	{       
        this.cWorkflow = cWorkflow;
	}
	    
	public AW build(AP process)
	{
		AW ejb = null;
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