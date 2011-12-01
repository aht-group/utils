package net.sf.ahtutils.model.interfaces.idm;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsIdentity  <L extends UtilsLang,
								 D extends UtilsDescription,
								 CU extends UtilsAclCategoryUsecase<L,D,CU,UC>,
								 UC extends UtilsAclUsecase<L,D,CU,UC>,
								 U extends EjbWithId>
{	
	public U getUser();
	public void setUser(U user);
	
	void allowUsecase(UC usecase);
	
	public boolean hasUsecase(String usecaseCode);
}
