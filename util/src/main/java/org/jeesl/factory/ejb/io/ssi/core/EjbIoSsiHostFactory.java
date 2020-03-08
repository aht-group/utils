package org.jeesl.factory.ejb.io.ssi.core;

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
		
	public HOST build(SYSTEM system, String code)
	{
		HOST ejb = null;
		try
		{
			ejb = cHost.newInstance();
//			ejb.sets
	        ejb.setCode(code);
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