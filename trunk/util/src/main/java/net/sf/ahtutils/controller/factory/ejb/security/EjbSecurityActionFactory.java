package net.sf.ahtutils.controller.factory.ejb.security;

import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSecurityActionFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityActionFactory.class);
	
    final Class<L> clLang;
    final Class<D> clDescription;
    final Class<C> clCategory;
    final Class<R> clRole;
    final Class<V> clView;
    final Class<U> clUsecase;
    final Class<A> clAction;
	
    public static <L extends UtilsLang,
	 			   D extends UtilsDescription,
	 			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
	 			   R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
	 			   V extends UtilsSecurityView<L,D,C,R,V,U,A>,
	 			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
	 			   A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
    	EjbSecurityActionFactory<L,D,C,R,V,U,A> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction)
    {
        return new EjbSecurityActionFactory<L,D,C,R,V,U,A>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction);
    }
    
    public EjbSecurityActionFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.clCategory = clCategory;
        this.clRole = clRole;
        this.clView = clView;
        this.clUsecase = clUsecase;
        this.clAction = clAction;
    } 
    
    public A create(V view, String code)
    {
    	A ejb = null;
    	
    	try
    	{
			ejb = clAction.newInstance();
			ejb.setView(view);
			ejb.setCode(code);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}