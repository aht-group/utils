package net.sf.ahtutils.controller.factory.ejb.acl;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAclRoleFactory<L extends UtilsLang,
				D extends UtilsDescription, 
				CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
				CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
				U extends UtilsAclUsecase<L,D,CU,U>,
				R extends UtilsAclRole<L,D,CU,CR,U,R>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAclRoleFactory.class);
	
	 final Class<L> clLang;
	 final Class<D> clDescription;
	 final Class<CU> clCategoryUsecase;
	 final Class<CR> clCategoryRole;
	 final Class<U> clUsecase;
	 final Class<R> clRole;
	
    public static <L extends UtilsLang,
					D extends UtilsDescription,
					CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
					CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
					U extends UtilsAclUsecase<L,D,CU,U>,
					R extends UtilsAclRole<L,D,CU,CR,U,R>>
    	EjbAclRoleFactory<L,D,CU,CR,U,R> factory(final Class<L> clLang,final Class<D> clDescription,final Class<CU> clCategoryUsecase,final Class<CR> clCategoryRole,final Class<U> clUsecase,final Class<R> clRole)
    {
        return new EjbAclRoleFactory<L,D,CU,CR,U,R>(clLang,clDescription,clCategoryUsecase,clCategoryRole,clUsecase,clRole);
    }
    
    public EjbAclRoleFactory(final Class<L> clLang,final Class<D> clDescription,Class<CU> clCategoryUsecase,final Class<CR> clCategoryRole, final Class<U> clUsecase,final Class<R> clRole)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.clCategoryUsecase = clCategoryUsecase;
        this.clCategoryRole = clCategoryRole;
        this.clUsecase = clUsecase;
        this.clRole = clRole;
    } 
    
    public R create(CR category, String code)
    {
    	R ejb = null;
    	
    	try
    	{
			ejb = clRole.newInstance();
			ejb.setCode(code);
			ejb.setCategory(category);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}