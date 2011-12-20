package net.sf.ahtutils.db.ejb.security;

import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.controller.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.controller.interfaces.AhtSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
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
 								C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
 								R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
 								V extends UtilsSecurityView<L,D,C,R,V,U,A>,
 								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
 								A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractSecurityInit.class);
	
    protected final Class<L> cL;
    protected final Class<D> cD;
    protected final Class<C> cC;
    protected final Class<R> cR;
    protected final Class<V> cV;
    protected final Class<U> cU;
    protected  final Class<A> cA;
	
	protected AhtSecurityFacade fSecurity;
	protected EjbLangFactory<L> ejbLangFactory;
	protected EjbDescriptionFactory<D> ejbDescriptionFactory;
	
	public static <L extends UtilsLang,
	   			   D extends UtilsDescription, 
	   			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
	   			   R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
	   			   V extends UtilsSecurityView<L,D,C,R,V,U,A>,
	   			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
	   			   A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
		SecurityInitRoles<L,D,C,R,V,U,A>
		factoryRoles(final Class<L> cL,final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV, final Class<U> cU, final Class<A> cA, AhtSecurityFacade fAcl)
	{
		return new SecurityInitRoles<L,D,C,R,V,U,A>(cL,cD,cC,cR,cV,cU,cA,fAcl);
	}
	
	public static <L extends UtilsLang,
	   			   D extends UtilsDescription, 
	   			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
	   			   R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
	   			   V extends UtilsSecurityView<L,D,C,R,V,U,A>,
	   			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
	   			   A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
		SecurityInitViews<L,D,C,R,V,U,A>
		factoryViews(final Class<L> cL,final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV, final Class<U> cU, final Class<A> cA, AhtSecurityFacade fAcl)
	{
		return new SecurityInitViews<L,D,C,R,V,U,A>(cL,cD,cC,cR,cV,cU,cA,fAcl);
	}
	
	public static <L extends UtilsLang,
	   			   D extends UtilsDescription, 
	   			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
	   			   R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
	   			   V extends UtilsSecurityView<L,D,C,R,V,U,A>,
	   			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
	   			   A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
		SecurityInitUsecases<L,D,C,R,V,U,A>
		factoryUsecases(final Class<L> cL,final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV, final Class<U> cU, final Class<A> cA, AhtSecurityFacade fAcl)
	{
		return new SecurityInitUsecases<L,D,C,R,V,U,A>(cL,cD,cC,cR,cV,cU,cA,fAcl);
	}
	
	public AbstractSecurityInit(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,AhtSecurityFacade fAcl)
	{       
        this.cL = cL;
        this.cD = cD;
        this.cC = cC;
        this.cR = cR;
        this.cV = cV;
        this.cU = cU;
        this.cA = cA;
        
        this.fSecurity=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cD);
	}
	
	protected void iuCategory(Access access, UtilsSecurityCategory.Type type) throws AhtUtilsConfigurationException
	{
		logger.debug("i/u "+Category.class.getSimpleName()+" with "+access.getCategory().size()+" categories");
		
		AhtDbEjbUpdater<C> updateCategory = AhtDbEjbUpdater.createFactory(cC);
		
		updateCategory.dbEjbs(fSecurity.allUtilsForType(cC,type.toString()));
//		updateCategory.dbEjbs(fSecurity.all(cC));

		for(Category category : access.getCategory())
		{
			updateCategory.actualAdd(category.getCode());
			
			C aclCategory;
			try
			{
				aclCategory = fSecurity.fAhtUtilsByCode(cC,category.getCode());
				rmLang(aclCategory);
				rmDescription(aclCategory);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					aclCategory = cC.newInstance();
					aclCategory.setType(type.toString());
					aclCategory.setCode(category.getCode());
					aclCategory = (C)fSecurity.persistAhtUtilsStatus(aclCategory);
				}
				catch (InstantiationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
				catch (UtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				aclCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
				aclCategory.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				aclCategory=(C)fSecurity.updateAhtUtilsStatus(aclCategory);
				iuChilds(aclCategory,category);
				
			}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
		}
		
		updateCategory.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	protected void iuChilds(C aclCategory, Category category) throws AhtUtilsConfigurationException
	{
		logger.error("This method *must* be overridden!");
	}
	
	protected <M extends EjbWithLang<L>> void rmLang(M ejb)
	{
		Map<String,L> langMap = ejb.getName();
		ejb.setName(null);
		
		try{ejb=fSecurity.updateAhtUtilsStatus(ejb);}
		catch (UtilsContraintViolationException e) {logger.error("",e);}
		
		for(L lang : langMap.values())
		{
			try {fSecurity.rmAhtUtilsEntity(lang);}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
		}
	}
	
	protected <M extends EjbWithDescription<D>> void rmDescription(M ejb)
	{
		Map<String,D> descMap = ejb.getDescription();
		ejb.setDescription(null);
		
		try{ejb=fSecurity.updateAhtUtilsStatus(ejb);}
		catch (UtilsContraintViolationException e) {logger.error("",e);}
		
		for(D desc : descMap.values())
		{
			try {fSecurity.rmAhtUtilsEntity(desc);}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
		}
	}
	
	protected <T extends UtilsSecurityWithViews<L,D,C,R,V,U,A>> T iuListViews(T ejb, Views views) throws UtilsContraintViolationException, UtilsNotFoundException
	{
		ejb.getViews().clear();
		ejb = fSecurity.updateAhtUtilsStatus(ejb);
		if(views!=null)
		{
			for(View view : views.getView())
			{
				V ejbView = fSecurity.fAhtUtilsByCode(cV, view.getCode());
				ejb.getViews().add(ejbView);
			}
			ejb = fSecurity.updateAhtUtilsStatus(ejb);
		}
		return ejb;
	}
	
	protected <T extends UtilsSecurityWithActions<L,D,C,R,V,U,A>> T iuListActions(T ejb, Actions actions) throws UtilsContraintViolationException, UtilsNotFoundException
	{
		ejb.getActions().clear();
		ejb = fSecurity.updateAhtUtilsStatus(ejb);
		if(actions!=null)
		{
			for(Action action : actions.getAction())
			{
				A ejbAction = fSecurity.fAhtUtilsByCode(cA, action.getCode());
				ejb.getActions().add(ejbAction);
			}
			ejb = fSecurity.updateAhtUtilsStatus(ejb);
		}
		return ejb;
	}
}