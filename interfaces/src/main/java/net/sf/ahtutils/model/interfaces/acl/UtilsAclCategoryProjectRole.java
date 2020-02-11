package net.sf.ahtutils.model.interfaces.acl;

import java.util.List;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsAclCategoryProjectRole<L extends JeeslLang,
									  D extends JeeslDescription,
									  C extends UtilsAclCategoryProjectRole<L,D,C,R>,
									  R extends UtilsAclRole<L,D,C,R>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public List<R> getRoles();
	public void setRoles(List<R> roles);
}