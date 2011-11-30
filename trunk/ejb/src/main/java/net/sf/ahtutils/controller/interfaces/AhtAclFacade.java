package net.sf.ahtutils.controller.interfaces;

import java.util.List;
import java.util.Set;

import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.controller.interfaces.AhtUtilsFacade;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.AclQuery;
import net.sf.ahtutils.xml.access.ProjectRoleCategory;
import net.sf.ahtutils.xml.access.Role;
import net.sf.ahtutils.xml.access.RoleCategory;
import net.sf.ahtutils.xml.access.UsecaseCategory;

public interface AhtAclFacade extends AhtUtilsFacade
{
	<L extends UtilsLang,
	 D extends UtilsDescription,
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 U extends UtilsAclUsecase<L,D,CU,U>>
	CU fUsecaseCategoryByCode(Class<CU> type, String code) throws AhtUtilsNotFoundException;
	
	<L extends UtilsLang,
	 D extends UtilsDescription,
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
	 U extends UtilsAclUsecase<L,D,CU,U>,
	 R extends UtilsAclRole<L,D,CU,CR,U,R>>
	CR fRoleCategoryByCode(Class<CR> type, String code) throws AhtUtilsNotFoundException;
	
	<L extends UtilsLang,
	 D extends UtilsDescription,
	 C extends UtilsAclCategoryProjectRole<L,D,C,R>,
	 R extends UtilsAclProjectRole<L,D,C,R>>
	C fProjectRoleCategoryByCode(Class<C> type, String code) throws AhtUtilsNotFoundException;
	
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 U extends UtilsAclUsecase<L,D,CU,U>>
	U fUsecaseByCode(Class<U> type, String code) throws AhtUtilsNotFoundException;
	
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
	 U extends UtilsAclUsecase<L,D,CU,U>,
	 R extends UtilsAclRole<L,D,CU,CR,U,R>>
	R fRoleByCode(Class<R> type, String code) throws AhtUtilsNotFoundException;
	
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
	 U extends UtilsAclUsecase<L,D,CU,U>,
	 R extends UtilsAclRole<L,D,CU,CR,U,R>>
	List<R> fAclRoles(Class<R> type, List<Role> lRoles) throws AhtUtilsNotFoundException;
	
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
	 U extends UtilsAclUsecase<L,D,CU,U>,
	 R extends UtilsAclRole<L,D,CU,CR,U,R>>
	Set<String> findUsecaseCodesForRoles(List<R> roles);
	
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 C extends UtilsAclCategoryProjectRole<L,D,C,R>,
	 R extends UtilsAclProjectRole<L,D,C,R>>
	R fProjectRoleByCode(Class<R> type, String code) throws AhtUtilsNotFoundException;
	
	
	//ALL
	<T extends Object> List<T> all(Class<T> type);
	/*
	<L extends UtilsLang,
	 D extends UtilsDescription,
	 C extends UtilsAclCategoryProjectRole<L,D,C,R>,
	 R extends UtilsAclProjectRole<L,D,C,R>>
	List<C> allAclProjectRoleCategory2(Class<C> type);
	
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 C extends UtilsAclCategoryProjectRole<L,D,C,R>,
	 R extends UtilsAclProjectRole<L,D,C,R>>
	List<R> allAclProjectRole2(Class<R> type);
	
	<L extends UtilsLang,
	 D extends UtilsDescription,
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 U extends UtilsAclUsecase<L,D,CU,U>>
	List<CU> allAclUsecaseCategory(Class<CU> type);
	
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 U extends UtilsAclUsecase<L,D,CU,U>>
	List<U> allAclUsecase2(Class<U> type);
	
	<L extends UtilsLang,
	 D extends UtilsDescription,
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
	 U extends UtilsAclUsecase<L,D,CU,U>,
	 R extends UtilsAclRole<L,D,CU,CR,U,R>>
	List<CR> allAclRoleCategory(Class<CR> type);
	
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
	 U extends UtilsAclUsecase<L,D,CU,U>,
	 R extends UtilsAclRole<L,D,CU,CR,U,R>>
	List<R> allAclRole2(Class<R> type); */
	
	//***************************   XML Factory   *************************************
	
	<L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclUsecase<L,D,CU,U>>
	UsecaseCategory getUsecaseCategory(CU aclUsecaseCategory, AclQuery qAcl);
	
	<L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,U extends UtilsAclUsecase<L,D,CU,U>,R extends UtilsAclRole<L,D,CU,CR,U,R>>
	RoleCategory getRoleCategory(CR aclRoleCategory, AclQuery qAcl);

	<L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclProjectRole<L,D,C,R>>
	ProjectRoleCategory getProjectRoleCategory(C aclProjectRoleCategory, AclQuery qAcl);
	
	List<UsecaseCategory> getUsecaseCategories(AclQuery qAcl);
	List<RoleCategory> getRoleCategories(AclQuery qAcl);
	List<ProjectRoleCategory> getProjectRoleCategories(AclQuery qAcl);
}
