package net.sf.ahtutils.model.interfaces.acl;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsAclProjectRole<L extends UtilsLang,
						 D extends UtilsDescription, 
						 C extends UtilsAclCategoryProjectRole<L,D,C,R>,
						 R extends UtilsAclProjectRole<L,D,C,R>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public C getCategory();
	public void setCategory(C category);
}