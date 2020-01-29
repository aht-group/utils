package org.jeesl.interfaces.controller.handler.module.workflow;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;

public interface JeeslWorkflowActionCallback<WA extends JeeslWorkflowAction<?,?,?,?,?>>
{
	void workflowAbort(JeeslWithWorkflow<?> entity) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException;
	void workflowCallback(JeeslWithWorkflow<?> entity) throws JeeslConstraintViolationException, JeeslLockingException;
}