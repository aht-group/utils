package org.jeesl.api.facade.module;

import java.util.List;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowLink;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowModificationLevel;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStagePermission;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransitionType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;
import org.jeesl.model.json.db.tuple.two.Json2Tuples;

public interface JeeslWorkflowFacade <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslStatus<LOC,L,D>,
										WX extends JeeslWorkflowContext<L,D,WX,?>,
										WP extends JeeslWorkflowProcess<L,D,WX,WS>,
										WS extends JeeslWorkflowStage<L,D,WP,WST,WSP,WT,?>,
										WST extends JeeslWorkflowStageType<L,D,WST,?>,
										WSP extends JeeslWorkflowStagePermission<WS,WPT,WML,SR>,
										WPT extends JeeslWorkflowPermissionType<L,D,WPT,?>,
										WML extends JeeslWorkflowModificationLevel<L,D,WML,?>,
										WT extends JeeslWorkflowTransition<L,D,WS,WTT,SR,?>,
										WTT extends JeeslWorkflowTransitionType<L,D,WTT,?>,
										AC extends JeeslWorkflowCommunication<WT,MT,MC,SR,RE>,
										WA extends JeeslWorkflowAction<WT,AB,AO,RE,RA>,
										AB extends JeeslWorkflowBot<AB,L,D,?>,
										AO extends EjbWithId,
										MT extends JeeslIoTemplate<L,D,?,?,?,?>,
										MC extends JeeslTemplateChannel<L,D,MC,?>,
										SR extends JeeslSecurityRole<L,D,?,?,?,?,?>,
										RE extends JeeslRevisionEntity<L,D,?,?,RA,?>,
										RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
										WL extends JeeslWorkflowLink<WF,RE>,
										WF extends JeeslWorkflow<WP,WS,WY,USER>,
										WY extends JeeslWorkflowActivity<WT,WF,FRC,USER>,
										FRC extends JeeslFileContainer<?,?>,
										USER extends JeeslUser<SR>>
			extends JeeslFacade
{	
	WT fTransitionBegin(WP process);
	
	WL fWorkflowLink(WF workflow) throws JeeslNotFoundException;
	<W extends JeeslWithWorkflow<WF>> WL fWorkflowLink(WP process, W owner) throws JeeslNotFoundException;
	List<WL> fWorkflowLinks(List<WF> workflows);
	<W extends JeeslWithWorkflow<WF>> List<WL> fWorkflowLinks(WP process, List<W> owners);
	List<WL> fWorkflowRepsonsibleLinks(USER user);
	List<WL> fWorkflowsEscalation(WP process);
	
	WF loadWorkflow(WF workflow);
	List<WF> fWorkflows(WP process, List<WS> stages);
	List<WF> fWorkflows(List<WP> processes, List<WST> types);
	
	Json1Tuples<WP> tpcActivitiesByProcess();
	Json2Tuples<WP,WST> tpcActivitiesByProcessType();
}