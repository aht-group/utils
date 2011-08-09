package net.sf.ahtutils.model.interfaces.acl;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsAclUsecase<L extends UtilsLang,
								 D extends UtilsDescription, 
								 C extends UtilsAclCategoryUsecase<L,D,UtilsAclUsecase<L,D,C>>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public C getCategory();
	public void setCategory(C category);
}