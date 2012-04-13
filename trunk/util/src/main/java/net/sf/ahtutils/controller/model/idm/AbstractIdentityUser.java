package net.sf.ahtutils.controller.model.idm;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public class AbstractIdentityUser <L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A>,
								   US extends EjbWithId>
{
	public static final long serialVersionUID=1;

	private Map<String,Boolean> mapUsecases;
	private Map<String,Boolean> mapViews;
	
	public AbstractIdentityUser()
	{
		mapUsecases = new Hashtable<String,Boolean>();
		mapViews = new Hashtable<String,Boolean>();
	}
	
	public void allowUsecase(U usecase) {mapUsecases.put(usecase.getCode(), true);}
	public void allowView(V view) {mapViews.put(view.getCode(), true);}
	
	public boolean hasUsecase(String code)
	{
		if(mapUsecases.containsKey(code))
		{
			return mapUsecases.get(code);
		}
		return false;
	}
	
	public boolean hasView(String code)
	{
		if(mapViews.containsKey(code))
		{
			return mapViews.get(code);
		}
		return false;
	}
	
	public int sizeAllowedUsecases() {return mapUsecases.size();}
	public int sizeAllowedViews() {return mapViews.size();}
	
	public Map<String, Boolean> getMapUsecases() {return mapUsecases;}
	public Map<String, Boolean> getMapViews() {return mapViews;}
}