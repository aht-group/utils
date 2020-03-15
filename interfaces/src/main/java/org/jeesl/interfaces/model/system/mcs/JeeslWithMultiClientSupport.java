package org.jeesl.interfaces.model.system.mcs;

public interface JeeslWithMultiClientSupport<REALM extends JeeslMcsRealm<?,?,REALM,?>>
{	
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRref();
	void setRref(long rref);
}