package org.jeesl.web.mbean.prototype.user;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import org.jeesl.api.facade.core.JeeslUserFacade;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.util.JeeslRememberMe;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithEmail;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractRememberMeBean <L extends JeeslLang,D extends JeeslDescription,
										C extends JeeslSecurityCategory<L,D>,
										R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
										V extends JeeslSecurityView<L,D,C,R,U,A>,
										U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
										A extends JeeslSecurityAction<L,D,R,V,U,AT>,
										AT extends JeeslSecurityTemplate<L,D,C>,
										USER extends JeeslUser<R>,
										REM extends JeeslRememberMe<USER>>
		extends AbstractAdminBean<L,D>
		implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractRememberMeBean.class);
	private static final long serialVersionUID = 1L;
	
	protected boolean logOnInfo = false;
	private boolean dummy; public boolean isDummy() {return dummy;} public void setDummy(boolean dummy) {this.dummy = dummy;}
	
	public AbstractRememberMeBean(final Class<L> cL, final Class<D> cD)
	{
		super(cL,cD);
	}
	
	protected void readCookie(Class<REM> cRem, JeeslUserFacade<USER> fUser, String cookieName)
	{
		Map<String,Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
		if(cookies.containsKey(cookieName))
		{
			try
			{
				if(logOnInfo){logger.info("Cookie found");}
				Cookie cookie = (Cookie) cookies.get(cookieName);
				REM rem = fUser.fByCode(cRem, cookie.getValue());
				if(logOnInfo){logger.info("REM found");}
				if(rem.getUser() instanceof EjbWithEmail)
				{
					if(logOnInfo){logger.info("ASSIGN");}
					EjbWithEmail email = (EjbWithEmail)rem.getUser();
					preSelect(email.getEmail());
				}
				else{if(logOnInfo){logger.info("NOT");}}
			}
			catch (JeeslNotFoundException e){if(logOnInfo){logger.info(e.getMessage());}}
		}
		else{if(logOnInfo){logger.info("Cookie NOT found");}}
	}
	
	protected void preSelect(String userName){}
}