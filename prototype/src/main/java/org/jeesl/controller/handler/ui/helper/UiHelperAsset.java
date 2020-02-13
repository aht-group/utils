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
								EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS>,
								ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
								ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(UiHelperAsset.class);
		
	private JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE> bCache; public void setCacheBean(JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE> bCache) {this.bCache = bCache;}
	
	private final List<COMPANY> companies; public List<COMPANY> getCompanies() {return companies;}
	

	private boolean showAmount; public boolean isShowAmount() {return showAmount;}
	private boolean showAmountLabelProcurement; public boolean isShowAmountLabelProcurement() {return showAmountLabelProcurement;}
	private boolean showAmountLabelMaintenance; public boolean isShowAmountLabelMaintenance() {return showAmountLabelMaintenance;}
	
	private boolean showCompanies; public boolean isShowCompanies() {return showCompanies;}
	private boolean showCompanyLabelProcurement; public boolean isShowCompanyLabelProcurement() {return showCompanyLabelProcurement;}
	private boolean showCompanyLabelMaintenance; public boolean isShowCompanyLabelMaintenance() {return showCompanyLabelMaintenance;}
	
	public UiHelperAsset()
	{
		companies = new ArrayList<>();
		reset();
	}
	
	private void reset()
	{
		companies.clear();
		
		showAmount = false;
		showAmountLabelProcurement = false;
		showAmountLabelMaintenance = false;
		
		showCompanies = false;
		showCompanyLabelProcurement = false;
		showCompanyLabelMaintenance = false;
	}

	public void update(REALM realm, RREF rref, EVENT event)
	{
		reset();
		boolean isProcurement = event.getType().getCode().equals(JeeslAomEventType.Code.procurement.toString());
		boolean isMaintenance = event.getType().getCode().equals(JeeslAomEventType.Code.maintenance.toString());
		boolean isRenew = event.getType().getCode().equals(JeeslAomEventType.Code.renew.toString());
		
		showAmount = EjbIdFactory.isSaved(event) && (isProcurement || isMaintenance || isRenew);
		showAmountLabelProcurement = showAmount && isProcurement;
		showAmountLabelMaintenance = showAmount && (isMaintenance || isRenew);
		
		if(EjbIdFactory.isSaved(event) && bCache!=null)
		{
			if(isProcurement)
			{
				showCompanyLabelProcurement = true;
				companies.addAll(bCache.getMapVendor().get(realm).get(rref));
			}
			else if(isMaintenance || isRenew)
			{
				showCompanyLabelMaintenance = true;
				companies.addAll(bCache.getMapMaintainer().get(realm).get(rref));
			}
			showCompanies = showCompanyLabelProcurement || showCompanyLabelMaintenance; 
		}
		
	}
}