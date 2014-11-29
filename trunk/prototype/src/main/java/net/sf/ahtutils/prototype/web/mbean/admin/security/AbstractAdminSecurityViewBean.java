package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
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

public class AbstractAdminSecurityViewBean <L extends UtilsLang,
D extends UtilsDescription,
C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
USER extends UtilsUser<L,D,C,R,V,U,A,USER>> implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityViewBean.class);
	
	protected UtilsSecurityFacade fSecurity;
	
	private Class<C> cCategory;
	private Class<V> cView;
	
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
	
	public void initSuper(final Class<C> cCategory, final Class<V> cView)
	{
		this.cCategory=cCategory;
		this.cView=cView;
		reloadCategories();
	}
	
	public void selectCategory() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(category));
		reloadViews();
		view=null;
		action=null;
	}
	
	public void selectView()
	{
		logger.info(AbstractLogMessage.selectEntity(view));
		reloadActions();
		action=null;
	}
	
	public void selectAction()
	{
		logger.info(AbstractLogMessage.selectEntity(action));
	}
	
	
	//RELOAD
	private void reloadCategories()
	{
		categories = fSecurity.allForType(cCategory,UtilsSecurityCategory.Type.view.toString());
	}
	private void reloadViews() throws UtilsNotFoundException
	{
		views = fSecurity.allForCategory(cView,cCategory,category.getCode());
	}
	private void reloadActions()
	{
		view = fSecurity.load(cView,view);
		actions = view.getActions();
	}
	
	public void saveCategory() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(category));
		category = fSecurity.save(category);
		reloadCategories();
	}
	
	public void saveView() throws UtilsContraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(view));
		view = fSecurity.save(view);
		reloadViews();
	}
	
	public void saveAction() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(action));
		action = fSecurity.save(action);
		reloadActions();
	}
}