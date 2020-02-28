package net.sf.ahtutils.model.interfaces.acl;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface UtilsAclView<L extends JeeslLang,
								 D extends JeeslDescription, 
								 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
								 U extends UtilsAclView<L,D,CU,U>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "aclViews";
	
	public CU getCategory();
	public void setCategory(CU category);
}