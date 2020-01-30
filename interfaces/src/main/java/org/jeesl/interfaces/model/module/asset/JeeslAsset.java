package org.jeesl.interfaces.model.module.asset;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.with.EjbWithRemark;
import org.jeesl.interfaces.model.with.status.JeeslWithStatus;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslAsset <REALM extends JeeslAssetRealm<?,?,REALM,?>,
							ASSET extends JeeslAsset<REALM,ASSET,MANU,STATUS,TYPE>,
							MANU extends JeeslAssetManufacturer<REALM>,
							STATUS extends JeeslAssetStatus<?,?,STATUS,?>,
							TYPE extends JeeslAssetType<?,?,REALM,TYPE,?>>
			extends Serializable,EjbSaveable,
					EjbWithPosition,EjbWithParentAttributeResolver,
					EjbWithNonUniqueCode,EjbWithName,EjbWithRemark,
					JeeslWithStatus<STATUS>
					
{
	public enum Attributes{realm,realmIdentifier,parent}
	
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRealmIdentifier();
	void setRealmIdentifier(long realmIdentifier);
	
	ASSET getParent();
	void setParent(ASSET parent);
	
	
	
	TYPE getType1();
	void setType1(TYPE type1);
	
	List<ASSET> getAssets();
	void setAssets(List<ASSET> assets);
}