package net.sf.ahtutils.model.interfaces.acl;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface UtilsAclRole<L extends JeeslLang,
						 D extends JeeslDescription, 
						 C extends UtilsAclCategoryProjectRole<L,D,C,R>,
						 R extends UtilsAclRole<L,D,C,R>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "aclRoles";
	
	public C getCategory();
	public void setCategory(C category);
}