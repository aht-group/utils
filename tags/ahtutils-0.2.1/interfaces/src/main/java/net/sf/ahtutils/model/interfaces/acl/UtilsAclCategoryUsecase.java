package net.sf.ahtutils.model.interfaces.acl;

import java.util.List;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsAclCategoryUsecase<L extends UtilsLang,
										 D extends UtilsDescription,
										 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
										 U extends UtilsAclView<L,D,CU,U>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public List<U> getUsecases();
	public void setUsecases(List<U> usecases); 
}