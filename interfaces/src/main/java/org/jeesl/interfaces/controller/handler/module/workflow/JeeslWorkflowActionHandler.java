package org.jeesl.interfaces.controller.handler.module.workflow;

import java.util.List;

import org.jeesl.exception.JeeslWorkflowException;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.exception.processing.UtilsProcessingException;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowDocument;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWorkflowActionHandler<WPD extends JeeslWorkflowDocument<?,?,?>,
											WT extends JeeslWorkflowTransition<?,?,WPD,?,?,?,?>,
											WA extends JeeslWorkflowAction<?,AB,AO,RE,RA>,
											AB extends JeeslWorkflowBot<AB,?,?,?>,
											AO extends EjbWithId,
											RE extends JeeslRevisionEntity<?,?,?,?,RA,?>,
											RA extends JeeslRevisionAttribute<?,?,RE,?,?>,
											WF extends JeeslWorkflow<?,?,?,?>,
											WCS extends JeeslConstraint<?,?,?,?,?,?,?,?>>
{
	boolean checkVeto(JeeslWithWorkflow<?> entity, WT transition);
	void workflowPreconditions(List<WCS> constraints, JeeslWithWorkflow<?> entity, WA action) throws JeeslNotFoundException;
	JeeslWithWorkflow<WF> statusUpdated(JeeslWithWorkflow<WF> entity) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException, UtilsProcessingException, JeeslWorkflowException;
	JeeslWithWorkflow<WF> perform(JeeslWithWorkflow<WF> entity, WA action) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException, UtilsProcessingException, JeeslWorkflowException;
}