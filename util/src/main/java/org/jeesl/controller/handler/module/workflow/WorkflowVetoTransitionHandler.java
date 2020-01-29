package org.jeesl.controller.handler.module.workflow;

import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkflowVetoTransitionHandler<WS extends JeeslWorkflowStage<?,?,?,?,?,WT,?>,
											WT extends JeeslWorkflowTransition<?,?,WS,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(WorkflowVetoTransitionHandler.class);
	
	
	public WorkflowVetoTransitionHandler()
	{
		
	}
	
	public <SRC extends Enum<SRC>, DST extends Enum<DST>>boolean isPath(SRC src, DST dst, WT transition)
	{
		boolean isSrc = transition.getSource().getCode().equals(src.toString());
		boolean isDst = transition.getDestination().getCode().equals(dst.toString());
		return isSrc && isDst;
	}
	
	public <SRC extends Enum<SRC>, DST extends Enum<DST>>boolean onlyAllowed(SRC src, DST dst, WT transition)
	{
		if(transition.getSource().getCode().equals(src.toString()))
		{
			if(!transition.getDestination().getCode().equals(dst.toString())) {return true;}
		}
		return false;
	}
}