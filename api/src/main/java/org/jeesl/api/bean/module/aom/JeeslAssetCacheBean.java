package org.jeesl.api.bean.module.aom;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.aom.asset.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomView;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomAssetStatus;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomAssetType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventUpload;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslAssetCacheBean <L extends JeeslLang, D extends JeeslDescription,
										REALM extends JeeslTenantRealm<L,D,REALM,?>, RREF extends EjbWithId,
										COMPANY extends JeeslAomCompany<REALM,SCOPE>,
										SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
										ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,ASTATUS,ATYPE>,
										ASTATUS extends JeeslAomAssetStatus<L,D,ASTATUS,?>,
										ATYPE extends JeeslAomAssetType<L,D,REALM,ATYPE,VIEW,?>,
										VIEW extends JeeslAomView<L,D,REALM,?>,
										ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
										UP extends JeeslAomEventUpload<L,D,UP,?>>
								extends Serializable
{
//	Map<RREF,List<COMPANY>> cachedLevel();
	
	
	Map<REALM,Map<RREF,List<ATYPE>>> getMapAssetType1();
	Map<REALM,Map<RREF,List<ATYPE>>> getMapAssetType2();
	
	List<ETYPE> getEventType();
	
	Map<RREF,List<COMPANY>> cachedCompany();
	Map<RREF,List<COMPANY>> getMapVendor();
	Map<RREF,List<COMPANY>> getMapMaintainer();
	
	void update(REALM realm, RREF rref, COMPANY company);
	
	void update(REALM realm, RREF rref, VIEW view, ATYPE type);
	void delete(REALM realm, RREF rref, VIEW view, ATYPE type);
}