package net.sf.ahtutils.controller.interfaces;

import java.util.List;
import java.util.Set;

import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.AclQuery;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.ProjectRoleCategory;
import net.sf.ahtutils.xml.access.Role;
import net.sf.ahtutils.xml.access.UsecaseCategory;

public interface AhtAclFacade extends AhtUtilsFacade
{	
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
	 U extends UtilsAclUsecase<L,D,CU,U>,
	 R extends UtilsAclGroup<L,D,CU,CR,U,R>>
	List<R> fAclRoles(Class<R> type, List<Role> lRoles) throws AhtUtilsNotFoundException;
	
	@Deprecated// ERP-275
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
	 U extends UtilsAclUsecase<L,D,CU,U>,
	 R extends UtilsAclGroup<L,D,CU,CR,U,R>>
	Set<String> findUsecaseCodesForRoles(List<R> roles);
	
	<L extends UtilsLang,
	 D extends UtilsDescription, 
	 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
	 CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
	 U extends UtilsAclUsecase<L,D,CU,U>,
	 R extends UtilsAclGroup<L,D,CU,CR,U,R>>
	Set<U> findUsecasesForRoles(List<R> roles);
		
	<T extends Object> List<T> all(Class<T> type);
	
	//***************************   XML Factory   *************************************
	
	<L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclUsecase<L,D,CU,U>>
	UsecaseCategory getUsecaseCategory(CU aclUsecaseCategory, AclQuery qAcl);
	
	<L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,U extends UtilsAclUsecase<L,D,CU,U>,R extends UtilsAclGroup<L,D,CU,CR,U,R>>
	Category getRoleCategory(CR aclRoleCategory, AclQuery qAcl);

	<L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclProjectRole<L,D,C,R>>
	ProjectRoleCategory getProjectRoleCategory(C aclProjectRoleCategory, AclQuery qAcl);
	
	List<UsecaseCategory> getUsecaseCategories(AclQuery qAcl);
	List<Category> getRoleCategories(AclQuery qAcl);
	List<ProjectRoleCategory> getProjectRoleCategories(AclQuery qAcl);
}
