package org.jeesl.interfaces.model.module.asset;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslAssetManufacturer <REALM extends JeeslAssetRealm<?,?,REALM,?>>
		extends Serializable,EjbSaveable,EjbWithName,EjbWithNonUniqueCode
{
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRealmIdentifier();
	void setRealmIdentifier(long realmIdentifier);
	
	String getUrl();
	void setUrl(String url);
}