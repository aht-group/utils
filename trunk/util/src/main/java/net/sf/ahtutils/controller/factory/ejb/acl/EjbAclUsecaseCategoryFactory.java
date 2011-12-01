package net.sf.ahtutils.controller.factory.ejb.acl;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAclUsecaseCategoryFactory <L extends UtilsLang,
											D extends UtilsDescription,
											CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
											U extends UtilsAclUsecase<L,D,CU,U>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAclUsecaseCategoryFactory.class);
	
    final Class<L> clLang;
    final Class<D> clDescription;
    final Class<CU> clCategoryUsecase;
    final Class<U> clUsecase;
	
    public static <L extends UtilsLang,
					D extends UtilsDescription,
					CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
					U extends UtilsAclUsecase<L,D,CU,U>>
    	EjbAclUsecaseCategoryFactory<L,D,CU,U> factory(final Class<L> clLang,final Class<D> clDescription,final Class<CU> clCategoryUsecase,final Class<U> clUsecase)
    {
        return new EjbAclUsecaseCategoryFactory<L,D,CU,U>(clLang,clDescription,clCategoryUsecase,clUsecase);
    }
    
    public EjbAclUsecaseCategoryFactory(final Class<L> clLang,final Class<D> clDescription,Class<CU> clCategoryUsecase, final Class<U> clUsecase)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.clCategoryUsecase = clCategoryUsecase;
        this.clUsecase = clUsecase;
    } 
    
    public CU create(String code)
    {
    	CU ejb = null;
    	
    	try
    	{
			ejb = clCategoryUsecase.newInstance();
			ejb.setCode(code);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}