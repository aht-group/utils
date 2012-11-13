package net.sf.ahtutils.db.ejb.security;

import net.sf.ahtutils.controller.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityWithActions;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityWithViews;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Action;
import net.sf.ahtutils.xml.access.Actions;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractSecurityInit <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
 								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
 								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
 								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
 								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
 								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractSecurityInit.class);
	
    protected final Class<L> cL;
    protected final Class<D> cD;
    protected final Class<C> cC;
    protected final Class<R> cR;
    protected final Class<V> cV;
    protected final Class<U> cU;
    protected final Class<A> cA;
    protected final Class<USER> cUser;
	
	protected UtilsSecurityFacade fSecurity;
	protected EjbLangFactory<L> ejbLangFactory;
	protected EjbDescriptionFactory<D> ejbDescriptionFactory;
	
	public static <L extends UtilsLang,
	   			   D extends UtilsDescription, 
	   			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	   			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	   			   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	   			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	   			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	   			   USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		SecurityInitRoles<L,D,C,R,V,U,A,USER>
		factoryRoles(final Class<L> cL,final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV, final Class<U> cU, final Class<A> cA, final Class<USER> cUser, UtilsSecurityFacade fAcl)
	{
		return new SecurityInitRoles<L,D,C,R,V,U,A,USER>(cL,cD,cC,cR,cV,cU,cA,cUser,fAcl);
	}
	
	public static <L extends UtilsLang,
	   			   D extends UtilsDescription, 
	   			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	   			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	   			   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	   			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	   			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	   			USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		SecurityInitViews<L,D,C,R,V,U,A,USER>
		factoryViews(final Class<L> cL,final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV, final Class<U> cU, final Class<A> cA, final Class<USER> cUser, UtilsSecurityFacade fAcl)
	{
		return new SecurityInitViews<L,D,C,R,V,U,A,USER>(cL,cD,cC,cR,cV,cU,cA,cUser,fAcl);
	}
	
	public static <L extends UtilsLang,
	   			   D extends UtilsDescription, 
	   			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	   			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	   			   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	   			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	   			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	   			USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		SecurityInitUsecases<L,D,C,R,V,U,A,USER>
		factoryUsecases(final Class<L> cL,final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV, final Class<U> cU, final Class<A> cA, final Class<USER> cUser, UtilsSecurityFacade fAcl)
	{
		return new SecurityInitUsecases<L,D,C,R,V,U,A,USER>(cL,cD,cC,cR,cV,cU,cA,cUser,fAcl);
	}
	
	public AbstractSecurityInit(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,final Class<USER> cUser,UtilsSecurityFacade fAcl)
	{       
        this.cL = cL;
        this.cD = cD;
        this.cC = cC;
        this.cR = cR;
        this.cV = cV;
        this.cU = cU;
        this.cA = cA;
        this.cUser = cUser;
        
        this.fSecurity=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
	}
	
	protected void iuCategory(Access access, UtilsSecurityCategory.Type type) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Category.class.getSimpleName()+" with "+access.getCategory().size()+" categories");
		
		AhtDbEjbUpdater<C> updateCategory = AhtDbEjbUpdater.createFactory(cC);
		
		updateCategory.dbEjbs(fSecurity.allForType(cC,type.toString()));

		for(Category category : access.getCategory())
		{
			updateCategory.actualAdd(category.getCode());
			
			C aclCategory;
			try
			{
				aclCategory = fSecurity.fByCode(cC,category.getCode());
				ejbLangFactory.rmLang(fSecurity,aclCategory);
				ejbDescriptionFactory.rmDescription(fSecurity,aclCategory);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					aclCategory = cC.newInstance();
					aclCategory.setType(type.toString());
					aclCategory.setCode(category.getCode());
					aclCategory = (C)fSecurity.persist(aclCategory);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				aclCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
				aclCategory.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				aclCategory=(C)fSecurity.update(aclCategory);
				iuChilds(aclCategory,category);
				
			}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
		
		updateCategory.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	protected void iuChilds(C aclCategory, Category category) throws UtilsConfigurationException
	{
		logger.error("This method *must* be overridden!");
	}
	
	protected <T extends UtilsSecurityWithViews<L,D,C,R,V,U,A,USER>> T iuListViews(T ejb, Views views) throws UtilsContraintViolationException, UtilsNotFoundException, UtilsLockingException
	{
		ejb.getViews().clear();
		ejb = fSecurity.update(ejb);
		if(views!=null)
		{
			for(View view : views.getView())
			{
				V ejbView = fSecurity.fByCode(cV, view.getCode());
				ejb.getViews().add(ejbView);
			}
			ejb = fSecurity.update(ejb);
		}
		return ejb;
	}
	
	protected <T extends UtilsSecurityWithActions<L,D,C,R,V,U,A,USER>> T iuListActions(T ejb, Actions actions) throws UtilsContraintViolationException, UtilsNotFoundException, UtilsLockingException
	{
		ejb.getActions().clear();
		ejb = fSecurity.update(ejb);
		if(actions!=null)
		{
			for(Action action : actions.getAction())
			{
				A ejbAction = fSecurity.fByCode(cA, action.getCode());
				ejb.getActions().add(ejbAction);
			}
			ejb = fSecurity.update(ejb);
		}
		return ejb;
	}
}