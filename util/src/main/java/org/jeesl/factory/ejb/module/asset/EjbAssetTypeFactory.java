package org.jeesl.factory.ejb.module.asset;

import org.jeesl.interfaces.model.module.aom.asset.JeeslAomView;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomType;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAssetTypeFactory<REALM extends JeeslMcsRealm<?,?,REALM,?>,
									TYPE extends JeeslAomType<?,?,REALM,TYPE,ALEVEL,?>,
									ALEVEL extends JeeslAomView<?,?,REALM,?>>
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
}