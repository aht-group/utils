package org.jeesl.factory.ejb.module.asset;

import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbAssetManufacturerFactory<REALM extends JeeslAssetRealm<?,?,REALM,?>,
										MANU extends JeeslAssetManufacturer<REALM>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetManufacturerFactory.class);
	
	private final Class<MANU> cManu;
	
    public EjbAssetManufacturerFactory(final Class<MANU> cManu)
    {
        this.cManu = cManu;
    }
	
	public <RREF extends EjbWithId> MANU build(REALM realm, RREF rRef)
	{
		try
		{
			MANU ejb = cManu.newInstance();
			ejb.setRealm(realm);
			ejb.setRealmIdentifier(rRef.getId());
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}