package net.sf.ahtutils.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
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

public interface UtilsSecurityFacade extends UtilsFacade
{	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		R load(Class<R> cRole, R role);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		V load(Class<V> cView, V view);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<V> allViewsForUser(Class<USER> clUser, USER user);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<R> allRolesForUser(Class<USER> clUser, USER user);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<A> allActionsForUser(Class<USER> clUser, USER user);
	
	<WC extends UtilsSecurityWithCategory<L,D,C,R,V,U,A,USER>,
	L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<WC> allForCategory(Class<WC> clWc, Class<C> clC, String catCode) throws UtilsNotFoundException;
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		void grantRole(Class<USER> clUser, Class<R> clRole, USER user, R role, boolean grant);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		boolean hasRole(Class<USER> clUser, Class<R> clRole, USER user, R role);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	S extends UtilsStaffPool<L,D,C,R,V,U,A,P,E,USER>,
	P extends EjbWithId,
	E extends EjbWithId,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<S> fStaff(Class<S> clStaff, P pool);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	S extends UtilsStaff<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<S> fStaff(Class<S> clStaff, R role);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	S extends UtilsStaff<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<S> fStaff(Class<S> clStaff, USER user);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	S extends UtilsStaff<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		List<S> fStaff(Class<S> clStaff, USER user, R role);
}
