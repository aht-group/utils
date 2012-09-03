package net.sf.ahtutils.model.interfaces.idm;

import java.util.List;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsUser <L extends UtilsLang,
							D extends UtilsDescription,
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		extends EjbWithId
{	
	public List<R> getRoles();
	public void setRoles(List<R> roles);
}
