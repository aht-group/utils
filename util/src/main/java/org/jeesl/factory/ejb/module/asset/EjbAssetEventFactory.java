package org.jeesl.factory.ejb.module.asset;

import java.util.Date;

import org.jeesl.controller.handler.NullNumberBinder;
import org.jeesl.factory.builder.module.AomFactoryBuilder;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventStatus;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAssetEventFactory<COMPANY extends JeeslAomCompany<?,?>,
								ASSET extends JeeslAomAsset<?,ASSET,COMPANY,?,?>,
								EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS,USER,FRC>,
								ETYPE extends JeeslAomEventType<?,?,ETYPE,?>,
								ESTATUS extends JeeslAomEventStatus<?,?,ESTATUS,?>,
								USER extends JeeslSimpleUser,
								FRC extends JeeslFileContainer<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetEventFactory.class);
	
	private final AomFactoryBuilder<?,?,?,COMPANY,?,ASSET,?,?,?,EVENT,ETYPE,ESTATUS,USER,FRC,?> fbAsset;
	
    public EjbAssetEventFactory(final AomFactoryBuilder<?,?,?,COMPANY,?,ASSET,?,?,?,EVENT,ETYPE,ESTATUS,USER,FRC,?> fbAsset)
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
	
	public EVENT clone(EVENT event)
	{
		try
		{
			EVENT ejb = fbAsset.getClassEvent().newInstance();
			ejb.getAssets().addAll(event.getAssets());
			ejb.setType(event.getType());
			ejb.setStatus(event.getStatus());
			ejb.setRecord(new Date());
			ejb.setName("CLONE "+event.getName());
			ejb.setRemark(event.getRemark());
			ejb.setCompany(event.getCompany());
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