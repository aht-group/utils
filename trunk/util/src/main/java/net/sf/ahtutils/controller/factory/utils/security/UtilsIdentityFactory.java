package net.sf.ahtutils.controller.factory.utils.security;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.model.interfaces.idm.UtilsIdentity;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsIdentityFactory <I extends UtilsIdentity<L,D,C,R,V,U,A,USER>,
								   L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								   USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{

	final static Logger logger = LoggerFactory.getLogger(UtilsIdentityFactory.class);

	final Class<I>  clIdentity;
	final Class<L>  clLang;
	final Class<D>  clDescription;
	final Class<C>  clCategory;
	final Class<R>  clRole;
	final Class<V>  clView;
	final Class<U> 	clUsecase;
	final Class<A> 	clAction;
	final Class<USER>  clUser;

	public UtilsIdentityFactory(final Class<I> clIdentity,
								final Class<L> clLang,
								final Class<D> clDescription,
								final Class<C> clCategory,
								final Class<R> clRole,
								final Class<V> clView,
								final Class<U> clUsecase,
								final Class<A> clAction,
								final Class<USER> clUser)
	{
		this.clIdentity=clIdentity;
		this.clLang=clLang;
		this.clDescription=clDescription;
		this.clCategory=clCategory;
		this.clRole=clRole;
		this.clView=clView;
		this.clUsecase=clUsecase;
		this.clAction=clAction;
		this.clUser = clUser;
	} 

	public static <I extends UtilsIdentity<L,D,C,R,V,U,A,USER>,
	   			   L extends UtilsLang,
	   			   D extends UtilsDescription,
	   			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	   			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	   			   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	   			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	   			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	   			USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
	UtilsIdentityFactory<I,L,D,C,R,V,U,A,USER> factory(final Class<I> clIdentity,
													 final Class<L> clLang,
													 final Class<D> clDescription,
													 final Class<C> clCategory,
													 final Class<R> clRole,
													 final Class<V> clView,
													 final Class<U> clUsecase,
													 final Class<A> clAction,
													 final Class<USER> clUser)
	{
		return new UtilsIdentityFactory<I,L,D,C,R,V,U,A,USER>(clIdentity,clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser);
	}

	public I create(UtilsSecurityFacade fSecurity, USER user) throws InstantiationException, IllegalAccessException
	{		
		I identity = clIdentity.newInstance();
		identity.setUser(user);
		
//		for(UC uc : fAcl.findUsecasesForRoles(roles))
//		{
//			identity.allowUsecase(uc);
//		}
		
		for(R r : fSecurity.allRolesForUser(clUser,user))
		{
			identity.allowRole(r);
		}
		
		for(V v : fSecurity.allViewsForUser(clUser,user))
		{
			identity.allowView(v);
		}
		
		return identity;
	}
}