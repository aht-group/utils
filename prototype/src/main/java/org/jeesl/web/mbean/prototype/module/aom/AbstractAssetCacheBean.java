package org.jeesl.web.mbean.prototype.module.aom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.module.JeeslAssetCacheBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
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
import org.jeesl.util.db.cache.EjbCodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAssetCacheBean <L extends JeeslLang, D extends JeeslDescription,
										REALM extends JeeslAomRealm<L,D,REALM,?>, RREF extends EjbWithId,
										COMPANY extends JeeslAomCompany<REALM,SCOPE>,
										SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
										ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,ASTATUS,ATYPE>,
										ASTATUS extends JeeslAomStatus<L,D,ASTATUS,?>,
										ATYPE extends JeeslAomType<L,D,REALM,ATYPE,?>,
										EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS>,
										ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
										ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>>
								implements JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ETYPE>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetCacheBean.class);
	
//	private JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset;
	
	private final AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS> fbAsset;

	private EjbCodeCache<SCOPE> cacheScope;
	
	private final Map<REALM,Map<RREF,List<ATYPE>>> mapAssetType; @Override public Map<REALM,Map<RREF,List<ATYPE>>> getMapAssetType() {return mapAssetType;}
	
	private final Map<REALM,Map<RREF,List<COMPANY>>> mapCompany; @Override public Map<REALM,Map<RREF,List<COMPANY>>> cachedCompany() {return mapCompany;}
	private final Map<REALM,Map<RREF,List<COMPANY>>> mapManufacturer; public Map<REALM,Map<RREF,List<COMPANY>>> getMapManufacturer() {return mapManufacturer;}
	private final Map<REALM,Map<RREF,List<COMPANY>>> mapVendor; @Override public Map<REALM,Map<RREF,List<COMPANY>>> getMapVendor() {return mapVendor;}
	private final Map<REALM,Map<RREF,List<COMPANY>>> mapMaintainer; @Override public Map<REALM,Map<RREF,List<COMPANY>>> getMapMaintainer() {return mapMaintainer;}
	
    private final List<ASTATUS> assetStatus; public List<ASTATUS> getAssetStatus() {return assetStatus;}
    private final List<ETYPE> eventType; @Override public List<ETYPE> getEventType() {return eventType;}
    private final List<ESTATUS> eventStatus; public List<ESTATUS> getEventStatus() {return eventStatus;}
    
    
    
	public AbstractAssetCacheBean(AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS> fbAsset)
	{
		this.fbAsset=fbAsset;
		
		mapAssetType = new HashMap<>();
		
		mapCompany = new HashMap<>();
		mapManufacturer = new HashMap<>();
		mapVendor = new HashMap<>();
		mapMaintainer = new HashMap<>();
		
		assetStatus = new ArrayList<>();
		eventType = new ArrayList<>();
		eventStatus = new ArrayList<>();
	}
	
	public void postConstruct(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS> fAsset)
	{
		if(cacheScope==null) {cacheScope = new EjbCodeCache<SCOPE>(fAsset,fbAsset.getClassScope());}
		if(assetStatus.isEmpty()) {assetStatus.addAll(fAsset.allOrderedPositionVisible(fbAsset.getClassStatus()));}
		if(eventType.isEmpty()) {eventType.addAll(fAsset.allOrderedPositionVisible(fbAsset.getClassEventType()));}
		if(eventStatus.isEmpty()) {eventStatus.addAll(fAsset.allOrderedPositionVisible(fbAsset.getClassEventStatus()));}
	}
	
	public void reloadRealm(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS> fAsset, REALM realm, RREF rref)
	{
		reloadAssetTypes(fAsset,realm,rref,false);
		
		if(!mapCompany.containsKey(realm)) {mapCompany.put(realm,new HashMap<>());}
		if(!mapCompany.get(realm).containsKey(rref)) {mapCompany.get(realm).put(rref,new ArrayList<>());}
		mapCompany.get(realm).get(rref).clear();
		mapCompany.get(realm).get(rref).addAll(fAsset.fAssetCompanies(realm,rref));
		logger.info(AbstractLogMessage.reloaded(fbAsset.getClassCompany(),mapCompany.get(realm).get(rref)));
		reloadCompanies(realm,rref);
	}
	
	public void reloadAssetTypes(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS> fAsset, REALM realm, RREF rref,boolean force)
	{		
		if(!mapAssetType.containsKey(realm)) {mapAssetType.put(realm,new HashMap<>());}	
		if(!mapAssetType.get(realm).containsKey(rref)) {mapAssetType.get(realm).put(rref,new ArrayList<>());}

		if(force || mapAssetType.get(realm).get(rref).isEmpty())
		{
			mapAssetType.get(realm).get(rref).clear();
			ATYPE root = fAsset.fcAssetRootType(realm,rref);
			reloadTypes(fAsset,realm,rref,fAsset.allForParent(fbAsset.getClassAssetType(),root));
			logger.info(AbstractLogMessage.reloaded(fbAsset.getClassAssetType(), mapAssetType.get(realm).get(rref), rref)+" in realm "+realm.toString());
		}
	}
	private void reloadTypes(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS> fAsset, REALM realm, RREF rref, List<ATYPE> types)
	{
		for(ATYPE type : types)
		{
			mapAssetType.get(realm).get(rref).add(type);
			reloadTypes(fAsset,realm,rref,fAsset.allForParent(fbAsset.getClassAssetType(),type));
		}
	}
	
	private void reloadCompanies(REALM realm, RREF rref)
	{
		reloadCompanies(realm,rref,cacheScope.ejb(JeeslAomScope.Code.manufacturer),mapManufacturer,mapCompany.get(realm).get(rref));
		reloadCompanies(realm,rref,cacheScope.ejb(JeeslAomScope.Code.vendor),mapVendor,mapCompany.get(realm).get(rref));
		reloadCompanies(realm,rref,cacheScope.ejb(JeeslAomScope.Code.maintainer),mapMaintainer,mapCompany.get(realm).get(rref));
	}
	
	private void reloadCompanies(REALM realm, RREF rref, SCOPE scope,
								Map<REALM,Map<RREF,List<COMPANY>>> map, List<COMPANY> companies)
	{		
		if(!map.containsKey(realm)) {map.put(realm,new HashMap<>());}
		if(!map.get(realm).containsKey(rref)) {map.get(realm).put(rref,new ArrayList<>());}
		map.get(realm).get(rref).clear();
		
		for(COMPANY c : companies)
		{
			if(c.getScopes().contains(scope)) {map.get(realm).get(rref).add(c);}
		}		
	}
	
	public void update(REALM realm, RREF rref, COMPANY company)
	{
		if(!Collections.replaceAll(mapCompany.get(realm).get(rref),company,company)){mapCompany.get(realm).get(rref).add(company);}
		reloadCompanies(realm,rref);
	}
}