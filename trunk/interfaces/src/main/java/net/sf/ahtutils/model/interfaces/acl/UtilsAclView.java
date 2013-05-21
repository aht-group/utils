package net.sf.ahtutils.model.interfaces.acl;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsAclView<L extends UtilsLang,
								 D extends UtilsDescription, 
								 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
								 U extends UtilsAclView<L,D,CU,U>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "aclViews";
	
	public CU getCategory();
	public void setCategory(CU category);
}