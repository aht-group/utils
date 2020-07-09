package org.jeesl.factory.xml.module.workflow;

import org.jeesl.model.xml.module.workflow.Processes;
import org.jeesl.model.xml.module.workflow.Stage;
import org.jeesl.model.xml.module.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlWorkflowFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlWorkflowFactory.class);
	
	public static Workflow build(){return new Workflow();}
	public static Workflow build(Processes processes) {Workflow xml = build(); xml.setProcesses(processes); return xml;}
	public static Workflow build(Stage stage) {Workflow xml = build(); xml.setStage(stage); return xml;}
}