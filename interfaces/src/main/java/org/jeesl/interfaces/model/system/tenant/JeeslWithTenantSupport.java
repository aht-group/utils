package org.jeesl.interfaces.model.system.tenant;

public interface JeeslWithTenantSupport<REALM extends JeeslTenantRealm<?,?,REALM,?>>
{	
	public enum Attributes{realm,rref}
	
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRref();
	void setRref(long rref);
}