package net.sf.ahtutils.controller.factory.ejb.acl;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAclRoleCategoryFactory <L extends UtilsLang,
										D extends UtilsDescription,
										CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
										CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,
										U extends UtilsAclUsecase<L,D,CU,U>,
										R extends UtilsAclRole<L,D,CU,CR,U,R>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAclRoleCategoryFactory.class);
	
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
    	EjbAclRoleCategoryFactory<L,D,CU,CR,U,R> factory(final Class<L> clLang,final Class<D> clDescription,final Class<CU> clCategoryUsecase,final Class<CR> clCategoryRole,final Class<U> clUsecase,final Class<R> clRole)
    {
        return new EjbAclRoleCategoryFactory<L,D,CU,CR,U,R>(clLang,clDescription,clCategoryUsecase,clCategoryRole,clUsecase,clRole);
    }
    
    public EjbAclRoleCategoryFactory(final Class<L> clLang,final Class<D> clDescription,Class<CU> clCategoryUsecase,final Class<CR> clCategoryRole, final Class<U> clUsecase,final Class<R> clRole)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.clCategoryUsecase = clCategoryUsecase;
        this.clCategoryRole = clCategoryRole;
        this.clUsecase = clUsecase;
        this.clRole = clRole;
    } 
    
    public CR create(String code)
    {
    	CR ejb = null;
    	
    	try
    	{
			ejb = clCategoryRole.newInstance();
			ejb.setCode(code);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}