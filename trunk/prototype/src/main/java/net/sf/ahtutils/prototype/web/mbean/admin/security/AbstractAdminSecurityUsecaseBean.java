package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
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
		extends AbstractAdminSecurityBean<L,D,C,R,V,U,A,USER>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityUsecaseBean.class);

	private List<C> categories;
	public List<C> getCategories() {return categories;}
	
	private List<U> usecases;
	public List<U> getUsecases() {return usecases;}
	
	private C category;
	public void setCategory(C category) {this.category = category;}
	public C getCategory() {return category;}
	
	private U usecase;
	public U getUsecase(){return usecase;}
	public void setUsecase(U usecase){this.usecase = usecase;}
	
	public void initSuper(String[] langs,final Class<L> cLang,final Class<D> cDescription, final Class<C> cCategory,final Class<R> cRole,final Class<V> cView,final Class<U> cUsecase, final Class<A> cAction,final Class<USER> cUser)
	{
		initSecuritySuper(cLang,cDescription,cCategory,cRole,cView,cUsecase,cAction,cUser,langs);
		opViews = fSecurity.all(cView);
		opActions = new ArrayList<A>();
		reloadCategories();
	}
	
	//SELECT
	public void selectCategory() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(category));
		reloadUsecases();
		usecase=null;
	}
	public void selectUsecase()
	{
		logger.info(AbstractLogMessage.selectEntity(usecase));
		usecase = efLang.persistMissingLangs(fSecurity,langs,usecase);
		usecase = efDescription.persistMissingLangs(fSecurity,langs,usecase);
		usecase = fSecurity.find(cUsecase,usecase);
		reloadActions();
	}
	
	//Reload
	private void reloadCategories()
	{
		logger.info("reloadCategories");
		categories = fSecurity.allForType(cCategory,UtilsSecurityCategory.Type.usecase.toString());
	}
	private void reloadUsecases() throws UtilsNotFoundException
	{
		logger.info("reloadUsecases");
		usecases = fSecurity.allForCategory(cUsecase,cCategory,category.getCode());
	}
	private void reloadActions()
	{
		opActions.clear();
		for(V v : usecase.getViews())
		{
			v = fSecurity.load(cView,v);
			opActions.addAll(v.getActions());
		}
	}
	
	//Add
	public void addCategory() throws UtilsContraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(cCategory));
		category = efCategory.create(null,UtilsSecurityCategory.Type.usecase.toString());
		category.setName(efLang.createEmpty(langs));
		category.setDescription(efDescription.createEmpty(langs));
	}
	public void addUsecase() throws UtilsContraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(cUsecase));
		usecase = efUsecase.create(category,"");
		usecase.setName(efLang.createEmpty(langs));
		usecase.setDescription(efDescription.createEmpty(langs));
	}
	

	//Save
	public void saveCategory() throws UtilsNotFoundException, UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(category));
		category = fSecurity.save(category);
		reloadCategories();
		reloadUsecases();
	}
	public void saveUsecase() throws UtilsContraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(usecase));
		usecase = fSecurity.save(usecase);
		reloadUsecases();
	}
	
	//OverlayPanel
	public void opAddView() throws UtilsContraintViolationException, UtilsLockingException
	{
		if(!usecase.getViews().contains(opView))
		{
			usecase.getViews().add(opView);
			usecase = fSecurity.save(usecase);
			opView = null;
			selectUsecase();
		}
	}
	public void opAddAction() throws UtilsContraintViolationException, UtilsLockingException
	{
		if(!usecase.getActions().contains(opAction))
		{
			usecase.getActions().add(opAction);
			usecase = fSecurity.save(usecase);
			opAction = null;
			selectUsecase();
		}
	}
	
	//Overlay-Rm
	public void opRmView() throws UtilsContraintViolationException, UtilsLockingException
	{
		if(tblView!=null && usecase.getViews().contains(tblView))
		{
			usecase.getViews().remove(tblView);
			usecase = fSecurity.save(usecase);
			tblView = null;
			selectUsecase();
		}
	}
	public void opRmAction() throws UtilsContraintViolationException, UtilsLockingException
	{
		if(tblAction!=null && usecase.getActions().contains(tblAction))
		{
			usecase.getActions().remove(tblAction);
			usecase = fSecurity.save(usecase);
			tblAction = null;
			selectUsecase();
		}
	}
}