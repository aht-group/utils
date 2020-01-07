package org.jeesl.web.rest.module;

import org.jeesl.api.facade.module.JeeslWorkflowFacade;
import org.jeesl.api.rest.module.workflow.JeeslWorkflowRestExportInterface;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.factory.xml.module.workflow.XmlProcessFactory;
import org.jeesl.factory.xml.module.workflow.XmlProcessesFactory;
import org.jeesl.factory.xml.module.workflow.XmlWorkflowFactory;
import org.jeesl.factory.xml.system.status.XmlContextFactory;
import org.jeesl.factory.xml.system.status.XmlContextsFactory;
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
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.module.workflow.Processes;
import org.jeesl.model.xml.module.workflow.Workflow;
import org.jeesl.util.query.xml.module.XmlWorkflowQuery;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class WorkflowRestService <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
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
									AL extends JeeslWorkflowLink<AW,RE>,
									AW extends JeeslWorkflow<WP,WS,WY>,
									WY extends JeeslWorkflowActivity<WT,AW,FRC,USER>,
									FRC extends JeeslFileContainer<?,?>,
									USER extends JeeslUser<SR>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslWorkflowRestExportInterface
{
	final static Logger logger = LoggerFactory.getLogger(WorkflowRestService.class);
	
	private final JeeslWorkflowFacade<L,D,LOC,WX,WP,WS,WST,WSP,WPT,WML,WT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow;
	private final WorkflowFactoryBuilder<L,D,WX,WP,WS,WST,WSP,WPT,WML,WT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow;
	
	private final XmlProcessFactory<L,D,WX,WP,WS,WST,WSP,WPT,WML,WT,WTT,SR> xfProcess;
	
	private WorkflowRestService(WorkflowFactoryBuilder<L,D,WX,WP,WS,WST,WSP,WPT,WML,WT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow,
			JeeslWorkflowFacade<L,D,LOC,WX,WP,WS,WST,WSP,WPT,WML,WT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow)
	{
		super(fWorkflow,fbWorkflow.getClassL(),fbWorkflow.getClassD());
		this.fWorkflow=fWorkflow;
		this.fbWorkflow=fbWorkflow;
		
		xfProcess = new XmlProcessFactory<>(XmlWorkflowQuery.get(XmlWorkflowQuery.Key.xProcess));
		xfProcess.lazy(fbWorkflow, fWorkflow);
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
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
						AL extends JeeslWorkflowLink<AW,RE>,
						AW extends JeeslWorkflow<WP,WS,WY>,
						WY extends JeeslWorkflowActivity<WT,AW,FRC,USER>,
						FRC extends JeeslFileContainer<?,?>,
						USER extends JeeslUser<SR>>
			WorkflowRestService<L,D,LOC,WX,WP,WS,WST,WSP,WPT,WML,WT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER>
			factory(WorkflowFactoryBuilder<L,D,WX,WP,WS,WST,WSP,WPT,WML,WT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow,
					JeeslWorkflowFacade<L,D,LOC,WX,WP,WS,WST,WSP,WPT,WML,WT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow)
	{
		return new WorkflowRestService<>(fbWorkflow,fWorkflow);
	}

	@Override public Container exportWorkflowContext() {return xfContainer.build(fWorkflow.allOrderedPosition(fbWorkflow.getClassContext()));}

	@Override
	public Workflow exportWorkflowProcesses()
	{
		Workflow xml = XmlWorkflowFactory.build();
		xml.setContexts(XmlContextsFactory.build(exportWorkflowContext().getStatus()));
		
		Processes processes = XmlProcessesFactory.build();
		for(WP process : fWorkflow.all(fbWorkflow.getClassProcess()))
		{
			processes.getProcess().add(xfProcess.build(process));
		}
		xml.setProcesses(processes);
		
		return xml;
	}
}