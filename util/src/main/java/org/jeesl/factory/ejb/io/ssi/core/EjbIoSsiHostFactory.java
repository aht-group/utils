package org.jeesl.factory.ejb.io.ssi.core;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.io.ssi.core.JeeslIoSsiHost;
import org.jeesl.interfaces.model.system.io.ssi.core.JeeslIoSsiSystem;

public class EjbIoSsiHostFactory <SYSTEM extends JeeslIoSsiSystem<?,?>,
									HOST extends JeeslIoSsiHost<?,?>>
{
	private final Class<HOST> cHost;

	public EjbIoSsiHostFactory(final Class<HOST> cHost)
	{
        this.cHost = cHost;
	}
		
	public HOST build(SYSTEM system, String code)
	{
		HOST ejb = null;
		try
		{
			ejb = cHost.newInstance();
	        ejb.setCode(code);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public void converter(JeeslFacade facade, HOST host)
	{
		
	}
}