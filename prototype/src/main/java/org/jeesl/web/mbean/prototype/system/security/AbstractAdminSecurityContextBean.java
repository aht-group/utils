package org.jeesl.web.mbean.prototype.system.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jeesl.api.bean.JeeslSecurityBean;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;
import org.jeesl.interfaces.model.system.security.doc.JeeslSecurityOnlineHelp;
import org.jeesl.interfaces.model.system.security.doc.JeeslSecurityOnlineTutorial;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityArea;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityContext;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.jsf.handler.PositionListReorderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.io.StringUtil;

public class AbstractAdminSecurityContextBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,AT>,
											AT extends JeeslSecurityTemplate<L,D,C>,
											CTX extends JeeslSecurityContext<L,D>,
											M extends JeeslSecurityMenu<V,M>,
											AR extends JeeslSecurityArea<L,D,V>,
											OT extends JeeslSecurityOnlineTutorial<L,D,V>,
											OH extends JeeslSecurityOnlineHelp<V,?,?>,
											USER extends JeeslUser<R>>
			extends AbstractAdminSecurityBean<L,D,LOC,C,R,V,U,A,AT,CTX,M,AR,OT,OH,USER>
			implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityContextBean.class);
			
	private final List<CTX> contexts; public List<CTX> getContexts(){return contexts;}
	
	private CTX context; public CTX getContext() {return context;} public void setContext(CTX context) {this.context = context;}

	private final Class<CTX> cCtx;
	
	public AbstractAdminSecurityContextBean(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,CTX,M,AR,OT,OH,?,?,USER> fbSecurity, Class<CTX> cCtx)
	{
		super(fbSecurity);
		this.cCtx=cCtx;
		contexts = new ArrayList<CTX>();
	}
	
	public void postConstructSecurityContext(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fSecurity,
											JeeslTranslationBean<L,D,LOC> bTranslation,
											JeeslFacesMessageBean bMessage,
											JeeslSecurityBean<L,D,C,R,V,U,A,AT,M,USER> bSecurity)
	{
		categoryType = JeeslSecurityCategory.Type.role;
		super.postConstructSecurity(fSecurity,bTranslation,bMessage,bSecurity);
		reloadContexts();
	}
	
	public void addCategory() 
	{
		category = efCategory.create("", JeeslSecurityCategory.Type.role.toString());
		category.setName(efLang.createEmpty(localeCodes));
		category.setDescription(efDescription.createEmpty(localeCodes));
	}
	

	
	private void reloadContexts()
	{
		contexts.clear();
		contexts.addAll(fSecurity.all(cCtx));
	}

	//Role
	public void addContext() throws JeeslConstraintViolationException, InstantiationException, IllegalAccessException
	{
		logger.info(AbstractLogMessage.addEntity(fbSecurity.getClassRole()));
		context = cCtx.newInstance();
		context.setName(efLang.createEmpty(localeCodes));
		context.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void deleteContext() throws JeeslConstraintViolationException, JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(context));
		fSecurity.rm(context);
		context=null;
		reloadContexts();
	}
	
	public void saveContext() throws JeeslLockingException, JeeslNotFoundException, JeeslConstraintViolationException
	{
		logger.info(AbstractLogMessage.saveEntity(context));

		context = fSecurity.save(context);
		selectContext();
		reloadContexts();
	}
	
	public void selectContext()
	{
		logger.trace(AbstractLogMessage.selectEntity(context));
		context = fSecurity.find(cCtx,context);
		context = efLang.persistMissingLangs(fSecurity,localeCodes,context);
		context = efDescription.persistMissingLangs(fSecurity,localeCodes,context);		
	}
	
	protected void roleUpdatePerformed(){}
	
	
	public void reorderContexts() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fSecurity,contexts);}
}