package net.sf.ahtutils.model.interfaces.security;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsSecurityUsecase<L extends UtilsLang,
 									  D extends UtilsDescription, 
 									  C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
 									  R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
 									  V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
 									  U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
 									  A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
 									  USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
			extends EjbWithCode,
					EjbWithLang<L>,
					EjbWithDescription<D>,
					UtilsSecurityWithViews<L,D,C,R,V,U,A,USER>,
					UtilsSecurityWithActions<L,D,C,R,V,U,A,USER>
{
	public static final String extractId = "securityUsecases";
	
	public C getCategory();
	public void setCategory(C category);
}