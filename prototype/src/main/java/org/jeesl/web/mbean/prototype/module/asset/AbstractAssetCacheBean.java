package org.jeesl.web.mbean.prototype.module.asset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.interfaces.bean.system.JeeslAssetCacheBean;
import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;
import org.jeesl.interfaces.model.module.asset.JeeslAssetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAssetCacheBean <L extends UtilsLang, D extends UtilsDescription,
										REALM extends JeeslAssetRealm<L,D,REALM,?>, RREF extends EjbWithId,
										ASSET extends JeeslAsset<REALM,ASSET,MANU,STATUS,TYPE>,
										MANU extends JeeslAssetManufacturer<REALM>,
										STATUS extends JeeslAssetStatus<L,D,STATUS,?>,
										TYPE extends JeeslAssetType<L,D,REALM,TYPE,?>>
								implements JeeslAssetCacheBean<L,D,REALM,RREF,ASSET,MANU,STATUS,TYPE>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetCacheBean.class);
	
	private JeeslAssetFacade<L,D,REALM,ASSET,MANU,STATUS,TYPE> fAsset;
	
	private final AssetFactoryBuilder<L,D,REALM,ASSET,MANU,STATUS,TYPE> fbAsset;

	private final Map<REALM,Map<RREF,List<TYPE>>> map; public Map<REALM, Map<RREF, List<TYPE>>> getMap() {return map;}

	public AbstractAssetCacheBean(AssetFactoryBuilder<L,D,REALM,ASSET,MANU,STATUS,TYPE> fbAsset)
	{
		this.fbAsset=fbAsset;
		map = new HashMap<>();
	}
	
	protected void reload(JeeslAssetFacade<L,D,REALM,ASSET,MANU,STATUS,TYPE> fAsset, REALM realm, RREF rref)
	{
		this.fAsset=fAsset;
		if(!map.containsKey(realm)) {map.put(realm,new HashMap<>());}
		if(!map.get(realm).containsKey(rref)) {map.get(realm).put(rref,new ArrayList<>());}
		else {map.get(realm).get(rref).clear();}

		TYPE root = fAsset.fcAssetRootType(realm,rref);
		reloadTypes(realm,rref,fAsset.allForParent(fbAsset.getClassType(),root));
		logger.info(AbstractLogMessage.reloaded(fbAsset.getClassType(), map.get(realm).get(rref), rref)+" in realm "+realm.toString());
		
	}
	
	private void reloadTypes(REALM realm, RREF rref, List<TYPE> types)
	{
		for(TYPE type : types)
		{
			map.get(realm).get(rref).add(type);
			reloadTypes(realm,rref,fAsset.allForParent(fbAsset.getClassType(),type));
		}
	}
}