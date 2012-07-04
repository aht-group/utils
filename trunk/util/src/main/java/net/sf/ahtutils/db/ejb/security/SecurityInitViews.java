package net.sf.ahtutils.db.ejb.security;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Action;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityInitViews <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
 								R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
 								V extends UtilsSecurityView<L,D,C,R,V,U,A>,
 								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
 								A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
		extends AbstractSecurityInit<L,D,C,R,V,U,A>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityInitViews.class);
	
	private AhtDbEjbUpdater<V> updateView;
	private AhtDbEjbUpdater<A> updateAction;
	
	public SecurityInitViews(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,UtilsSecurityFacade fAcl)
	{       
        super(cL,cD,cC,cR,cV,cU,cA,fAcl);
	}
	
	public void iuViews(Access access) throws UtilsConfigurationException
	{
		updateView = AhtDbEjbUpdater.createFactory(cV);
		updateAction = AhtDbEjbUpdater.createFactory(cA);
		
		updateView.dbEjbs(fSecurity.all(cV));
		updateAction.dbEjbs(fSecurity.all(cA));

		iuCategory(access, UtilsSecurityCategory.Type.view);
		
		updateView.remove(fSecurity);
		updateAction.remove(fSecurity);
		logger.trace("iuRoles finished");
	}
	
	@Override protected void iuChilds(C aclCategory, Category category) throws UtilsConfigurationException
	{
		if(category.isSetViews() && category.getViews().isSetView())
		{
			for(View view : category.getViews().getView())
			{
				logger.trace("View: "+view.getCode());
				updateView.actualAdd(view.getCode());
				iuView(aclCategory, view);
			}
		}
	}
	
	private void iuView(C category, View view) throws UtilsConfigurationException
	{
		V ebj;
		try
		{
			ebj = fSecurity.fByCode(cV,view.getCode());
			rmLang(ebj);
			rmDescription(ebj);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ebj = cV.newInstance();
				ebj.setCategory(category);
				ebj.setCode(view.getCode());
				ebj = fSecurity.persist(ebj);
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			ebj.setName(ejbLangFactory.getLangMap(view.getLangs()));
			ebj.setDescription(ejbDescriptionFactory.create(view.getDescriptions()));
			ebj.setCategory(category);
			ebj=fSecurity.update(ebj);
			
			if(view.isSetActions() && view.getActions().isSetAction())
			{
				for(Action action : view.getActions().getAction())
				{
					updateAction.actualAdd(action.getCode());
					iuAction(ebj, action);
				}
			}
		}
		catch (UtilsContraintViolationException e) {logger.error("",e);}
		catch (InstantiationException e) {logger.error("",e);}
		catch (IllegalAccessException e) {logger.error("",e);}
		catch (UtilsIntegrityException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
	
	private void iuAction(V ejbView, Action action) throws UtilsConfigurationException
	{
		A ebj;
		try
		{
			ebj = fSecurity.fByCode(cA,action.getCode());
			rmLang(ebj);
			rmDescription(ebj);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ebj = cA.newInstance();
				ebj.setView(ejbView);
				ebj.setCode(action.getCode());
				ebj = fSecurity.persist(ebj);
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			ebj.setName(ejbLangFactory.getLangMap(action.getLangs()));
			ebj.setDescription(ejbDescriptionFactory.create(action.getDescriptions()));
			ebj.setView(ejbView);
			ebj=fSecurity.update(ebj);
		}
		catch (UtilsContraintViolationException e) {logger.error("",e);}
		catch (InstantiationException e) {logger.error("",e);}
		catch (IllegalAccessException e) {logger.error("",e);}
		catch (UtilsIntegrityException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
}