package org.jeesl.controller.facade.module;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslWorkflowFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.factory.json.system.io.db.tuple.JsonTupleFactory;
import org.jeesl.factory.json.system.io.db.tuple.t1.Json1TuplesFactory;
import org.jeesl.factory.json.system.io.db.tuple.t2.Json2TuplesFactory;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslWorkflowFacadeBean<L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslStatus<LOC,L,D>,
									AX extends JeeslWorkflowContext<L,D,AX,?>,
									WP extends JeeslWorkflowProcess<L,D,AX,WS>,
									WS extends JeeslWorkflowStage<L,D,WP,WST,WSP,WT,?>,
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
					extends JeeslFacadeBean
					implements JeeslWorkflowFacade<L,D,LOC,AX,WP,WS,WST,WSP,WPT,WML,WT,WTT,AC,AA,AB,AO,MT,MC,SR,RE,RA,WL,WF,WY,FRC,USER>
{	
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowFacadeBean.class);
	
	private final WorkflowFactoryBuilder<L,D,AX,WP,WS,WST,WSP,WPT,WML,WT,WTT,AC,AA,AB,AO,MT,MC,SR,RE,RA,WL,WF,WY,FRC,USER> fbWorkflow;
	
	public JeeslWorkflowFacadeBean(EntityManager em, final WorkflowFactoryBuilder<L,D,AX,WP,WS,WST,WSP,WPT,WML,WT,WTT,AC,AA,AB,AO,MT,MC,SR,RE,RA,WL,WF,WY,FRC,USER> fbApproval)
	{
		super(em);
		this.fbWorkflow=fbApproval;
	}

	@Override
	public WT fTransitionBegin(WP process)
	{
		logger.warn("Optimisation required here!!");
		for(WS stage : this.allForParent(fbWorkflow.getClassStage(), process))
		{
			if(stage.getType().getCode().equals(JeeslWorkflowStageType.Code.start.toString()))
			{
				List<WT> transitions = this.allForParent(fbWorkflow.getClassTransition(), stage);
				for(WT t : transitions)
				{
					if(!t.getType().getCode().equals(JeeslWorkflowTransitionType.Code.auto.toString()))
					{
						logger.info("Returning: "+t.getType().getCode());
						return t;
					}
				}
			}
		}
		return null;
	}
	
	@Override public WL fWorkflowLink(WF workflow) throws JeeslNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<WL> cQ = cB.createQuery(fbWorkflow.getClassLink());
		Root<WL> link = cQ.from(fbWorkflow.getClassLink());
		
		Join<WL,WF> jWorkflow = link.join(JeeslWorkflowLink.Attributes.workflow.toString());
		
		cQ.where(cB.and(cB.equal(jWorkflow,workflow)));
		cQ.select(link);
		
		List<WL> links = em.createQuery(cQ).getResultList();
		
		if(!links.isEmpty())
		{
			if(links.size()==1) {return links.get(0);}
			else
			{
				logger.warn("NYI Multiple links");
				return links.get(0);
			}
		}
		else
		{
			throw new JeeslNotFoundException("No "+fbWorkflow.getClassLink()+" found for "+workflow.toString());
		}
	}
	
	@Override public List<WL> fWorkflowLinks(List<WF> workflows)
	{
		return new ArrayList<WL>();
	}

	@Override public <W extends JeeslWithWorkflow<WF>> WL fWorkflowLink(WP process, W owner) throws JeeslNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<WL> cQ = cB.createQuery(fbWorkflow.getClassLink());
		Root<WL> link = cQ.from(fbWorkflow.getClassLink());
		
		Join<WL,WF> jWorkflow = link.join(JeeslWorkflowLink.Attributes.workflow.toString());
		Path<WP> pProcess = jWorkflow.get(JeeslWorkflow.Attributes.process.toString());
		Path<Long> pRefId = link.get(JeeslWorkflowLink.Attributes.refId.toString());
		
		cQ.where(cB.and(cB.equal(pRefId,owner.getId()),cB.equal(pProcess,process)));
		cQ.select(link);
		
		List<WL> links = em.createQuery(cQ).getResultList();
		
		if(!links.isEmpty())
		{
			if(links.size()==1) {return links.get(0);}
			else
			{
				logger.warn("NYI Multiple links");
				return links.get(0);
			}
		}
		else
		{
			{throw new JeeslNotFoundException("No "+fbWorkflow.getClassLink()+" found for "+owner);}
		}
	}

	@Override public List<WF> fWorkflows(WP process, List<WS> stages) 
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<WF> cQ = cB.createQuery(fbWorkflow.getClassWorkflow());
		Root<WF> workflow = cQ.from(fbWorkflow.getClassWorkflow());
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Path<WP> pProcess = workflow.get(JeeslWorkflow.Attributes.process.toString());
		predicates.add(cB.equal(pProcess,process));
		
		if(stages!=null)
		{
			if(stages.isEmpty()) {return new ArrayList<>();}
			else
			{
				Join<WF,WS> jStage = workflow.join(JeeslWorkflow.Attributes.currentStage.toString());
				predicates.add(jStage.in(stages));
			}
		}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(workflow);
		
		return em.createQuery(cQ).getResultList();
	}
	
	@Override public List<WF> fWorkflows(List<WP> processes, List<WST> types)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<WF> cQ = cB.createQuery(fbWorkflow.getClassWorkflow());
		Root<WF> workflow = cQ.from(fbWorkflow.getClassWorkflow());
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(processes!=null && !processes.isEmpty())
		{
			Join<WF,WP> jProcess = workflow.join(JeeslWorkflow.Attributes.process.toString());
			predicates.add(jProcess.in(processes));
		}
		
		Join<WF,WS> jStage = workflow.join(JeeslWorkflow.Attributes.currentStage.toString());
		
		if(types!=null && !types.isEmpty())
		{
			Join<WS,WST> jType = jStage.join(JeeslWorkflowStage.Attributes.type.toString());
			predicates.add(jType.in(types));
		}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(workflow);
		
		return em.createQuery(cQ).getResultList();
	}

	@Override public Json1Tuples<WP> tpcActivitiesByProcess()
	{
		Json1TuplesFactory<WP> jtf = new Json1TuplesFactory<>(fbWorkflow.getClassProcess());
		jtf.setfUtils(this);
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
		Root<WF> item = cQ.from(fbWorkflow.getClassWorkflow());
		
		Expression<Long> eCount = cB.count(item.<Long>get("id"));
		Path<WP> pProcess = item.get(JeeslWorkflow.Attributes.process.toString());
		
		cQ.groupBy(pProcess.get("id"));
		cQ.multiselect(pProcess.get("id"),eCount);
	       
		TypedQuery<Tuple> tQ = em.createQuery(cQ);
        return jtf.buildCount(tQ.getResultList());
	}
	
	@Override public Json2Tuples<WP,WST> tpcActivitiesByProcessType()
	{
		Json2TuplesFactory<WP,WST> jtf = new Json2TuplesFactory<>(fbWorkflow.getClassProcess(),fbWorkflow.getClassStageType());
		jtf.setfUtils(this);
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
		Root<WF> workflow = cQ.from(fbWorkflow.getClassWorkflow());
		
		Expression<Long> eCount = cB.count(workflow.<Long>get("id"));
		Path<WP> pProcess = workflow.get(JeeslWorkflow.Attributes.process.toString());
		
		Join<WF,WS> jStage = workflow.join(JeeslWorkflow.Attributes.currentStage.toString());
		Join<WS,WST> jType = jStage.join(JeeslWorkflowStage.Attributes.type.toString());
		
		cQ.groupBy(pProcess.get("id"),jType.get("id"));
		cQ.multiselect(pProcess.get("id"),jType.get("id"),eCount);
	       
		TypedQuery<Tuple> tQ = em.createQuery(cQ);
        return jtf.build(tQ.getResultList(),JsonTupleFactory.Type.count);
	}
}