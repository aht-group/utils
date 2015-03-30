package net.sf.ahtutils.controller.factory.ejb.acl;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAclCategoryViewFactory <L extends UtilsLang,
											D extends UtilsDescription,
											CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
											U extends UtilsAclView<L,D,CU,U>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAclCategoryViewFactory.class);
	
    final Class<L> clLang;
    final Class<D> clDescription;
    final Class<CU> clCategoryUsecase;
    final Class<U> clUsecase;
	
    public static <L extends UtilsLang,
					D extends UtilsDescription,
					CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
					U extends UtilsAclView<L,D,CU,U>>
    	EjbAclCategoryViewFactory<L,D,CU,U> factory(final Class<L> clLang,final Class<D> clDescription,final Class<CU> clCategoryUsecase,final Class<U> clUsecase)
    {
        return new EjbAclCategoryViewFactory<L,D,CU,U>(clLang,clDescription,clCategoryUsecase,clUsecase);
    }
    
    public EjbAclCategoryViewFactory(final Class<L> clLang,final Class<D> clDescription,Class<CU> clCategoryUsecase, final Class<U> clUsecase)
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