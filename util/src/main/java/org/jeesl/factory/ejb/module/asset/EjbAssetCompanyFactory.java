package org.jeesl.factory.ejb.module.asset;

import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbAssetCompanyFactory<REALM extends JeeslAomRealm<?,?,REALM,?>,
									COMPANY extends JeeslAomCompany<REALM,SCOPE>,
									SCOPE extends JeeslAomScope<?,?,SCOPE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetCompanyFactory.class);
	
	private final Class<COMPANY> cCompany;
	
    public EjbAssetCompanyFactory(final Class<COMPANY> cCompany)
    {
        this.cCompany = cCompany;
    }
	
	public <RREF extends EjbWithId> COMPANY build(REALM realm, RREF rref)
	{
		try
		{
			COMPANY ejb = cCompany.newInstance();
			ejb.setRealm(realm);
			ejb.setRealmIdentifier(rref.getId());
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}