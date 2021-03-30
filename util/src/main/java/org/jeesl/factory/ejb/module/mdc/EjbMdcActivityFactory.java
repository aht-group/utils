package org.jeesl.factory.ejb.module.mdc;

import org.jeesl.factory.builder.module.MdcFactoryBuilder;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcActivity;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcScope;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcStatus;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbMdcActivityFactory<R extends JeeslTenantRealm<?,?,R,?>,
									ACTIVITY extends JeeslMdcActivity<R,SCOPE,STATUS,AS>,
									SCOPE extends JeeslMdcScope<?,?,R,SCOPE,?>,
									STATUS extends JeeslMdcStatus<?,?,STATUS,?>,
									AS extends JeeslAttributeSet<?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbMdcActivityFactory.class);

	private final MdcFactoryBuilder<?,?,?,R,ACTIVITY,SCOPE,STATUS,AS> fbMdc;

	public EjbMdcActivityFactory(MdcFactoryBuilder<?,?,?,R,ACTIVITY,SCOPE,STATUS,AS> fbMdc)
	{
		this.fbMdc = fbMdc;
	}

	public <RREF extends EjbWithId> ACTIVITY build(R realm, RREF rref)
	{
		ACTIVITY ejb = null;
		try
		{
			ejb = fbMdc.getClassActivity().newInstance();
			ejb.setRealm(realm);
			ejb.setRref(rref.getId());
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
	
	public  void converter(JeeslFacade facade, ACTIVITY ejb)
	{
		if(ejb.getScope()!=null) {ejb.setScope(facade.find(fbMdc.getClassScope(),ejb.getScope()));}
		if(ejb.getStatus()!=null) {ejb.setStatus(facade.find(fbMdc.getClassStatus(),ejb.getStatus()));}
		if(ejb.getAttributeSet()!=null) {ejb.setAttributeSet(facade.find(fbMdc.getClassAttributeSet(),ejb.getAttributeSet()));}
	}
}