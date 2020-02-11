package org.jeesl.interfaces.model.module.aom.company;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslAomCompany <REALM extends JeeslAomRealm<?,?,REALM,?>,
									SCOPE extends JeeslAomScope<?,?,SCOPE,?>>
		extends Serializable,EjbSaveable,EjbWithName,EjbWithNonUniqueCode
{
	public enum Attributes{realm,realmIdentifier,scopes}
	
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRealmIdentifier();
	void setRealmIdentifier(long realmIdentifier);
	
	String getUrl();
	void setUrl(String url);
	
	List<SCOPE> getScopes();
	void setScopes(List<SCOPE> scopes);
}