package net.sf.ahtutils.model.interfaces.idm;

import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsIdentity <L extends UtilsLang,
								D extends UtilsDescription,
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A>,
								US extends UtilsUser<L,D,C,R,V,U,A>>
{	
	public US getUser();
	public void setUser(US user);
		
	public boolean hasUsecase(String usecaseCode);
	int sizeUsecases();
	
	public void allowView(V v);
}
