package org.jeesl.controller.handler.ui.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.module.JeeslAssetCacheBean;
import org.jeesl.factory.ejb.util.EjbIdFactory;
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
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UiHelperAsset <L extends JeeslLang, D extends JeeslDescription,
								REALM extends JeeslAomRealm<L,D,REALM,?>, RREF extends EjbWithId,
								COMPANY extends JeeslAomCompany<REALM,SCOPE>,
								SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
								ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,ASTATUS,ATYPE>,
								ASTATUS extends JeeslAomStatus<L,D,ASTATUS,?>,
								ATYPE extends JeeslAomType<L,D,REALM,ATYPE,?>,
								EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS,USER>,
								ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
								ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>,
								USER extends JeeslSimpleUser>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(UiHelperAsset.class);
		
	private JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ETYPE> bCache; public void setCacheBean(JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ETYPE> bCache) {this.bCache = bCache;}
	
	private final List<COMPANY> companies; public List<COMPANY> getCompanies() {return companies;}
	
	private boolean showAmount; public boolean isShowAmount() {return showAmount;}	
	private boolean showCompany; public boolean isShowCompany() {return showCompany;}
	
	public UiHelperAsset()
	{
		companies = new ArrayList<>();
		reset();
	}
	
	private void reset()
	{
		companies.clear();
		
		showAmount = false;
		showCompany = false;
	}

	public void update(REALM realm, RREF rref, EVENT event)
	{
		reset();
		boolean isQuote = event.getType().getCode().equals(JeeslAomEventType.Code.quote.toString());
		boolean isProcurement = event.getType().getCode().equals(JeeslAomEventType.Code.procurement.toString());
		boolean isDeployment = event.getType().getCode().equals(JeeslAomEventType.Code.deployment.toString());
		boolean isMaintenance = event.getType().getCode().equals(JeeslAomEventType.Code.maintenance.toString());
		boolean isRenew = event.getType().getCode().equals(JeeslAomEventType.Code.renew.toString());
		
		showAmount = EjbIdFactory.isSaved(event) && (isQuote || isProcurement || isDeployment || isMaintenance || isRenew);
		
		if(EjbIdFactory.isSaved(event) && bCache!=null)
		{
			if(isQuote)
			{
				companies.addAll(bCache.cachedCompany().get(realm).get(rref));
			}
			else if(isProcurement)
			{
				companies.addAll(bCache.getMapVendor().get(realm).get(rref));
			}
			else if(isDeployment)
			{
				companies.addAll(bCache.getMapMaintainer().get(realm).get(rref));
			}
			else if(isMaintenance || isRenew)
			{
				companies.addAll(bCache.getMapMaintainer().get(realm).get(rref));
			}
			showCompany = isQuote || isProcurement || isDeployment || isMaintenance || isRenew; 
		}
		
	}
}