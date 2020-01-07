package org.jeesl.api.facade.module;

import java.util.List;

import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowModificationLevel;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStagePermission;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransitionType;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflowFacade <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
										AX extends JeeslWorkflowContext<L,D,AX,?>,
										WP extends JeeslWorkflowProcess<L,D,AX,WS>,
										WS extends JeeslWorkflowStage<L,D,WP,WST,WSP,WT,?>,
										WST extends JeeslWorkflowStageType<L,D,WST,?>,
										WSP extends JeeslWorkflowStagePermission<WS,APT,WML,SR>,
										APT extends JeeslWorkflowPermissionType<L,D,APT,?>,
										WML extends JeeslWorkflowModificationLevel<?,?,WML,?>,
										WT extends JeeslWorkflowTransition<L,D,WS,WTT,SR,?>,
										WTT extends JeeslWorkflowTransitionType<L,D,WTT,?>,
										AC extends JeeslWorkflowCommunication<WT,MT,MC,SR,RE>,
										AA extends JeeslWorkflowAction<WT,AB,AO,RE,RA>,
										AB extends JeeslWorkflowBot<AB,L,D,?>,
										AO extends EjbWithId,
										MT extends JeeslIoTemplate<L,D,?,?,?,?>,
										MC extends JeeslTemplateChannel<L,D,MC,?>,
										SR extends JeeslSecurityRole<L,D,?,?,?,?,?>,
										RE extends JeeslRevisionEntity<L,D,?,?,RA,?>,
										RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
										WL extends JeeslWorkflowLink<WF,RE>,
										WF extends JeeslWorkflow<WP,WS,WY>,
										WY extends JeeslWorkflowActivity<WT,WF,FRC,USER>,
										FRC extends JeeslFileContainer<?,?>,
										USER extends JeeslUser<SR>>
			extends UtilsFacade
{	
	WT fTransitionBegin(WP process);
	
	WL fWorkflowLink(WF workflow) throws UtilsNotFoundException;
	<W extends JeeslWithWorkflow<WF>> WL fWorkflowLink(WP process, W owner) throws UtilsNotFoundException;
//	<W extends JeeslWithWorkflow<AW>> AW fWorkflow(Class<W> cWith, W with) throws UtilsNotFoundException;
	List<WF> fWorkflows(WP process, List<WS> stages);
}