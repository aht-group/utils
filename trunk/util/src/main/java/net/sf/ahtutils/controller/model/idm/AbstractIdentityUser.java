package net.sf.ahtutils.controller.model.idm;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public class AbstractIdentityUser <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclUsecase<L,D,CU,U>>
{
	public static final long serialVersionUID=1;

	protected Map<String,Boolean> mapAllowedUsecases;
	
	public AbstractIdentityUser()
	{
		mapAllowedUsecases = new Hashtable<String,Boolean>();
	}
	
	public void allowUsecase(U usecase)
	{
		mapAllowedUsecases.put(usecase.getCode(), true);
	}
	
	public boolean hasUsecase(String code)
	{
		if(mapAllowedUsecases.containsKey(code))
		{
			return mapAllowedUsecases.get(code);
		}
		return false;
	}
	

}