package org.jeesl.web.mbean.prototype.module.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.api.facade.module.JeeslWorkflowFacade;
import org.jeesl.controller.handler.module.workflow.JeeslWorkflowEngine;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.builder.io.IoTemplateFactoryBuilder;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
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
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransitionType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslIdentity;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractWorkflowDashboardBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslStatus<LOC,L,D>,
											AX extends JeeslWorkflowContext<L,D,AX,?>,
											AP extends JeeslWorkflowProcess<L,D,AX,WS>,
											WS extends JeeslWorkflowStage<L,D,AP,WST,WSP,WT,?>,
											WST extends JeeslWorkflowStageType<L,D,WST,?>,
											WSP extends JeeslWorkflowStagePermission<WS,WPT,WML,SR>,
											WPT extends JeeslWorkflowPermissionType<L,D,WPT,?>,
											WML extends JeeslWorkflowModificationLevel<?,?,WML,?>,
											WT extends JeeslWorkflowTransition<L,D,WS,WTT,SR,?>,
											WTT extends JeeslWorkflowTransitionType<L,D,WTT,?>,
											AC extends JeeslWorkflowCommunication<WT,MT,MC,SR,RE>,
											AA extends JeeslWorkflowAction<WT,AB,AO,RE,RA>,
											AB extends JeeslWorkflowBot<AB,L,D,?>,
											AO extends EjbWithId,
											MT extends JeeslIoTemplate<L,D,?,?,MD,?>,
											MC extends JeeslTemplateChannel<L,D,MC,?>,
											MD extends JeeslIoTemplateDefinition<D,MC,MT>,
											SR extends JeeslSecurityRole<L,D,?,?,?,?,USER>,
											RE extends JeeslRevisionEntity<L,D,?,?,RA,?>,
											RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
											AL extends JeeslWorkflowLink<AW,RE>,
											AW extends JeeslWorkflow<AP,WS,AY>,
											AY extends JeeslWorkflowActivity<WT,AW,FRC,USER>,
											FRC extends JeeslFileContainer<?,?>,
											WCS extends JeeslConstraint<L,D,?,?,?,?,?,?>,
											USER extends JeeslUser<SR>,
											ID extends JeeslIdentity<SR,?,?,?,USER>>
				extends AbstractAdminBean<L,D>
				implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractWorkflowDashboardBean.class);

	private JeeslWorkflowFacade<L,D,LOC,AX,AP,WS,WST,WSP,WPT,WML,WT,WTT,AC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,AY,FRC,USER> fWorkflow;
	private JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?,?> fRevision;
	
	private final WorkflowFactoryBuilder<L,D,AX,AP,WS,WST,WSP,WPT,WML,WT,WTT,AC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,AY,FRC,USER> fbApproval;
	private final IoTemplateFactoryBuilder<L,D,?,?,MT,?,?,?,?> fbTemplate;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?,?> fbRevision;
	private final SecurityFactoryBuilder<L,D,?,SR,?,?,?,?,?,?,?,?> fbSecurity;
	
	private JeeslWorkflowEngine<L,D,LOC,AX,AP,WS,WST,WSP,WPT,WML,WT,WTT,AC,AA,AB,AO,MT,MC,MD,SR,RE,RA,AL,AW,AY,FRC,WCS,USER> wfEngine;
	
	private final SbSingleHandler<AX> sbhContext; public SbSingleHandler<AX> getSbhContext() {return sbhContext;}
	private final SbSingleHandler<AP> sbhProcess; public SbSingleHandler<AP> getSbhProcess() {return sbhProcess;}
	
	private final List<AW> workflows; public List<AW> getWorkflows() {return workflows;}
	
	protected AW workflow; public AW getWorkflow() {return workflow;} public void setProcess(AW workflow) {this.workflow = workflow;}

	public AbstractWorkflowDashboardBean(final WorkflowFactoryBuilder<L,D,AX,AP,WS,WST,WSP,WPT,WML,WT,WTT,AC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,AY,FRC,USER> fbApproval,
											final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?,?> fbRevision,
											final SecurityFactoryBuilder<L,D,?,SR,?,?,?,?,?,?,?,?> fbSecurity,
											final IoTemplateFactoryBuilder<L,D,?,?,MT,?,?,?,?> fbTemplate)
	{
		super(fbApproval.getClassL(),fbApproval.getClassD());
		this.fbApproval=fbApproval;
		this.fbRevision=fbRevision;
		this.fbSecurity=fbSecurity;
		this.fbTemplate=fbTemplate;
		
		sbhContext = new SbSingleHandler<AX>(fbApproval.getClassContext(),this);
		sbhProcess = new SbSingleHandler<AP>(fbApproval.getClassProcess(),this);
		
		workflows = new ArrayList<>();
	}
	
	protected void postConstructProcess(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
										JeeslWorkflowFacade<L,D,LOC,AX,AP,WS,WST,WSP,WPT,WML,WT,WTT,AC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,AY,FRC,USER> fWorkflow,
										JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?,?> fRevision,
										JeeslWorkflowEngine<L,D,LOC,AX,AP,WS,WST,WSP,WPT,WML,WT,WTT,AC,AA,AB,AO,MT,MC,MD,SR,RE,RA,AL,AW,AY,FRC,WCS,USER> wfEngine)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fWorkflow=fWorkflow;
		this.fRevision=fRevision;
		this.wfEngine=wfEngine;
		
		initPageSettings();
		
		reloadProcesses();
		if(sbhProcess.isSelected())
		{
			reloadWorkflows();
		}
	}
	
	
	protected void initPageSettings()
	{

		sbhContext.setList(fWorkflow.allOrderedPositionVisible(fbApproval.getClassContext()));
		sbhContext.setDefault();
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbApproval.getClassContext(), sbhContext.getList()));}
	}
	
	private void reset()
	{
		
	}
	
	@Override
	public void selectSbSingle(EjbWithId item) throws JeeslLockingException, JeeslConstraintViolationException
	{
		if(item instanceof JeeslWorkflowContext) {reloadProcesses();}
		else if(item instanceof JeeslWorkflowProcess)
		{
			reloadWorkflows();
		}
	}
	
	public void reloadProcesses()
	{
		sbhProcess.update(fWorkflow.all(fbApproval.getClassProcess()),sbhProcess.getSelection());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbApproval.getClassProcess(), sbhProcess.getList(),sbhContext.getSelection()));}
	}
	
	public void reloadWorkflows()
	{
		workflows.clear();
		workflows.addAll(fWorkflow.all(fbApproval.getClassWorkflow()));
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbApproval.getClassWorkflow(), workflows));}	
	}
	
}