package org.jeesl.factory.txt.system.security;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtSecurityActionFactory <L extends JeeslLang,
										 D extends JeeslDescription,
										 C extends JeeslSecurityCategory<L,D>,
										 R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
										 V extends JeeslSecurityView<L,D,C,R,U,A>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
										 A extends JeeslSecurityAction<L,D,R,V,U,AT>,
										 AT extends JeeslSecurityTemplate<L,D,C>,
										 USER extends JeeslUser<R>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtSecurityActionFactory.class);
    
    public TxtSecurityActionFactory()
    {
    	
    } 
    
    public String code(V view, AT template)
    {
	    	StringBuffer sb = new StringBuffer();
	    	sb.append(view.getCode());
	    	sb.append(template.getCode().substring(template.getCode().lastIndexOf("."), template.getCode().length()));
	    	return sb.toString();
    }
}