package net.sf.ahtutils.model.interfaces.acl;

import net.sf.ahtutils.model.interfaces.EjbWithId;

public interface UtilsIdentity<U extends EjbWithId>
{	
	public U getUser();
	public void setUser(U user);
}