package net.sf.ahtutils.model.interfaces.security;

import java.util.List;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsSecurityRole<L extends UtilsLang,
						 		   D extends UtilsDescription, 
						 		   C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
						 		   R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
						 		   V extends UtilsSecurityView<L,D,C,R,V,U,A>,
						 		   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
						 		   A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>,UtilsSecurityWithViews<L,D,C,R,V,U,A>,UtilsSecurityWithActions<L,D,C,R,V,U,A>
{
	public static final String extractId = "securityRoles";
	
	public C getCategory();
	public void setCategory(C category);
	
	public List<U> getUsecases();
	public void setUsecases(List<U> usecases);
}