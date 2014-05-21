package net.sf.ahtutils.interfaces.model.security;

import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsStaff<L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								   USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
								   DOMAIN extends EjbWithId>
			extends EjbWithId
{
	public R getRole();
	public void setRole(R role);
	
	public USER getUser();
	public void setUser(USER user);
}