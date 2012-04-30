package  net.sf.ahtutils.controller.factory.ejb.security;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsAuditTrail;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EjbAuditTrailFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A>,
										 US extends UtilsUser<L,D,C,R,V,U,A>,
							 			 T extends UtilsAuditTrail<L,D,C,R,V,U,A,US,TY>,
							 			 TY extends UtilsStatus<L>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAuditTrailFactory.class);
	
    final Class<L> clLang;
    final Class<D> clDescription;
    final Class<C> clCategory;
    final Class<R> clRole;
    final Class<V> clView;
    final Class<U> clUsecase;
    final Class<A> clAction;
    final Class<US> clUser;
    final Class<T> clTrail;
    final Class<TY> clType;
	
    public static <L extends UtilsLang,
	 			   D extends UtilsDescription,
	 			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
	 			   R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
	 			   V extends UtilsSecurityView<L,D,C,R,V,U,A>,
	 			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
	 			   A extends UtilsSecurityAction<L,D,C,R,V,U,A>,
	 			   US extends UtilsUser<L,D,C,R,V,U,A>,
	 			   T extends UtilsAuditTrail<L,D,C,R,V,U,A,US,TY>,
	 			   TY extends UtilsStatus<L>>
    	EjbAuditTrailFactory<L,D,C,R,V,U,A,US,T,TY> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<US> clUser, final Class<T> clTrail,final Class<TY> clType)
    {
        return new EjbAuditTrailFactory<L,D,C,R,V,U,A,US,T,TY>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser,clTrail,clType);
    }
    
    public EjbAuditTrailFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<US> clUser, final Class<T> clTrail,final Class<TY> clType)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.clCategory = clCategory;
        this.clRole = clRole;
        this.clView = clView;
        this.clUsecase = clUsecase;
        this.clAction = clAction;
        this.clUser = clUser;
        this.clTrail = clTrail;
        this.clType = clType;
    } 
    
    public T create(US user, TY type)
    {
    	T ejb = null;
    	
    	try
    	{
			ejb = clTrail.newInstance();
			ejb.setUser(user);
			ejb.setType(type);
			ejb.setRecord(new Date());
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}