package org.jeesl.factory.ejb.io.ssi.data;

import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.io.ssi.data.JeeslIoSsiMapping;

public class EjbIoSsiMappingFactory <SYSTEM extends JeeslIoSsiSystem<?,?>,
										MAPPING extends JeeslIoSsiMapping<SYSTEM,ENTITY>,
										ENTITY extends JeeslRevisionEntity<?,?,?,?,?,?>>
{
	private final Class<MAPPING> cMapping;

	public EjbIoSsiMappingFactory(final Class<MAPPING> cMapping)
	{
        this.cMapping = cMapping;
	}
	
	public MAPPING build(SYSTEM system)
	{
		MAPPING ejb = null;
		try
		{
			ejb = cMapping.newInstance();
			ejb.setSystem(system);
	       
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}