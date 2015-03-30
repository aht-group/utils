package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.controller.factory.ejb.security.EjbSecurityActionFactory;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAdminSecurityViewBean <L extends UtilsLang,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityViewBean.class);
	
	protected UtilsSecurityFacade fSecurity;
	
	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
	private EjbSecurityActionFactory<L,D,C,R,V,U,A,USER> efAction;
	
	private Class<C> cCategory;
	private Class<V> cView;
	private Class<A> cAction;
	
	private List<C> categories;
	public List<C> getCategories() {return categories;}
	
	private List<V> views;
	public List<V> getViews(){return views;}
	
	private List<A> actions;
	public List<A> getActions(){return actions;}

	private C category;
	public void setCategory(C category) {this.category = category;}
	public C getCategory() {return category;}
	
	private V view;
	public V getView(){return view;}
	public void setView(V view) {this.view = view;}
	
	private A action;
	public A getAction(){return action;}
	public void setAction(A action) {this.action = action;}
	
	private String[] langs;
	
	public void initSuper(final Class<L> cLang, final Class<D> cDescription, final Class<C> cCategory, final Class<R> cRole, final Class<V> cView, final Class<U> cUsecase, final Class<A> cAction, final Class<USER> cUser, String[] langs)
	{
		this.cCategory=cCategory;
		this.cView=cView;
		this.cAction=cAction;
		this.langs=langs;
		
		efLang = new EjbLangFactory<L>(cLang);
		efDescription = new EjbDescriptionFactory<D>(cDescription);
		efAction = EjbSecurityActionFactory.factory(cLang,cDescription,cCategory,cRole,cView,cUsecase,cAction,cUser);
		
		reloadCategories();
	}
	
	// SELECT
	public void selectCategory() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(category));
		category = efLang.persistMissingLangs(fSecurity,langs,category);
		category = efDescription.persistMissingLangs(fSecurity,langs,category);
		reloadViews();
		view=null;
		action=null;
	}
	public void selectView()
	{
		logger.info(AbstractLogMessage.selectEntity(view));
		view = efLang.persistMissingLangs(fSecurity,langs,view);
		view = efDescription.persistMissingLangs(fSecurity,langs,view);
		reloadActions();
		action=null;
	}
	public void selectAction()
	{
		logger.info(AbstractLogMessage.selectEntity(action));
		action = efLang.persistMissingLangs(fSecurity,langs,action);
		action = efDescription.persistMissingLangs(fSecurity,langs,action);
	}
	
	//RELOAD
	private void reloadCategories()
	{
		categories = fSecurity.allForType(cCategory,UtilsSecurityCategory.Type.view.toString());
	}
	private void reloadViews() throws UtilsNotFoundException
	{
		views = fSecurity.allForCategory(cView,cCategory,category.getCode());
		logger.info("Reloaded "+views.size());
	}
	private void reloadActions()
	{
		view = fSecurity.load(cView,view);
		actions = view.getActions();
	}
	
	//SAVE
	public void saveCategory() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(category));
		category = fSecurity.save(category);
		reloadCategories();
	}
	
	public void saveView() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(view));
		view = fSecurity.save(view);
		reloadViews();
	}
	
	public void saveAction() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(action));
		action = fSecurity.save(action);
		reloadActions();
	}
	
	//ACTION
	public void addAction() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(cAction));
		action = efAction.create(view,"");
		action.setName(efLang.createEmpty(langs));
		action.setDescription(efDescription.createEmpty(langs));
	}
	
	public void rmAction() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.rmEntity(action));
		fSecurity.rm(action);
		action=null;
	}
}