package org.jeesl.factory.ejb.io.fr;

import org.jeesl.interfaces.model.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.io.fr.JeeslFileStorageEngine;
import org.jeesl.interfaces.model.io.fr.JeeslFileStorageType;
import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIoFrStorageFactory<SYSTEM extends JeeslIoSsiSystem<?,?>,
									STORAGE extends JeeslFileStorage<?,?,SYSTEM,STYPE,SENGINE>,
									STYPE extends JeeslFileStorageType<?,?,STYPE,?>,
									SENGINE extends JeeslFileStorageEngine<?,?,SENGINE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoFrStorageFactory.class);
	
	private final Class<STORAGE> cStorage;
    
	public EjbIoFrStorageFactory(final Class<STORAGE> cStorage)
	{       
        this.cStorage = cStorage;
	}
	
	public STORAGE build(STYPE type)
	{
		STORAGE ejb = null;
		try
		{
			 ejb = cStorage.newInstance();
			 ejb.setType(type);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}