package org.jeesl.factory.ejb.module.asset;

import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbAssetTypeFactory<REALM extends JeeslAomRealm<?,?,REALM,?>,
									TYPE extends JeeslAomType<?,?,REALM,TYPE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetTypeFactory.class);
	
	private final Class<TYPE> cType;
	
    public EjbAssetTypeFactory(final Class<TYPE> cType)
    {
        this.cType = cType;
    }
	
	public <RREF extends EjbWithId> TYPE build(REALM realm, RREF ref, TYPE parent, String code)
	{
		try
		{
			TYPE ejb = cType.newInstance();
			ejb.setRealm(realm);
			ejb.setRealmIdentifier(ref.getId());
			ejb.setParent(parent);
			ejb.setCode(code);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
	
	public void converter(TYPE type)
	{
		
	}
}