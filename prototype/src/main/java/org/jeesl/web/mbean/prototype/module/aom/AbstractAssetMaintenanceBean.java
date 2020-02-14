package org.jeesl.web.mbean.prototype.module.aom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.module.JeeslAssetCacheBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetEventFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventStatus;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.util.comparator.ejb.module.asset.EjbAssetComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAssetMaintenanceBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
										REALM extends JeeslAomRealm<L,D,REALM,?>, RREF extends EjbWithId,
										COMPANY extends JeeslAomCompany<REALM,SCOPE>,
										SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
										ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,STATUS,ATYPE>,
										STATUS extends JeeslAomStatus<L,D,STATUS,?>,
										ATYPE extends JeeslAomType<L,D,REALM,ATYPE,?>,
										EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS>,
										ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
										ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetMaintenanceBean.class);
	
	protected JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,ATYPE,EVENT,ETYPE,ESTATUS> fAsset;
	
	private final AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,ATYPE,EVENT,ETYPE,ESTATUS> fbAsset;
	
	private final EjbAssetEventFactory<COMPANY,ASSET,EVENT,ETYPE,ESTATUS> efEvent;
	
	private final Comparator<ASSET> cpAsset;
	
	private final SbMultiHandler<ESTATUS> sbhEventStatus; public SbMultiHandler<ESTATUS> getSbhEventStatus() {return sbhEventStatus;}
	
	private final List<EVENT> events; public List<EVENT> getEvents() {return events;}
	    
	private REALM realm; public REALM getRealm() {return realm;}
	private RREF rref; public RREF getRref() {return rref;}

	private EVENT event; public EVENT getEvent() {return event;} public void setEvent(EVENT event) {this.event = event;}

	public AbstractAssetMaintenanceBean(AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,ATYPE,EVENT,ETYPE,ESTATUS> fbAsset)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
		
		efEvent = fbAsset.ejbEvent();
		
		cpAsset = fbAsset.cpAsset(EjbAssetComparator.Type.position);
		
		sbhEventStatus = new SbMultiHandler<>(fbAsset.getClassEventStatus(),this);
		
		events = new ArrayList<>();
	}
	
	protected <E extends Enum<E>> void postConstructAssetMaintenance(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,ATYPE,EVENT,ETYPE,ESTATUS> fAsset,
									JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,STATUS,ATYPE,ETYPE> bCache,
									E eRealm, RREF rref
									)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fAsset=fAsset;
		
		realm = fAsset.fByEnum(fbAsset.getClassRealm(),eRealm);
		this.rref=rref;
		
		sbhEventStatus.setList(fAsset.all(fbAsset.getClassEventStatus()));
		sbhEventStatus.preSelect(JeeslAomEventStatus.Code.planned);
		sbhEventStatus.preSelect(JeeslAomEventStatus.Code.postponed);
		sbhEventStatus.preSelect(JeeslAomEventStatus.Code.confirmed);
		
		reloadEvents();
	}
	
	@Override public void toggled(Class<?> c)
	{
		logger.info("Toggled");
		reloadEvents();
	}
	
	
	
	private void reloadEvents()
	{
		events.clear();
		events.addAll(fAsset.fAssetEvents(realm, rref, sbhEventStatus.getSelected()));
	}
    
    public void addEvent()
    {
    	logger.info(AbstractLogMessage.addEntity(fbAsset.getClassEvent()));
    	efEvent.ejb2nnb(event,nnb);
    }
    
    public void selectEvent()
    {
    	logger.info(AbstractLogMessage.selectEntity(event));
    	event = fAsset.find(fbAsset.getClassEvent(),event);
    	efEvent.ejb2nnb(event,nnb);
    	Collections.sort(event.getAssets(),cpAsset);
    }
    
    public void saveEvent() throws JeeslConstraintViolationException, JeeslLockingException
    {
    	logger.info(AbstractLogMessage.saveEntity(event));
    	efEvent.converter(fAsset,event);
    	efEvent.nnb2ejb(event,nnb);
    	event = fAsset.save(event);
    	reloadEvents();
    }
}