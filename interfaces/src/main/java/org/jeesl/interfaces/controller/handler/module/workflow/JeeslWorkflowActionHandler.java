package org.jeesl.interfaces.controller.handler.module.workflow;

import java.util.List;

import org.jeesl.exception.JeeslWorkflowException;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflowActionHandler<WT extends JeeslWorkflowTransition<?,?,?,?,?,?>,
											WA extends JeeslWorkflowAction<?,AB,AO,RE,RA>,
											AB extends JeeslWorkflowBot<AB,?,?,?>,
											AO extends EjbWithId,
											RE extends JeeslRevisionEntity<?,?,?,?,RA,?>,
											RA extends JeeslRevisionAttribute<?,?,RE,?,?>,
											AW extends JeeslWorkflow<?,?,?>,
											WCS extends JeeslConstraint<?,?,?,?,?,?,?,?>>
{
	boolean checkVeto(JeeslWithWorkflow<?> entity, WT transition);
	void workflowPreconditions(List<WCS> constraints, JeeslWithWorkflow<?> entity, WA action) throws JeeslNotFoundException;
	JeeslWithWorkflow<AW> statusUpdated(JeeslWithWorkflow<AW> entity) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException, UtilsProcessingException, JeeslWorkflowException;
	JeeslWithWorkflow<AW> perform(JeeslWithWorkflow<AW> entity, WA action) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException, UtilsProcessingException, JeeslWorkflowException;
}