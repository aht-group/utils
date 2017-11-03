package org.jeesl.web.mbean.prototype.system.io.revision;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.jeesl.api.bean.JeeslLabelBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.factory.builder.system.RevisionFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionEntityBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RVM extends JeeslRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractAdminRevisionBean<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionEntityBean.class);
	
	private JeeslLabelBean<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> bLabel;
	
	private List<RE> links; public List<RE> getLinks() {return links;}
	
	private RE entity; public RE getEntity() {return entity;} public void setEntity(RE entity) {this.entity = entity;}
	private REM mapping; public REM getMapping() {return mapping;}public void setMapping(REM mapping) {this.mapping = mapping;}
	
	private String className; public String getClassName() {return className;}
	
	public AbstractAdminRevisionEntityBean(final RevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fbRevision){super(fbRevision);}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision, JeeslLabelBean<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> bLabel)
	{
		super.initRevisionSuper(langs,bMessage,fRevision);
		this.bLabel=bLabel;
		
		scopes = fRevision.all(fbRevision.getClassScope());
		types = fRevision.allOrderedPositionVisible(fbRevision.getClassAttributeType());
		scopeTypes = fRevision.allOrderedPositionVisible(fbRevision.getClassScopeType());
		reloadEntities();
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		super.toggled(c);
		logger.info(AbstractLogMessage.toggled(c));
		reloadEntities();
		cancelEntity();
	}

	private void reloadEntities()
	{
		entities = fRevision.findEntities(fbRevision.getClassEntity(), fbRevision.getClassCategory(), sbhCategory.getSelected(), true);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbRevision.getClassEntity(),entities));}
		Collections.sort(entities, comparatorEntity);
	}
	
	public void addEntity() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbRevision.getClassEntity()));}
		entity = efEntity.build(null);
		entity.setName(efLang.createEmpty(langs));
		entity.setDescription(efDescription.createEmpty(langs));
		attribute=null;
		mapping=null;
	}
	
	private void reloadEntity()
	{
		entity = fRevision.load(fbRevision.getClassEntity(), entity);
		attributes = entity.getAttributes();
		entityMappings = entity.getMaps();
		try
		{
			Class<?> c = Class.forName(entity.getCode());
			className = c.getSimpleName();
		}
		catch (ClassNotFoundException e)
		{
			className = "CLASS NOT FOUND";
		}
	}
	
	public void selectEntity() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(entity));}
		entity = fRevision.find(fbRevision.getClassEntity(), entity);
		entity = efLang.persistMissingLangs(fRevision,langs,entity);
		entity = efDescription.persistMissingLangs(fRevision,langs,entity);
		reloadEntity();
		attribute=null;
		mapping=null;
	}
	
	public void saveEntity() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(entity));}
		if(entity.getCategory()!=null){entity.setCategory(fRevision.find(fbRevision.getClassCategory(), entity.getCategory()));}
		entity = fRevision.save(entity);
		reloadEntities();
		reloadEntity();
		bMessage.growlSuccessSaved();
		bLabel.reload(entity);
		updatePerformed();
	}
	
	public void rmEntity() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(entity));}
		fRevision.rm(entity);
		entity=null;
		mapping=null;
		attribute=null;
		bMessage.growlSuccessRemoved();
		reloadEntities();
		updatePerformed();
	}
	
	public void cancelEntity()
	{
		entity = null;
		attribute=null;
		mapping=null;
	}
	
	//*************************************************************************************
	
	public void saveAttribute() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(attribute));}
		if(attribute.getType()!=null){attribute.setType(fRevision.find(fbRevision.getClassAttributeType(), attribute.getType()));}
		attribute = fRevision.save(fbRevision.getClassEntity(),entity,attribute);
		reloadEntity();
		bMessage.growlSuccessSaved();
		bLabel.reload(entity);
		updatePerformed();
	}
	
	public void rmAttribute() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(attribute));}
		fRevision.rm(fbRevision.getClassEntity(),entity,attribute);
		attribute=null;
		bMessage.growlSuccessRemoved();
		reloadEntity();
		updatePerformed();
	}
	
	//*************************************************************************************
	
	public void addMapping() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbRevision.getClassEntityMapping()));}
		RST rst = null; if(!scopeTypes.isEmpty()){rst=scopeTypes.get(0);}
		mapping = efMappingEntity.build(entity,null,rst);
		updateUi();
	}
	
	public void selectMapping() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(mapping));}
		mapping = fRevision.find(fbRevision.getClassEntityMapping(), mapping);
		updateUi();
	}
	
	public void saveMapping() throws UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(mapping));}
		mapping.setScope(fRevision.find(fbRevision.getClassScope(),mapping.getScope()));
		mapping.setType(fRevision.find(fbRevision.getClassScopeType(), mapping.getType()));
		
		try
		{
			mapping = fRevision.save(mapping);
			updateUi();
			reloadEntity();
			bMessage.growlSuccessSaved();
			updatePerformed();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}
	
	public void rmMapping() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(mapping));}
		fRevision.rm(mapping);
		mapping=null;
		bMessage.growlSuccessRemoved();
		reloadEntity();
		updatePerformed();
	}
	
	public void cancelMapping()
	{
		mapping=null;
	}
	
	public void changeScopeType()
	{
		mapping.setType(fRevision.find(fbRevision.getClassScopeType(), mapping.getType()));
		logger.info(AbstractLogMessage.selectEntity(mapping, mapping.getType()));
		updateUi();
	}
	
	//UI
	private boolean uiWithJpqlTree;
	
	public boolean isUiWithJpqlTree() {
		return uiWithJpqlTree;
	}
	private void updateUi()
	{
		uiWithJpqlTree = false;
		if(mapping!=null)
		{
			if(mapping.getType().getCode().equals(JeeslRevisionEntityMapping.Type.jpqlTree.toString())){uiWithJpqlTree=true;}
		}
	}
	
	protected void reorderEntites() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, fbRevision.getClassEntity(), entities);Collections.sort(entities, comparatorEntity);}
	protected void reorderAttributes() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, attributes);}
	protected void reorderMappings() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, entityMappings);}
	
	protected void updatePerformed(){}	
	
	@SuppressWarnings("rawtypes")
	@Override protected void updateSecurity2(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		uiAllowSave = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(uiAllowSave+" allowSave ("+actionDeveloper+")");
		}
	}
}