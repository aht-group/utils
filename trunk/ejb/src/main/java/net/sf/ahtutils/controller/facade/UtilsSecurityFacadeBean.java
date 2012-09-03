package net.sf.ahtutils.controller.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityWithCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsStaff;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public class UtilsSecurityFacadeBean extends UtilsFacadeBean implements UtilsSecurityFacade
{	
	public UtilsSecurityFacadeBean(EntityManager em)
	{
		super(em);
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A>, R extends UtilsSecurityRole<L, D, C, R, V, U, A>, V extends UtilsSecurityView<L, D, C, R, V, U, A>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A>, A extends UtilsSecurityAction<L, D, C, R, V, U, A>, US extends UtilsUser<L, D, C, R, V, U, A>>
	R load(Class<R> cRole, R role)
	{
		role = em.find(cRole, role.getId());
		
		return role;
	}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A>, R extends UtilsSecurityRole<L, D, C, R, V, U, A>, V extends UtilsSecurityView<L, D, C, R, V, U, A>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A>, A extends UtilsSecurityAction<L, D, C, R, V, U, A>,US extends UtilsUser<L,D,C,R,V,U,A>>
		List<V>	allViewsForUser(Class<US> clUser, US user)
	{
		user = em.find(clUser, user.getId());
		Map<Long,V> views = new HashMap<Long,V>();
		for(R r : user.getRoles())
		{
			for(V v : r.getViews())
			{
				views.put(v.getId(), v);
			}
		}
		
		List<V> result = new ArrayList<V>();
		for(V v : views.values()){result.add(v);}
		return result;
	}
	
	@Override
	public <WC extends UtilsSecurityWithCategory<L, D, C, R, V, U, A>, L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A>, R extends UtilsSecurityRole<L, D, C, R, V, U, A>, V extends UtilsSecurityView<L, D, C, R, V, U, A>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A>, A extends UtilsSecurityAction<L, D, C, R, V, U, A>, US extends UtilsUser<L, D, C, R, V, U, A>>
	List<WC> allForCategory(Class<WC> clWc, Class<C> clC, String code) throws UtilsNotFoundException
	{
		C category = this.fByCode(clC, code);
		return this.allForParent(clWc, "category", category);
	}	
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A>, R extends UtilsSecurityRole<L, D, C, R, V, U, A>, V extends UtilsSecurityView<L, D, C, R, V, U, A>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A>, A extends UtilsSecurityAction<L, D, C, R, V, U, A>, S extends UtilsStaff<L, D, C, R, V, U, A, P, E>, P extends EjbWithId, E extends EjbWithId> List<S>
		fStaff(Class<S> clStaff, P pool)
	{return allForParent(clStaff, "pool", pool);}
}
