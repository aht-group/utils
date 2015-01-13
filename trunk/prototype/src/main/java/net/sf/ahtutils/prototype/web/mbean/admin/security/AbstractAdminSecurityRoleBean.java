package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.controller.factory.ejb.security.EjbSecurityRoleFactory;
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

public class AbstractAdminSecurityRoleBean <L extends UtilsLang,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityRoleBean.class);
	
	protected UtilsSecurityFacade fSecurity;
	
	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
	private EjbSecurityRoleFactory<L,D,C,R,V,U,A,USER> efRole;
	
	private Class<C> cCategory;
	private Class<R> cRole;
	
	private List<C> categories;
	public List<C> getCategories() {return categories;}
	
	private List<R> roles;
	public List<R> getRoles(){return roles;}

	private C category;
	public void setCategory(C category) {this.category = category;}
	public C getCategory() {return category;}
	
	private R role;
	public R getRole(){return role;}
	public void setRole(R role) {this.role = role;}
	
	private String[] langs;
	
	public void initSuper(final Class<L> cLang, final Class<D> cDescription, final Class<C> cCategory, final Class<R> cRole, final Class<V> cView, final Class<U> cUsecase, final Class<A> cAction, final Class<USER> cUser, String[] langs)
	{
		this.cCategory=cCategory;
		this.cRole=cRole;
		this.langs=langs;
		
		efLang = new EjbLangFactory<L>(cLang);
		efDescription = new EjbDescriptionFactory<D>(cDescription);
		efRole = EjbSecurityRoleFactory.factory(cLang,cDescription,cCategory,cRole,cView,cUsecase,cAction,cUser);
		
		reloadCategories();
	}
	
	// SELECT
	public void selectCategory() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(category));
		category = efLang.persistMissingLangs(fSecurity,langs,category);
		category = efDescription.persistMissingLangs(fSecurity,langs,category);
		reloadRoles();
		role=null;
	}
	public void selectRole()
	{
		logger.info(AbstractLogMessage.selectEntity(role));
		role = efLang.persistMissingLangs(fSecurity,langs,role);
		role = efDescription.persistMissingLangs(fSecurity,langs,role);
	}
	
	//RELOAD
	private void reloadCategories()
	{
		categories = fSecurity.allForType(cCategory,UtilsSecurityCategory.Type.role.toString());
	}
	private void reloadRoles() throws UtilsNotFoundException
	{
		roles = fSecurity.allForCategory(cRole,cCategory,category.getCode());
		logger.info("Reloaded "+roles.size());
	}
	
	//SAVE
	public void saveCategory() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(category));
		category = fSecurity.save(category);
		reloadCategories();
	}
	public void saveRole() throws UtilsContraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(role));
		role = fSecurity.save(role);
		reloadRoles();
	}

	//Role
	public void addRole() throws UtilsIntegrityException
	{
		logger.info(AbstractLogMessage.addEntity(cRole));
		role = efRole.create(category,"");
		role.setName(efLang.createEmpty(langs));
		role.setDescription(efDescription.createEmpty(langs));
	}
	
	public void rmRole() throws UtilsIntegrityException
	{
		logger.info(AbstractLogMessage.rmEntity(role));
		fSecurity.rm(role);
		role=null;
	}
}