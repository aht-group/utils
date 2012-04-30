package net.sf.ahtutils.model.interfaces.with;

import java.util.List;

import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsAuditTrail;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface EjbWithAuditTrails <L extends UtilsLang,
									D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A>,
									US extends UtilsUser<L,D,C,R,V,U,A>,
									T extends UtilsAuditTrail<L,D,C,R,V,U,A,US,TY>,
									TY extends UtilsStatus<L>>
{
	List<T> getAuditTrails();
	void setAuditTrails(List<T> auditTrails);
}
