package org.jeesl.web.mbean.prototype.system.io.revision;

import java.io.Serializable;
import java.util.Collections;

import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionViewMapping;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.er.JeeslRevisionDiagram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionScopeBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionScopeBean.class);
	
	private RS scope; public RS getScope() {return scope;} public void setScope(RS scope) {this.scope = scope;}

	public AbstractAdminRevisionScopeBean(final IoRevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD> fbRevision){super(fbRevision);}
	
	protected void initSuper(String[] langs, JeeslFacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD> fRevision)
	{
		super.initRevisionSuper(langs,bMessage,fRevision);
		types = fRevision.allOrderedPositionVisible(fbRevision.getClassAttributeType());
		reloadScopes();
	}
	
	@Override public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{
		super.toggled(c);
		logger.info(AbstractLogMessage.toggled(c));
		reloadScopes();
		cancel();
	}

	public void reloadScopes()
	{
		scopes = fRevision.findRevisionScopes(sbhCategory.getSelected(), true);
		logger.info(AbstractLogMessage.reloaded(fbRevision.getClassScope(),scopes));
		Collections.sort(scopes, comparatorScope);
	}
	
	private void reloadScope()
	{
		scope = fRevision.load(fbRevision.getClassScope(), scope);
		attributes = scope.getAttributes();
	}
	
	public void add() throws JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbRevision.getClassScope()));
		scope = efScope.build();
		scope.setName(efLang.createEmpty(langs));
		scope.setDescription(efDescription.createEmpty(langs));
	}
	
	public void select() throws JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(scope));
		scope = fRevision.find(fbRevision.getClassScope(),scope);
		scope = efLang.persistMissingLangs(fRevision,langs,scope);
		scope = efDescription.persistMissingLangs(fRevision,langs,scope);
		reloadScope();
		attribute=null;
	}
	
	public void save() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(scope));
		scope = fRevision.save(scope);
		bMessage.growlSuccessSaved();
		reloadScopes();
		reloadScope();
		updatePerformed();
	}
	
	public void rm() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(scope));
		fRevision.rm(scope);
		bMessage.growlSuccessRemoved();
		scope=null;
		reloadScopes();
		updatePerformed();
	}
	
	public void cancel()
	{
		scope=null;
		attribute=null;
	}
	
	public void saveAttribute() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(attribute));}
		if(attribute.getType()!=null){attribute.setType(fRevision.find(fbRevision.getClassAttributeType(), attribute.getType()));}
		attribute = fRevision.save(fbRevision.getClassScope(),scope,attribute);
		reloadScope();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmAttribute() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(attribute));}
		fRevision.rm(fbRevision.getClassScope(),scope,attribute);
		attribute=null;
		bMessage.growlSuccessRemoved();
		reloadScope();
		updatePerformed();
	}
	
	protected void reorderScopes() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fRevision, fbRevision.getClassScope(), scopes);Collections.sort(scopes, comparatorScope);}
	protected void reorderAttributes() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fRevision, attributes);}
	
	protected void updatePerformed(){}	
}