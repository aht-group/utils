package org.jeesl.factory.ejb.io.ssi.docker;

import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiHost;
import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.io.ssi.docker.JeeslIoSsiContainer;

public class EjbIoSsiContainerFactory <SYSTEM extends JeeslIoSsiSystem<?,?>,
										INSTANCE extends JeeslIoSsiContainer<SYSTEM,HOST>,
										HOST extends JeeslIoSsiHost<?,?,SYSTEM>>
{
	private final Class<INSTANCE> cInstance;

	public EjbIoSsiContainerFactory(final Class<INSTANCE> cAttriubte)
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