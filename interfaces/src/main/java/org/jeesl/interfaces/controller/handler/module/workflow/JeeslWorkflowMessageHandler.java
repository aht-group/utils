package org.jeesl.interfaces.controller.handler.module.workflow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.system.io.mail.EmailAddress;
import org.jeesl.model.xml.system.io.mail.Mail;

public interface JeeslWorkflowMessageHandler<WC extends JeeslWorkflowCommunication<?,MT,MC,SR,RE>,
											SR extends JeeslSecurityRole<?,?,?,?,?,?,USER>,
											RE extends JeeslRevisionEntity<?,?,?,?,?,?>,
											MT extends JeeslIoTemplate<?,?,?,?,MD,?>,
											MC extends JeeslTemplateChannel<?,?,MC,?>,
											MD extends JeeslIoTemplateDefinition<?,MC,MT>,
											WF extends JeeslWorkflow<?,?,?>,
											WY extends JeeslWorkflowActivity<?,WF,?,USER>,
											USER extends JeeslUser<SR>>
							extends Serializable
{
	String localeCode(USER user);
	String headerPrefix();
	
	List<USER> getRecipients(JeeslWithWorkflow<WF> workflowOwner, WY activity, WC communication);
	
	EmailAddress senderEmail(WY activity);
	EmailAddress recipientEmail(USER user);
	
	void completeModel(JeeslWithWorkflow<WF> entity, WY activity, WC communication, String localeCode, Map<String,Object> model);
	
	void spool(Mail mail) throws JeeslConstraintViolationException, JeeslNotFoundException;
	List<MD> getDefinitions(MT template);
}