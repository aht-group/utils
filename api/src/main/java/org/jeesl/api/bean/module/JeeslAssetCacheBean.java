package org.jeesl.api.bean.module;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAssetCacheBean <L extends JeeslLang, D extends JeeslDescription,
										REALM extends JeeslAomRealm<L,D,REALM,?>, RREF extends EjbWithId,
										COMPANY extends JeeslAomCompany<REALM,SCOPE>,
										SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
										ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,ASTATUS,ATYPE>,
										ASTATUS extends JeeslAomStatus<L,D,ASTATUS,?>,
										ATYPE extends JeeslAomType<L,D,REALM,ATYPE,?>,
										ETYPE extends JeeslAomEventType<L,D,ETYPE,?>>
								extends Serializable
{
//	void reloadAssetTypes(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset, REALM realm, RREF rref);
	Map<REALM,Map<RREF,List<ATYPE>>> getMapAssetType();
	List<ETYPE> getEventType();
	
	Map<REALM,Map<RREF,List<COMPANY>>> cachedCompany();
	Map<REALM,Map<RREF,List<COMPANY>>> getMapVendor();
	Map<REALM,Map<RREF,List<COMPANY>>> getMapMaintainer();
	
	void update(REALM realm, RREF rref, COMPANY company);
}