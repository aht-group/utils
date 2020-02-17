package org.jeesl.factory.ejb.module.asset;

import java.util.Date;

import org.jeesl.controller.handler.NullNumberBinder;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventStatus;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAssetEventFactory<COMPANY extends JeeslAomCompany<?,?>,
								ASSET extends JeeslAomAsset<?,ASSET,COMPANY,?,?>,
								EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS,USER>,
								ETYPE extends JeeslAomEventType<?,?,ETYPE,?>,
								ESTATUS extends JeeslAomEventStatus<?,?,ESTATUS,?>,
								USER extends JeeslSimpleUser>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetEventFactory.class);
	
	private final AssetFactoryBuilder<?,?,?,COMPANY,?,ASSET,?,?,EVENT,ETYPE,ESTATUS,USER> fbAsset;
	
    public EjbAssetEventFactory(final AssetFactoryBuilder<?,?,?,COMPANY,?,ASSET,?,?,EVENT,ETYPE,ESTATUS,USER> fbAsset)
    {
        this.fbAsset = fbAsset;
    }
	
	public EVENT build(ASSET asset, ETYPE type)
	{
		try
		{
			EVENT ejb = fbAsset.getClassEvent().newInstance();
			ejb.getAssets().add(asset);
			ejb.setRecord(new Date());
			ejb.setName("");
			ejb.setRemark("");
			ejb.setType(type);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
	
	public void converter(JeeslFacade facade, EVENT event)
	{
		event.setType(facade.find(fbAsset.getClassEventType(),event.getType()));
		event.setStatus(facade.find(fbAsset.getClassEventStatus(),event.getStatus()));
		if(event.getCompany()!=null) {event.setCompany(facade.find(fbAsset.getClassCompany(),event.getCompany()));}
	}
	
	public void ejb2nnb(EVENT event, NullNumberBinder nnb)
	{
		nnb.doubleToA(event.getAmount());
	}
	public void nnb2ejb(EVENT event, NullNumberBinder nnb)
	{
		event.setAmount(nnb.aToDouble());
	}
}