package org.jeesl.web.mbean.prototype.system.io.revision;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jeesl.api.bean.JeeslLabelBean;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionViewMapping;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.er.JeeslRevisionDiagram;
import org.jeesl.interfaces.web.JeeslJsfSecurityHandler;
import org.jeesl.util.comparator.ejb.PositionParentComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionEntityBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
											RC extends JeeslRevisionCategory<L,D,RC,?>,
											RV extends JeeslRevisionView<L,D,RVM>,
											RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
											RS extends JeeslRevisionScope<L,D,RC,RA>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends JeeslRevisionEntity<L,D,RC,REM,RA,ERD>,
											REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
											RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>, RER extends UtilsStatus<RER,L,D>,
											RAT extends UtilsStatus<RAT,L,D>,
											ERD extends JeeslRevisionDiagram<L,D,RC>>
					extends AbstractAdminRevisionBean<L,D,LOC,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionEntityBean.class);
	
	private JeeslLabelBean<RE> bLabel;
	
	private List<RE> links; public List<RE> getLinks() {return links;}
	private List<ERD> diagrams; public List<ERD> getDiagrams() {return diagrams;}
	
	private RE entity; public RE getEntity() {return entity;} public void setEntity(RE entity) {this.entity = entity;}
	private REM mapping; public REM getMapping() {return mapping;}public void setMapping(REM mapping) {this.mapping = mapping;}
	
	private String className; public String getClassName() {return className;}
	
	public AbstractAdminRevisionEntityBean(final IoRevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD> fbRevision){super(fbRevision);}
	
	protected void postConstructRevisionEntity(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD> fRevision, JeeslLabelBean<RE> bLabel)
	{
		String[] langs = bTranslation.getLangKeys().toArray(new String[0]);
		super.initRevisionSuper(langs,bMessage,fRevision);
		this.bLabel=bLabel;
		
		scopes = fRevision.all(fbRevision.getClassScope());
		types = fRevision.allOrderedPositionVisible(fbRevision.getClassAttributeType());
		relations = fRevision.allOrderedPositionVisible(fbRevision.getClassRelation());
		scopeTypes = fRevision.allOrderedPositionVisible(fbRevision.getClassScopeType());
		
		diagrams = fRevision.all(fbRevision.getClassDiagram());
		Collections.sort(diagrams, new PositionParentComparator<ERD>(fbRevision.getClassDiagram()));
		
		links = fRevision.all(fbRevision.getClassEntity());
		Collections.sort(links,comparatorEntity);
		
		reloadEntities();
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.toggled(c));
		super.toggled(c);
		
		if(c.isAssignableFrom(fbRevision.getClassCategory()))
		{
			reloadEntities();
			logger.info(AbstractLogMessage.reloaded(fbRevision.getClassEntity(),entities));
			sbhDiagram.selectNone();
		}
		else if(c.isAssignableFrom(fbRevision.getClassDiagram()))
		{
			logger.info(AbstractLogMessage.reloaded(fbRevision.getClassDiagram(),diagrams));
			if(sbhDiagram.hasSelected())
			{
				Iterator<RE> i = entities.iterator();
				
				while (i.hasNext())
				{
					RE re = i.next();
			        if (re.getDiagram()==null || !sbhDiagram.getSelected().contains(re.getDiagram()))
			        {
			        	i.remove();
			        }
				}
			}
			
		}
		cancelEntity();
	}

	private void reloadEntities()
	{
//		if(debugOnInfo) {logger.info("fRevision==null?"+(fRevision==null)+" sbhCategory==null?"+(sbhCategory==null)+" sbhCategory.getSelected()==null?"+(sbhCategory.getSelected()==null));}
		entities = fRevision.findRevisionEntities(sbhCategory.getSelected(), true);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbRevision.getClassEntity(),entities));}
		Collections.sort(entities,comparatorEntity);
		
		sbhDiagram.clear();
		sbhDiagram.setList(efEntity.toDiagrams(entities));
		Collections.sort(sbhDiagram.getList(),cpDiagram);
		sbhDiagram.selectAll();
	}
	
	public void addEntity() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbRevision.getClassEntity()));}
		entity = efEntity.build(null,entities);
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
	
	public void saveEntity() throws UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(entity));}
		
		try
		{
			if(entity.getCategory()!=null){entity.setCategory(fRevision.find(fbRevision.getClassCategory(), entity.getCategory()));}
			if(entity.getDiagram()!=null){entity.setDiagram(fRevision.find(fbRevision.getClassDiagram(), entity.getDiagram()));}
			entity = fRevision.save(entity);
			reloadEntities();
			reloadEntity();
			bMessage.growlSuccessSaved();
			bLabel.reload(entity);
			updatePerformed();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
		
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
		changeRelation();
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
	private boolean uiWithJpqlTree; public boolean isUiWithJpqlTree() {return uiWithJpqlTree;}
	
	private void updateUi()
	{
		uiWithJpqlTree = false;
		if(mapping!=null)
		{
			if(mapping.getType().getCode().equals(JeeslRevisionEntityMapping.Type.jpqlTree.toString())){uiWithJpqlTree=true;}
		}
	}
	
	public void changeRelation()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(attribute.getRelation()));}
		if(attribute.getRelation()!=null) {attribute.setRelation(fRevision.find(fbRevision.getClassRelation(), attribute.getRelation()));}
		else {attribute.setEntity(null);}
		if(attribute.getEntity()!=null){attribute.setEntity(fRevision.find(fbRevision.getClassEntity(), attribute.getEntity()));}
	}
	
	public void reorderEntites() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, fbRevision.getClassEntity(), entities);Collections.sort(entities, comparatorEntity);}
	public void reorderAttributes() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, attributes);}
	public void reorderMappings() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, entityMappings);}
	
	protected void updatePerformed(){}	
	
	@SuppressWarnings("rawtypes")
	@Override protected void updateSecurity2(JeeslJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		uiAllowSave = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(uiAllowSave+" allowSave ("+actionDeveloper+")");
		}
	}
}