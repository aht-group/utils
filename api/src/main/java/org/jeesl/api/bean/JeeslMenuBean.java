package org.jeesl.api.bean;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslIdentity;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;

public interface JeeslMenuBean<L extends JeeslLang, D extends JeeslDescription, 
								R extends JeeslSecurityRole<L,D,?,V,U,A,USER>,
								V extends JeeslSecurityView<L,D,?,R,U,A>,
								U extends JeeslSecurityUsecase<L,D,?,R,V,A>,
								A extends JeeslSecurityAction<L,D,R,V,U,?>,
								M extends JeeslSecurityMenu<V,M>,
								USER extends JeeslUser<R>,
								I extends JeeslIdentity<R,V,U,A,USER>>
{	
	void updateLocale(String localeCode);
}