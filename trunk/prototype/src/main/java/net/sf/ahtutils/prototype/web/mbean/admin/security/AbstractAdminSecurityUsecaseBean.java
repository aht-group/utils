package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.controller.factory.ejb.security.EjbSecurityCategoryFactory;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAdminSecurityUsecaseBean <L extends UtilsLang,
											D extends UtilsDescription,
											C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
											R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
											V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
											U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
											A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityUsecaseBean.class);
	
	protected UtilsSecurityFacade fSecurity;
	
	private Class<C> cCategory;
	private Class<U> cUsecase;
	private Class<V> cView;
	private Class<A> cAction;
	
	private List<C> categories;
	public List<C> getCategories() {return categories;}
	
	private List<U> usecases;
	public List<U> getUsecases() {return usecases;}
	
	private C category;
	public void setCategory(C category) {this.category = category;}
	public C getCategory() {return category;}
	
	private String[] langs;
	private EjbLangFactory<L> efl;
	private EjbDescriptionFactory<D> efd;
	private EjbSecurityCategoryFactory<L,D,C,R,V,U,A,USER> efCategory;
	
	public void initSuper(String[] langs,final Class<L> cLang,final Class<D> clDescription, final Class<C> cCategory,final Class<R> cRole,final Class<V> cView,final Class<U> cUsecase, final Class<A> cAction,final Class<USER> cUser)
	{
		this.langs=langs;
		this.cCategory=cCategory;
		this.cUsecase=cUsecase;
		this.cView=cView;
		this.cAction=cAction;
		reloadCategories();
		
		efl = EjbLangFactory.createFactory(cLang);
		efd = EjbDescriptionFactory.createFactory(clDescription);
		efCategory = EjbSecurityCategoryFactory.factory(cLang,clDescription,cCategory,cRole,cView,cUsecase,cAction,cUser);
	}
	
	private void reloadCategories()
	{
		categories = fSecurity.allForType(cCategory,UtilsSecurityCategory.Type.usecase.toString());
	}

	public void addCategory() throws UtilsIntegrityException
	{
		logger.info(AbstractLogMessage.addEntity(cCategory));
		category = efCategory.create(null,UtilsSecurityCategory.Type.usecase.toString());
		category.setName(efl.createEmpty(langs));
		category.setDescription(efd.createEmpty(langs));
	}
	
	public void selectCategory() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(category));
	}
	
	public void saveCategory() throws UtilsNotFoundException, UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(category));
		category = fSecurity.save(category);
		reloadCategories();
	}
}