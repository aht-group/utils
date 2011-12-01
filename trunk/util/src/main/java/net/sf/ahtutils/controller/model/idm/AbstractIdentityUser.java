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

	protected Map<String,String> mapAllowedUsecases;
	
	public AbstractIdentityUser()
	{
		mapAllowedUsecases = new Hashtable<String,String>();
	}
	
	public void allowUsecase(U usecase)
	{
		
	}
	
	public boolean hasUsecase(String usecaseCode)
	{
		return true;
	}
	

}