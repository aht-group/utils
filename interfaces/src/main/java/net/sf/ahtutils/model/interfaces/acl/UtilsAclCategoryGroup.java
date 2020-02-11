package net.sf.ahtutils.model.interfaces.acl;

import java.util.List;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsAclCategoryGroup<L extends JeeslLang,
									  D extends JeeslDescription,
									  CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
									  CR extends UtilsAclCategoryGroup<L,D,CU,CR,U,R>,
									  U extends UtilsAclView<L,D,CU,U>,
									  R extends UtilsAclGroup<L,D,CU,CR,U,R>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public List<R> getRoles();
	public void setRoles(List<R> roles);
}