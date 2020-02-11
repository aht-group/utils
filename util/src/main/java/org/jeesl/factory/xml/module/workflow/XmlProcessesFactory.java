package org.jeesl.factory.xml.module.workflow;

import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.model.xml.module.workflow.Processes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlProcessesFactory<L extends JeeslLang, D extends JeeslDescription,
								WX extends JeeslWorkflowContext<L,D,WX,?>,
								WP extends JeeslWorkflowProcess<L,D,WX,WS>,
								WS extends JeeslWorkflowStage<L,D,WP,WST,?,WT,?>,
								WST extends JeeslWorkflowStageType<L,D,WST,?>,
								WT extends JeeslWorkflowTransition<L,D,WS,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlProcessesFactory.class);
	
	
	@SuppressWarnings("unused") private final String localeCode;
	@SuppressWarnings("unused") private final Processes q;
	
	public XmlProcessesFactory(String localeCode, Processes q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public static Processes build(){return new Processes();}
}