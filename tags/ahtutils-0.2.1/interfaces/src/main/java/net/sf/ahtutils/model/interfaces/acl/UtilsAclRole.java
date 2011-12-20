package net.sf.ahtutils.model.interfaces.acl;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsAclRole<L extends UtilsLang,
						 D extends UtilsDescription, 
						 C extends UtilsAclCategoryProjectRole<L,D,C,R>,
						 R extends UtilsAclRole<L,D,C,R>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "aclRoles";
	
	public C getCategory();
	public void setCategory(C category);
}