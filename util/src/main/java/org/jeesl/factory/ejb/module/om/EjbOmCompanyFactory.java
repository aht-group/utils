package org.jeesl.factory.ejb.module.om;

import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.om.JeeslOmCompany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbOmCompanyFactory<REALM extends JeeslAssetRealm<?,?,REALM,?>,
								COMPANY extends JeeslOmCompany<REALM>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbOmCompanyFactory.class);
	
	private final Class<COMPANY> cCompany;
	
    public EjbOmCompanyFactory(final Class<COMPANY> cCompany)
    {
        this.cCompany = cCompany;
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
	
	public void converter(UtilsFacade facade, COMPANY company)
	{
		
	}
}