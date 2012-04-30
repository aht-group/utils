package net.sf.ahtutils.model.interfaces.security;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsStaff<L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A>,
								   P extends EjbWithId,
								   E extends EjbWithId>
			extends EjbWithId
{
	public R getRole();
	public void setRole(R role);
	
	public P getPool();
	public void setPool(P pool);
	
	public E getEntity();
	public void setEntity(E entity);
}