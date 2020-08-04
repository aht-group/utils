package org.jeesl.factory.ejb.module.workflow;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbWorkflowStageFactory<P extends JeeslWorkflowProcess<?,?,?,WS>,
										WS extends JeeslWorkflowStage<?,?,P,?,?,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbWorkflowStageFactory.class);
	
	final Class<WS> cStage;
    
	public EjbWorkflowStageFactory(final Class<WS> cStage)
	{       
        this.cStage = cStage;
	}
	    
	public WS build(P process, List<WS> list)
	{
		WS ejb = null;
		try
		{
			ejb = cStage.newInstance();
			ejb.setProcess(process);
			EjbPositionFactory.next(ejb,list);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}