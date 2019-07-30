package org.jeesl.factory.xml.module.workflow;

import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.model.xml.module.workflow.Processes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class XmlProcessesFactory<L extends UtilsLang, D extends UtilsDescription,
								WX extends JeeslWorkflowContext<L,D,WX,?>,
								WP extends JeeslWorkflowProcess<L,D,WX>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlProcessesFactory.class);
	
	private final String localeCode;
	private final Processes q;
	
	public XmlProcessesFactory(String localeCode, Processes q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public static Processes build(){return new Processes();}
}