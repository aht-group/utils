package org.jeesl.factory.ejb.module.asset;

import java.util.List;
import java.util.UUID;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomView;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAssetLevelFactory<REALM extends JeeslTenantRealm<?,?,REALM,?>,
									ALEVEL extends JeeslAomView<?,?,REALM,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetLevelFactory.class);
	
	private final Class<ALEVEL> cLevel;
	
    public EjbAssetLevelFactory(final Class<ALEVEL> cLevel)
    {
        this.cLevel = cLevel;
    }
	
	public <RREF extends EjbWithId> ALEVEL build(REALM realm, RREF rref, List<ALEVEL> list)
	{
		try
		{
			ALEVEL ejb = cLevel.newInstance();
			ejb.setRealm(realm);
			ejb.setRref(rref.getId());
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setVisible(true);
			EjbPositionFactory.next(ejb,list);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}