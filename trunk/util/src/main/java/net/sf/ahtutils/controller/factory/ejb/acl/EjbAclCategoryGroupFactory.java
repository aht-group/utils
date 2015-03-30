package net.sf.ahtutils.controller.factory.ejb.acl;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAclCategoryGroupFactory <L extends UtilsLang,
										D extends UtilsDescription,
										CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
										CR extends UtilsAclCategoryGroup<L,D,CU,CR,U,R>,
										U extends UtilsAclView<L,D,CU,U>,
										R extends UtilsAclGroup<L,D,CU,CR,U,R>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAclCategoryGroupFactory.class);
	
    final Class<L> clLang;
    final Class<D> clDescription;
    final Class<CU> clCategoryUsecase;
    final Class<CR> clCategoryRole;
    final Class<U> clUsecase;
    final Class<R> clRole;
	
    public static <L extends UtilsLang,
					D extends UtilsDescription,
					CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
					CR extends UtilsAclCategoryGroup<L,D,CU,CR,U,R>,
					U extends UtilsAclView<L,D,CU,U>,
					R extends UtilsAclGroup<L,D,CU,CR,U,R>>
    	EjbAclCategoryGroupFactory<L,D,CU,CR,U,R> factory(final Class<L> clLang,final Class<D> clDescription,final Class<CU> clCategoryUsecase,final Class<CR> clCategoryRole,final Class<U> clUsecase,final Class<R> clRole)
    {
        return new EjbAclCategoryGroupFactory<L,D,CU,CR,U,R>(clLang,clDescription,clCategoryUsecase,clCategoryRole,clUsecase,clRole);
    }
    
    public EjbAclCategoryGroupFactory(final Class<L> clLang,final Class<D> clDescription,Class<CU> clCategoryUsecase,final Class<CR> clCategoryRole, final Class<U> clUsecase,final Class<R> clRole)
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