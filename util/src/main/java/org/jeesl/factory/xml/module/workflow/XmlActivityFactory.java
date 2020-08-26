package org.jeesl.factory.xml.module.workflow;

import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.model.xml.module.workflow.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlActivityFactory<L extends JeeslLang, D extends JeeslDescription,
								WX extends JeeslWorkflowContext<L,D,WX,?>,
								WP extends JeeslWorkflowProcess<L,D,WX,WS>,
								WS extends JeeslWorkflowStage<L,D,WP,WST,?,WT,?>,
								WST extends JeeslWorkflowStageType<L,D,WST,?>,
								WT extends JeeslWorkflowTransition<L,D,?,WS,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlActivityFactory.class);
	
	
	@SuppressWarnings("unused") private final String localeCode;
	@SuppressWarnings("unused") private final Activity q;
	
	public XmlActivityFactory(String localeCode, Activity q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public static Activity build(){return new Activity();}
	
	
}