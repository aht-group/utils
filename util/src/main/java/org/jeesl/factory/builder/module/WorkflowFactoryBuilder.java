package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowActionFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowActivityFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowCommunicationFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowLinkFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowPermissionFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowProcessFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowStageFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowTransitionFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowFactory;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflow;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class WorkflowFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									WX extends JeeslWorkflowContext<L,D,WX,?>,
									WP extends JeeslWorkflowProcess<L,D,WX,WS>,
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
									AL extends JeeslWorkflowLink<AW,RE>,
									AW extends JeeslWorkflow<WP,WS,WY>,
									WY extends JeeslWorkflowActivity<WT,AW,FRC,USER>,
									FRC extends JeeslFileContainer<?,?>,
									USER extends JeeslUser<SR>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(WorkflowFactoryBuilder.class);
	
	private final Class<WX> cContext; public Class<WX> getClassContext() {return cContext;}
	private final Class<WP> cProcess; public Class<WP> getClassProcess() {return cProcess;}
	private final Class<WS> cStage; public Class<WS> getClassStage() {return cStage;}
	private final Class<WST> cStageType; public Class<WST> getClassStageType() {return cStageType;}
	private final Class<WSP> cPermission; public Class<WSP> getClassPermission() {return cPermission;}
	private final Class<APT> cPermissionType; public Class<APT> getClassPermissionType() {return cPermissionType;}
	private final Class<WML> cModificationLevel; public Class<WML> getClassModificationLevel() {return cModificationLevel;}
	private final Class<WT> cTransition; public Class<WT> getClassTransition() {return cTransition;}
	private final Class<WTT> cTransitionType; public Class<WTT> getClassTransitionType() {return cTransitionType;}
	private final Class<AC> cCommunication; public Class<AC> getClassCommunication() {return cCommunication;}
	private final Class<AA> cAction; public Class<AA> getClassAction() {return cAction;}
	private final Class<AB> cBot; public Class<AB> getClassBot() {return cBot;}
	private final Class<AL> cLink; public Class<AL> getClassLink() {return cLink;}
	private final Class<AW> cWorkflow; public Class<AW> getClassWorkflow() {return cWorkflow;}
	private final Class<WY> cActivity; public Class<WY> getClassActivity() {return cActivity;}
	
	public WorkflowFactoryBuilder(final Class<L> cL, final Class<D> cD,
									final Class<WX> cContext,
									final Class<WP> cProcess,
									final Class<WS> cStage,
									final Class<WST> cStageType,
									final Class<WSP> cPermission,
									final Class<APT> cPermissionType,
									final Class<WML> cModificationLevel,
									final Class<WT> cTransition,
									final Class<WTT> cTransitionType,
									final Class<AC> cCommunication,
									final Class<AA> cAction,
									final Class<AB> cBot,
									final Class<AL> cLink,
									final Class<AW> cWorkflow,
									final Class<WY> cActivity)
	{
		super(cL,cD);
		this.cContext=cContext;
		this.cProcess=cProcess;
		this.cStage=cStage;
		this.cStageType=cStageType;
		this.cPermission=cPermission;
		this.cPermissionType=cPermissionType;
		this.cModificationLevel=cModificationLevel;
		this.cTransition=cTransition;
		this.cTransitionType=cTransitionType;
		this.cCommunication=cCommunication;
		this.cAction=cAction;
		this.cBot=cBot;
		this.cLink=cLink;
		this.cWorkflow=cWorkflow;
		this.cActivity=cActivity;
	}
	
	public EjbWorkflowProcessFactory<WP> ejbProcess() {return new EjbWorkflowProcessFactory<>(cProcess);}
	public EjbWorkflowStageFactory<WP,WS> ejbStage() {return new EjbWorkflowStageFactory<>(cStage);}
	public EjbWorkflowPermissionFactory<WS,WSP,WML,SR> ejbPermission() {return new EjbWorkflowPermissionFactory<>(cPermission);}
	public EjbWorkflowTransitionFactory<WS,WT> ejbTransition() {return new EjbWorkflowTransitionFactory<>(cTransition);}
	public EjbWorkflowCommunicationFactory<WT,AC,MT,MC,SR,RE> ejbCommunication() {return new EjbWorkflowCommunicationFactory<>(cCommunication);}
	public EjbWorkflowActionFactory<WT,AA,AB,AO,RE,RA> ejbAction() {return new EjbWorkflowActionFactory<>(cAction);}
	public EjbWorkflowLinkFactory<RE,AL,AW> ejbLink() {return new EjbWorkflowLinkFactory<>(cLink);}
	public EjbWorkflowFactory<WP,WS,AW> ejbWorkflow() {return new EjbWorkflowFactory<>(cWorkflow);}
	public EjbWorkflowActivityFactory<WT,AW,WY,USER> ejbActivity() {return new EjbWorkflowActivityFactory<>(cActivity);}
}