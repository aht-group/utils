package net.sf.ahtutils.controller.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.security.UtilsStaffPool;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityWithCategory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UtilsSecurityFacadeBean extends UtilsFacadeBean implements UtilsSecurityFacade
{	
	public UtilsSecurityFacadeBean(EntityManager em)
	{
		super(em);
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>, R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>, V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>, U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>, A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>, USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		R load(Class<R> cRole, R role)
	{
		role = em.find(cRole, role.getId());
		role.getUsers().size();
		return role;
	}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
		V load(Class<V> cView, V view)
	{
		view = em.find(cView, view.getId());
		view.getActions().size();
		return view;
	}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>, R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>, V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>, U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>, A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<V>	allViewsForUser(Class<USER> clUser, USER user)
	{
		user = em.find(clUser, user.getId());
		Map<Long,V> views = new HashMap<Long,V>();
		for(R r : user.getRoles())
		{
			for(V v : r.getViews())
			{
				views.put(v.getId(), v);
			}
			for(U u : r.getUsecases())
			{
				for(V v : u.getViews())
				{
					views.put(v.getId(), v);
				}
			}
		}
		
		List<V> result = new ArrayList<V>();
		for(V v : views.values()){result.add(v);}
		return result;
	}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
		List<A> allActionsForUser(Class<USER> clUser, USER user)
	{
		user = em.find(clUser, user.getId());
		Map<Long,A> actions = new HashMap<Long,A>();
		for(R r : user.getRoles())
		{
			for(A a : r.getActions())
			{
				actions.put(a.getId(), a);
			}
			for(U u : r.getUsecases())
			{
				for(A a : u.getActions())
				{
					actions.put(a.getId(), a);
				}
			}
		}
		
		List<A> result = new ArrayList<A>();
		for(A a : actions.values()){result.add(a);}
		return result;
	}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>, R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>, V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>, U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>, A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<R>	allRolesForUser(Class<USER> clUser, USER user)
	{
		user = em.find(clUser, user.getId());
		return user.getRoles();
	}
	
	@Override
	public <WC extends UtilsSecurityWithCategory<L,D,C,R,V,U,A,USER>, L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>, R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>, V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>, U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>, A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>, USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<WC> allForCategory(Class<WC> clWc, Class<C> clC, String code) throws UtilsNotFoundException
	{
		C category = this.fByCode(clC, code);
		return this.allForParent(clWc, "category", category);
	}	
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>, R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>, V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>, U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>, A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>, S extends UtilsStaffPool<L,D,C,R,V,U,A,P,E,USER>, P extends EjbWithId, E extends EjbWithId,USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<S>	fStaffPool(Class<S> clStaff, P pool)
	{return allForParent(clStaff, "pool", pool);}
	
	
	// STAFF
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>, DOMAIN extends EjbWithId>
		List<S> fStaff(Class<S> clStaff, USER user)
	{return allForParent(clStaff, "user", user);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>, DOMAIN extends EjbWithId>
		List<S> fStaff(Class<S> clStaff, R role)
	{return allForParent(clStaff, "role", role);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>, DOMAIN extends EjbWithId>
		List<S> fStaff(Class<S> clStaff, DOMAIN domain)
	{return allForParent(clStaff, "domain", domain);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>, DOMAIN extends EjbWithId>
		List<S> fStaff(Class<S> clStaff, USER user, R role)
	{return allForParent(clStaff, "user", user, "role",role);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>, DOMAIN extends EjbWithId>
		List<S> fStaff(Class<S> clStaff, USER user, DOMAIN domain)
	{return allForParent(clStaff, "user", user, "domain",domain);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>, DOMAIN extends EjbWithId>
		List<S> fStaff(Class<S> clStaff, R role, DOMAIN domain)
	{return allForParent(clStaff, "role", role, "domain",domain);}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>, DOMAIN extends EjbWithId>
		S fStaff(Class<S> clStaff, USER user, R role, DOMAIN domain) throws UtilsNotFoundException
	{
		return oneForParents(clStaff,"user",user,"role",role,"domain",domain);
	}
	
	
	// XXX
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
		void grantRole(Class<USER> clUser, Class<R> clRole, USER user, R role, boolean grant)
	{
		logger.trace("grantRole "+grant);
		user = em.find(clUser,user.getId());
		role = em.find(clRole,role.getId());
		if(grant){addRole(clUser,clRole,user, role);}
		else{rmRole(clUser,clRole,user, role);}
		em.merge(user);
	}
	
	private <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
	void addRole(Class<USER> clUser, Class<R> clRole, USER user, R role)
	{
		if(!user.getRoles().contains(role))
		{
			user.getRoles().add(role);
		}
		user = em.merge(user);
	}
	
	private <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
	void rmRole(Class<USER> clUser, Class<R> clRole, USER user, R role)
	{
		if(user.getRoles().contains(role)){user.getRoles().remove(role);}
		if(role.getUsers().contains(user)){role.getUsers().remove(user);}
		user = em.merge(user);
		role = em.merge(role);
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>>
	boolean hasRole(Class<USER> clUser, Class<R> clRole, USER user, R role)
	{
		for(R r: allRolesForUser(clUser, user))
		{
			if(r.getId() == role.getId()){return true;}
		}
		return false;
	}
}
