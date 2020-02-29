package org.jeesl.factory.ejb.module.asset;

import java.util.List;
import java.util.UUID;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.aom.asset.JeeslAomLevel;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAssetLevelFactory<REALM extends JeeslMcsRealm<?,?,REALM,?>,
									ALEVEL extends JeeslAomLevel<?,?,REALM,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetLevelFactory.class);
	
	private final Class<ALEVEL> cLevel;
	
    public EjbAssetLevelFactory(final Class<ALEVEL> cLevel)
    {
        this.cLevel = cLevel;
    }
	
	public <RREF extends EjbWithId> ALEVEL build(REALM realm, RREF ref, List<ALEVEL> list)
	{
		try
		{
			ALEVEL ejb = cLevel.newInstance();
			ejb.setRealm(realm);
			ejb.setRealmIdentifier(ref.getId());
			ejb.setCode(UUID.randomUUID().toString());
			EjbPositionFactory.next(ejb,list);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}