package org.jeesl.factory.txt.module.workflow;

import org.jeesl.interfaces.model.module.workflow.msg.JeeslWorkflowStageNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtWorkflowNotificationFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtWorkflowNotificationFactory.class);
	
	public static <WSN extends JeeslWorkflowStageNotification<?,?,?,?,?>>  String codes(WSN notification)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(notification.getStage().getProcess().getCode()+" "+notification.getStage().getCode());
		return sb.toString();
	}
}
