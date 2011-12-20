package net.sf.ahtutils.controller.model.idm;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public class AbstractIdentityUser <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclView<L,D,CU,U>>
{
	public static final long serialVersionUID=1;

	private Map<String,Boolean> mapUsecases;
	
	public AbstractIdentityUser()
	{
		mapUsecases = new Hashtable<String,Boolean>();
	}
	
	public void allowUsecase(U usecase)
	{
		mapUsecases.put(usecase.getCode(), true);
	}
	
	public boolean hasUsecase(String code)
	{
		if(mapUsecases.containsKey(code))
		{
			return mapUsecases.get(code);
		}
		return false;
	}
	
	public int sizeUsecases()
	{
		return mapUsecases.size();
	}
	

}