package net.sf.ahtutils.model.interfaces.acl;

import java.util.List;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsAclRole<L extends UtilsLang,
						 D extends UtilsDescription, 
						 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
						 CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
						 U extends UtilsAclUsecase<L,D,CU,U>,
						 R extends UtilsAclRole<L,D,CU,CR,U,R>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public CR getCategory();
	public void setCategory(CR category);
	
	public List<R> getRoles();
	public void setRoles(List<R> roles);
	
	public List<U> getUsecases() ;
	public void setUsecases(List<U> usecases);
}