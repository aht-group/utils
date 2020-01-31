package org.jeesl.factory.ejb.module.asset;

import org.jeesl.interfaces.model.module.asset.JeeslAssetCompany;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbAssetCompanyFactory<REALM extends JeeslAssetRealm<?,?,REALM,?>,
									COMPANY extends JeeslAssetCompany<REALM,SCOPE>,
									SCOPE extends JeeslAssetScope<?,?,SCOPE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetCompanyFactory.class);
	
	private final Class<COMPANY> cCompany;
	
    public EjbAssetCompanyFactory(final Class<COMPANY> cManu)
    {
        this.cCompany = cManu;
    }
	
	public <RREF extends EjbWithId> COMPANY build(REALM realm, RREF rRef)
	{
		try
		{
			COMPANY ejb = cCompany.newInstance();
			ejb.setRealm(realm);
			ejb.setRealmIdentifier(rRef.getId());
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}