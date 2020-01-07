package org.jeesl.factory.ejb.module.asset;

import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAssetManufacturerFactory<MANU extends JeeslAssetManufacturer>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetManufacturerFactory.class);
	
	private final Class<MANU> cManu;
	
    public EjbAssetManufacturerFactory(final Class<MANU> cManu)
    {
        this.cManu = cManu;
    }
	
	public MANU build()
	{
		try
		{
			MANU ejb = cManu.newInstance();
			
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}