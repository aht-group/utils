package org.jeesl.interfaces.controller.handler.module.workflow;

import java.util.List;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;

public interface JeeslWorkflowResponsibleHandler<WF extends JeeslWorkflow<?,?,?,USER>,
												 USER extends JeeslUser<?>>
{
	List<USER> findResponsibles(WF workflow, JeeslWithWorkflow<WF> entity);
}