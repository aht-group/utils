package org.jeesl.interfaces.model.module.om;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslOmCompany <REALM extends JeeslAssetRealm<?,?,REALM,?>>
		extends Serializable,EjbSaveable,EjbWithName,EjbWithNonUniqueCode
{
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRealmIdentifier();
	void setRealmIdentifier(long realmIdentifier);
	
	String getUrl();
	void setUrl(String url);
}