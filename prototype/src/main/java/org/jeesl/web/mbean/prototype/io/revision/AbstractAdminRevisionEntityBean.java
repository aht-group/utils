package org.jeesl.web.mbean.prototype.io.revision;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jeesl.api.bean.JeeslLabelBean;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.interfaces.model.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.io.revision.core.JeeslRevisionView;
import org.jeesl.interfaces.model.io.revision.core.JeeslRevisionViewMapping;
import org.jeesl.interfaces.model.io.revision.data.JeeslRevisionScope;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.io.revision.er.JeeslRevisionDiagram;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.web.JeeslJsfSecurityHandler;
import org.jeesl.jsf.handler.PositionListReorderer;
import org.jeesl.util.comparator.ejb.PositionParentComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionEntityBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											RC extends JeeslRevisionCategory<L,D,RC,?>,
											RV extends JeeslRevisionView<L,D,RVM>,
											RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
											RS extends JeeslRevisionScope<L,D,RC,RA>,
											RST extends JeeslStatus<RST,L,D>,
											RE extends JeeslRevisionEntity<L,D,RC,REM,RA,ERD>,
											REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
											RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>, RER extends JeeslStatus<RER,L,D>,
											RAT extends JeeslStatus<RAT,L,D>,
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

	public AbstractAdminRevisionEntityBean(final IoRevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD,?> fbRevision){super(fbRevision);}

	protected void postConstructRevisionEntity(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD,?> fRevision, JeeslLabelBean<RE> bLabel)
	{
		super.postConstructRevision(bTranslation,bMessage,fRevision);
		this.bLabel=bLabel;

		scopes = fRevision.all(fbRevision.getClassScope());
		types = fRevision.allOrderedPositionVisible(fbRevision.getClassAttributeType());
		relations = fRevision.allOrderedPositionVisible(fbRevision.getClassRelation());
		scopeTypes = fRevision.allOrderedPositionVisible(fbRevision.getClassScopeType());

		diagrams = fRevision.all(fbRevision.getClassDiagram());
		Collections.sort(diagrams, new PositionParentComparator<ERD>(fbRevision.getClassDiagram()));

		links = fRevision.all(fbRevision.getClassEntity());
		Collections.sort(links,cpEntity);

		reloadEntities();
	}

	@Override public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
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
		Collections.sort(entities,cpEntity);

		sbhDiagram.clear();
		sbhDiagram.setList(efEntity.toDiagrams(entities));
		Collections.sort(sbhDiagram.getList(),cpDiagram);
		sbhDiagram.selectAll();
	}

	public void addEntity() throws JeeslNotFoundException
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

	public void selectEntity() throws JeeslNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(entity));}
		entity = fRevision.find(fbRevision.getClassEntity(), entity);
		entity = efLang.persistMissingLangs(fRevision,langs,entity);
		entity = efDescription.persistMissingLangs(fRevision,langs,entity);
		reloadEntity();
		attribute=null;
		mapping=null;
	}

	public void saveEntity() throws JeeslLockingException, JeeslNotFoundException
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
		catch (JeeslConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}

	}

	public void rmEntity() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
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

	public void saveAttribute() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
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

	public void rmAttribute() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(attribute));}
		fRevision.rm(fbRevision.getClassEntity(),entity,attribute);
		attribute=null;
		bMessage.growlSuccessRemoved();
		reloadEntity();
		updatePerformed();
	}

	//*************************************************************************************

	public void addMapping() throws JeeslNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbRevision.getClassEntityMapping()));}
		RST rst = null; if(!scopeTypes.isEmpty()){rst=scopeTypes.get(0);}
		mapping = efMappingEntity.build(entity,null,rst);
		updateUi();
	}

	public void selectMapping() throws JeeslNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(mapping));}
		mapping = fRevision.find(fbRevision.getClassEntityMapping(), mapping);
		updateUi();
	}

	public void saveMapping() throws JeeslLockingException, JeeslNotFoundException
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
		catch (JeeslConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}

	public void rmMapping() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
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

	public void reorderEntites() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fRevision, fbRevision.getClassEntity(), entities);Collections.sort(entities, cpEntity);}
	public void reorderAttributes() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fRevision, attributes);}
	public void reorderMappings() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fRevision, entityMappings);}

	protected void updatePerformed(){}

	public void selectMissingEntity(String missingEntityCode) {
		logger.info(missingEntityCode);
		RE tempMissingEntity = null;
		List<RE> allRevisionEntities = fRevision.findRevisionEntities(sbhCategory.getList(), true);
		int count = 0;
		for (Iterator iterator = allRevisionEntities.iterator(); iterator.hasNext();) {
			RE re = (RE) iterator.next();
			if(re.getCode().endsWith("." + missingEntityCode)) {
				logger.info("selected:" + re.getCode());
				count++;
				tempMissingEntity = re;
			}
			if(count==1) {this.entity = tempMissingEntity; reloadEntity();}
			else if (count > 1) {logger.info("multiple entites "  + "repeat: " + (count-1) + " for same  missingEntityCode = " + missingEntityCode );}
			else {logger.info("No entities definded Yet for missingEntityCode = " + missingEntityCode );}
		}
	}

	public void initMissingLables() {
		entity=null;
		mapping=null;
		attribute=null;
	}

	@SuppressWarnings("rawtypes")
	public void pullAttributesFromClass() {
		try
		{
			Class<?> c = Class.forName(getEntity().getCode());
			List<Class> classes = new ArrayList<>();
			List<Field> fields = new ArrayList<>();

			classes.add(c);
			Class[] interfaces = c.getInterfaces();
			for (Class i : interfaces) {
				classes.add(i);
			}
			for (Class enumClass : classes) {
				Field[] allFields = enumClass.getDeclaredFields();
				for (Field f : allFields) {if(!isFieldAvilable(f,enumClass)) {fields.add(f); addPulledField(f);}}
			}

			reloadEntity();
			bMessage.growlSuccessSaved();
			bLabel.reload(entity);
		} catch (SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | JeeslLockingException | JeeslConstraintViolationException e) {
			e.printStackTrace();
			logger.warn("Pull Attributes From Class Exception: "+e.getMessage());
		}
	}

	private void addPulledField(Field f) throws InstantiationException, IllegalAccessException, JeeslLockingException, JeeslConstraintViolationException {
		attribute = efAttribute.build(null);
		attribute.setCode(f.getName());
		attribute.setName(efLang.createLangMap("en",StringUtils.capitalize(f.getName())));
		attribute.setDescription(efDescription.createEmpty(langs));
		attribute.setEntity(entity);
		attribute.setBean(true);
		//logger.info("size:" +types.size() + types.get(0).getCode() + types.get(0).getId());
		attribute.setType(types.get(0));
		//logger.info("entity:" + entity.getId());
		attribute = fRevision.save(fbRevision.getClassEntity(),entity,attribute);
	}

	private boolean isFieldAvilable(Field f,Class<?> c){
		int mod = f.getModifiers();
		//static, final and abstract field are marked as available so that its not saved for automatic download
		if(Modifier.isFinal(mod) || Modifier.isStatic(mod) || Modifier.isAbstract(mod)) {return true;}

		//check if field have getter methods
		//non getters field are marked as available so that its not saved for automatic download
		try {c.getDeclaredMethod("get"+ StringUtils.capitalize(f.getName()));}catch (Exception e) {
		try {c.getDeclaredMethod("is"+ StringUtils.capitalize(f.getName()));} catch (Exception e2) {return false;}}

		//check if field is already saved
		for (Iterator iterator = attributes.iterator(); iterator.hasNext();) {
			RA ra = (RA) iterator.next();
			if(ra.getCode().equals(f.getName())) {return true;}
		}
		return false;
	}

	public boolean isEmptyEntityReloaded() {
		if(className.isEmpty() || className.equals("CLASS NOT FOUND")) {
			return true;
		}
		return false;
	}
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