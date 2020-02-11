package org.jeesl.factory.ejb.module.asset;

import java.util.UUID;

import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbAssetFactory<REALM extends JeeslAomRealm<?,?,REALM,?>,
							COMPANY extends JeeslAomCompany<REALM,SCOPE>,
							SCOPE extends JeeslAomScope<?,?,SCOPE,?>,
							ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,STATUS,TYPE>,
							STATUS extends JeeslAomStatus<?,?,STATUS,?>,
							TYPE extends JeeslAomType<?,?,REALM,TYPE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetFactory.class);
	
	private final AssetFactoryBuilder<?,?,?,COMPANY,SCOPE,ASSET,STATUS,TYPE,?,?,?> fbAsset;
	
    public EjbAssetFactory(final AssetFactoryBuilder<?,?,?,COMPANY,SCOPE,ASSET,STATUS,TYPE,?,?,?> fbAsset)
    {
        this.fbAsset = fbAsset;
    }
	
	public <RREF extends EjbWithId> ASSET build(REALM realm, RREF ref, ASSET parent, STATUS status, TYPE type1)
	{
		try
		{
			ASSET ejb = fbAsset.getClassAsset().newInstance();
			ejb.setRealm(realm);
			ejb.setRealmIdentifier(ref.getId());
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setParent(parent);
			ejb.setStatus(status);
			ejb.setType1(type1);
			ejb.setName("");
			ejb.setRemark("");
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
	
	public void converter(JeeslFacade facade, ASSET asset)
	{
		if(asset.getManufacturer()!=null) {asset.setManufacturer(facade.find(fbAsset.getClassCompany(),asset.getManufacturer()));}
	}
}