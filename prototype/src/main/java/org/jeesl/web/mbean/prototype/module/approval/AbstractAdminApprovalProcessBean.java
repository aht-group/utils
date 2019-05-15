package org.jeesl.web.mbean.prototype.module.approval;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslApprovalFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.module.ApprovalFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminApprovalProcessBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
											CTX extends JeeslApprovalContext<CTX,L,D,?>,
											P extends JeeslApprovalProcess<L,D,CTX>,
											S extends JeeslApprovalStage<L,D,P>,
											T extends JeeslApprovalTransition<L,D,S>
											>
				extends AbstractAdminBean<L,D>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminApprovalProcessBean.class);

	protected JeeslApprovalFacade<L,D,CTX,P,S,T> fApproval;
	protected final ApprovalFactoryBuilder<L,D,CTX,P,S,T> fbApproval;
	
	private final SbSingleHandler<CTX> sbhContext; public SbSingleHandler<CTX> getSbhContext() {return sbhContext;}
	private final SbSingleHandler<P> sbhProcess; public SbSingleHandler<P> getSbhProcess() {return sbhProcess;}
		
	private List<S> stages; public List<S> getStages() {return stages;} public void setStages(List<S> stages) {this.stages = stages;}
	private final List<T> transitions; public List<T> getTransitions() {return transitions;} 
	
	protected P process; public P getProcess() {return process;} public void setProcess(P process) {this.process = process;}
	private S stage; public S getStage() {return stage;} public void setStage(S stage) {this.stage = stage;}
	private T transition; public T getTransition() {return transition;} public void setTransition(T transition) {this.transition = transition;}
	
	private boolean editStage; public boolean isEditStage() {return editStage;} public void toggleEditStage() {editStage=!editStage;}
	private boolean editTransition; public boolean isEditTransition() {return editTransition;} public void toggleEditTransition() {editTransition=!editTransition;}

	public AbstractAdminApprovalProcessBean(final ApprovalFactoryBuilder<L,D,CTX,P,S,T> fbApproval)
	{
		super(fbApproval.getClassL(),fbApproval.getClassD());
		this.fbApproval=fbApproval;
		sbhContext = new SbSingleHandler<CTX>(fbApproval.getClassContext(),this);
		sbhProcess = new SbSingleHandler<P>(fbApproval.getClassProcess(),this);
		
		transitions = new ArrayList<>();
		editStage = false;
	}
	
	protected void postConstructProcess(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslApprovalFacade<L,D,CTX,P,S,T> fApproval, JeeslFacesMessageBean bMessage)
	{
		super.initJeeslAdmin(bTranslation, bMessage);
		this.fApproval=fApproval;
		reloadContexts();
		reloadProcesses();
		if(sbhProcess.isSelected())
		{
			process = fApproval.find(fbApproval.getClassProcess(),sbhProcess.getSelection());
			reloadStages();
		}
	}
	
	private void reset(boolean rTransitions, boolean rTransition)
	{
		if(rTransitions) {transitions.clear();}
		if(rTransition) {transition=null;}
	}
	
	private void reloadContexts()
	{
		sbhContext.setList(fApproval.allOrderedPositionVisible(fbApproval.getClassContext()));
		sbhContext.setDefault();
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbApproval.getClassContext(), sbhContext.getList()));}
	}
	
	@Override
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(item instanceof JeeslApprovalContext) {reloadProcesses();}
		else if(item instanceof JeeslApprovalProcess)
		{
			process = fApproval.find(fbApproval.getClassProcess(),sbhProcess.getSelection());
			reloadStages();
		}
	}
	
	public void reloadProcesses()
	{
		sbhProcess.update(fApproval.all(fbApproval.getClassProcess()),sbhProcess.getSelection());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbApproval.getClassProcess(), sbhProcess.getList(),sbhContext.getSelection()));}
	}
	
	public void reloadStages()
	{
		stages = fApproval.allForParent(fbApproval.getClassStage(), process);
	}

	public void addProcess() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbApproval.getClassProcess()));
		process = fbApproval.ejbProcess().build();
		process.setName(efLang.createEmpty(localeCodes));
		process.setDescription(efDescription.createEmpty(localeCodes));
		process.setContext(sbhContext.getSelection());
	}
	
	public void selectProcess() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(process));
		process = fApproval.find(fbApproval.getClassProcess(), process);
		process = efLang.persistMissingLangs(fApproval,localeCodes,process);
		process = efDescription.persistMissingLangs(fApproval,localeCodes,process);
		reloadStages();
	}
	
	public void saveProcess() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(process));
		process.setContext(fApproval.find(fbApproval.getClassContext(), process.getContext()));
		process = fApproval.save(process);
		reloadProcesses();
	}
	
	public void deleteProcess() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(process));
		fApproval.rm(process);
		reset(true,true);
		reloadProcesses();
	}
	
	public void addStage()
	{
		reset(true,true);
		logger.info(AbstractLogMessage.addEntity(fbApproval.getClassProcess()));
		stage = fbApproval.ejbStage().build(process,stages);
		stage.setName(efLang.createEmpty(localeCodes));
		stage.setDescription(efDescription.createEmpty(localeCodes));
		stage.setProcess(process);
		editStage = true;
	}
	
	public void saveStage() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(stage));
		stage.setProcess(fApproval.find(fbApproval.getClassProcess(), stage.getProcess()));
		stage = fApproval.save(stage);
		reloadStages();
	}
	
	public void selectStage() throws UtilsNotFoundException
	{
		reset(true,true);
		logger.info(AbstractLogMessage.selectEntity(stage));
		stage = fApproval.find(fbApproval.getClassStage(), stage);
		stage = efLang.persistMissingLangs(fApproval,localeCodes,stage);
		stage = efDescription.persistMissingLangs(fApproval,localeCodes,stage);
		editStage = false;
		reloadTransitions();
	}
	
	private void reloadTransitions()
	{
		transitions.clear();
		transitions.addAll(fApproval.allForParent(fbApproval.getClassTransition(), stage));
	}
	
	public void addTransition()
	{
		reset(false,true);
		logger.info(AbstractLogMessage.addEntity(fbApproval.getClassTransition()));
		transition = fbApproval.ejbTransition().build(stage,transitions);
		transition.setName(efLang.createEmpty(localeCodes));
		transition.setDescription(efDescription.createEmpty(localeCodes));
		editTransition = true;
	}
	
	protected void reorderProcesses() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fApproval,sbhProcess.getList());}
}