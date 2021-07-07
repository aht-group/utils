package org.jeesl.factory.ejb.io.ssi.core;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiHost;
import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiSystem;

public class EjbIoSsiHostFactory <SYSTEM extends JeeslIoSsiSystem<?,?>,
									HOST extends JeeslIoSsiHost<?,?,SYSTEM>>
{
	private final Class<SYSTEM> cSystem;
	private final Class<HOST> cHost;

	public EjbIoSsiHostFactory(final Class<SYSTEM> cSystem,
								final Class<HOST> cHost)
	{
		this.cSystem = cSystem;
        this.cHost = cHost;
	}
		
	public HOST build(SYSTEM system, String code, List<HOST> list)
	{
		HOST ejb = null;
		try
		{
			ejb = cHost.newInstance();
			ejb.setSystem(system);
	        ejb.setCode(code);
	        ejb.setPosition(0);
	        EjbPositionFactory.calcNext(ejb,list);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public void converter(JeeslFacade facade, HOST host)
	{
		if(host.getSystem()!=null) {host.setSystem(facade.find(cSystem,host.getSystem()));}
	}
}