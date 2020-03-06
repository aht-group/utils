package org.jeesl.factory.ejb.system.io.ssi.docker;

import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiHost;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.io.ssi.docker.JeeslIoSsiContainer;

public class EjbIoSsiInstanceFactory <SYSTEM extends JeeslIoSsiSystem,
										INSTANCE extends JeeslIoSsiContainer<SYSTEM,HOST>,
										HOST extends JeeslIoSsiHost<?,?> >
{
	private final Class<INSTANCE> cInstance;

	public EjbIoSsiInstanceFactory(final Class<INSTANCE> cAttriubte)
	{
        this.cInstance = cAttriubte;
	}
	
	public INSTANCE build(SYSTEM system)
	{
		INSTANCE ejb = null;
		try
		{
			ejb = cInstance.newInstance();
			ejb.setSystem(system);
	       
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}