package org.jeesl.web.rest.system.security.updater;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithActions;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithViews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Action;
import net.sf.ahtutils.xml.access.Actions;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;
import net.sf.ahtutils.xml.security.Security;

public class AbstractSecurityUpdater <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends JeeslSecurityCategory<L,D>,
 								R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
 								V extends JeeslSecurityView<L,D,C,R,U,A>,
 								U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
 								A extends JeeslSecurityAction<L,D,R,V,U,AT>,
 								AT extends JeeslSecurityTemplate<L,D,C>,
 								M extends JeeslSecurityMenu<V,M>,
 								USER extends JeeslUser<R>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractSecurityUpdater.class);
	
    protected final Class<L> cL;
    protected final Class<D> cD;
    protected final Class<C> cC;
    protected final Class<R> cR;
    protected final Class<V> cV;
    protected final Class<U> cU;
    protected final Class<A> cA;
    protected final Class<AT> cT;
    protected final Class<USER> cUser;
	
	protected JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	protected EjbLangFactory<L> ejbLangFactory;
	protected EjbDescriptionFactory<D> ejbDescriptionFactory;
				
	public AbstractSecurityUpdater(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,USER> fbSecurity,
			final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,final Class<AT> cT,final Class<USER> cUser,JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fAcl)
	{       
        this.cL = cL;
        this.cD = cD;
        this.cC = cC;
        this.cR = cR;
        this.cV = cV;
        this.cU = cU;
        this.cA = cA;
        this.cT = cT; 
        this.cUser = cUser;
        
        this.fSecurity=fAcl;
		
		ejbLangFactory = EjbLangFactory.factory(cL);
		ejbDescriptionFactory = EjbDescriptionFactory.factory(cD);
	}
	
	@Deprecated protected void iuCategory(Access access, JeeslSecurityCategory.Type type) throws UtilsConfigurationException
	{
		logger.info("i/u "+type+" with "+access.getCategory().size()+" categories");
		
		JeeslDbCodeEjbUpdater<C> updateCategory = JeeslDbCodeEjbUpdater.createFactory(cC);
		updateCategory.dbEjbs(fSecurity.allForType(cC,type.toString()));

		for(Category category : access.getCategory())
		{
			updateCategory.actualAdd(category.getCode());
			C ejbCategory;
			try
			{
				ejbCategory = fSecurity.fByTypeCode(cC,type.toString(),category.getCode());
				ejbLangFactory.rmLang(fSecurity,ejbCategory);
				ejbDescriptionFactory.rmDescription(fSecurity,ejbCategory);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejbCategory = cC.newInstance();
					ejbCategory.setType(type.toString());
					ejbCategory.setCode(category.getCode());
					logger.trace("Persisting "+ejbCategory.toString());
					ejbCategory = (C)fSecurity.persist(ejbCategory);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				ejbCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
				ejbCategory.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				
				if(category.isSetVisible()){ejbCategory.setVisible(category.isVisible());}else{ejbCategory.setVisible(true);}
				if(category.isSetPosition()){ejbCategory.setPosition(category.getPosition());}else{ejbCategory.setPosition(0);}
				
				ejbCategory=fSecurity.update(ejbCategory);
				logger.trace("Proceeding with childs");
				iuChilds(ejbCategory,category);
			}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
		
		updateCategory.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	protected void iuCategory(Security security, JeeslSecurityCategory.Type type) throws UtilsConfigurationException
	{
		logger.info("i/u "+type+" with "+security.getCategory().size()+" categories");
		
		JeeslDbCodeEjbUpdater<C> updateCategory = JeeslDbCodeEjbUpdater.createFactory(cC);
		
		updateCategory.dbEjbs(fSecurity.allForType(cC,type.toString()));

		for(net.sf.ahtutils.xml.security.Category category : security.getCategory())
		{
			updateCategory.actualAdd(category.getCode());
			
			C ejbCategory;
			try
			{
				ejbCategory = fSecurity.fByTypeCode(cC,type.toString(),category.getCode());
				ejbLangFactory.rmLang(fSecurity,ejbCategory);
				ejbDescriptionFactory.rmDescription(fSecurity,ejbCategory);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejbCategory = cC.newInstance();
					ejbCategory.setType(type.toString());
					ejbCategory.setCode(category.getCode());
					ejbCategory = (C)fSecurity.persist(ejbCategory);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				ejbCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
				ejbCategory.setDescription(ejbDescriptionFactory.create(category.getDescriptions()));
				
				if(category.isSetVisible()){ejbCategory.setVisible(category.isVisible());}else{ejbCategory.setVisible(true);}
				if(category.isSetPosition()){ejbCategory.setPosition(category.getPosition());}else{ejbCategory.setPosition(0);}
				
				ejbCategory=(C)fSecurity.update(ejbCategory);
				iuChilds(ejbCategory,category);
			}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
		
		updateCategory.remove(fSecurity);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	@Deprecated protected void iuChilds(C aclCategory, Category category) throws UtilsConfigurationException
	{
		logger.error("This method *must* be overridden!");
	}
	protected void iuChilds(C aclCategory, net.sf.ahtutils.xml.security.Category category) throws UtilsConfigurationException
	{
		logger.error("This method *must* be overridden!");
	}
	
	@Deprecated protected <T extends JeeslSecurityWithViews<V>> T iuListViews(T ejb, Views views) throws UtilsConstraintViolationException, UtilsNotFoundException, UtilsLockingException
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
	protected <T extends JeeslSecurityWithViews<V>> T iuListViewsSecurity(T ejb, net.sf.ahtutils.xml.security.Views views) throws UtilsConstraintViolationException, UtilsNotFoundException, UtilsLockingException
	{
//		ejb = fSecurity.load(cView, view);
		ejb.getViews().clear();
		ejb = fSecurity.update(ejb);
		if(views!=null)
		{
			for(net.sf.ahtutils.xml.security.View view : views.getView())
			{
				logger.trace("Adding view "+view.getCode()+" to "+ejb.toString());
				V ejbView = fSecurity.fByCode(cV, view.getCode());
				ejb.getViews().add(ejbView);
			}
			ejb = fSecurity.update(ejb);
		}
		return ejb;
	}
	
	@Deprecated protected <T extends JeeslSecurityWithActions<A>> T iuListActions(T ejb, Actions actions) throws UtilsConstraintViolationException, UtilsNotFoundException, UtilsLockingException
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
	protected <T extends JeeslSecurityWithActions<A>> T iuListActions(T ejb, net.sf.ahtutils.xml.security.Actions actions) throws UtilsConstraintViolationException, UtilsNotFoundException, UtilsLockingException
	{
		ejb.getActions().clear();
		ejb = fSecurity.update(ejb);
		if(actions!=null)
		{
			for(net.sf.ahtutils.xml.security.Action action : actions.getAction())
			{
				A ejbAction = fSecurity.fByCode(cA, action.getCode());
				ejb.getActions().add(ejbAction);
			}
			ejb = fSecurity.update(ejb);
		}
		return ejb;
	}
}