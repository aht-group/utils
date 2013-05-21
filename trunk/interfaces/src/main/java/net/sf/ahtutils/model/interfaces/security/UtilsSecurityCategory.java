package net.sf.ahtutils.model.interfaces.security;

import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithType;

public interface UtilsSecurityCategory<L extends UtilsLang,
									   D extends UtilsDescription,
									   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
									   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
									   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
									   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
									   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
									   USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
			extends EjbWithId,EjbWithCode,EjbWithType,EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Type {role,view,usecase}
	
//	public List<R> getRoles();
//	public void setRoles(List<R> roles);
}