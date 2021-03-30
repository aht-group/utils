package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcActivity;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcScope;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;

public interface JeeslMdcFacade <L extends JeeslLang, D extends JeeslDescription, R extends JeeslTenantRealm<L,D,R,?>, 
									ACTIVITY extends JeeslMdcActivity<R,SCOPE,STATUS,?>,
									SCOPE extends JeeslMdcScope<L,D,R,SCOPE,?>,
									STATUS extends JeeslMdcStatus<L,D,STATUS,?>>
			extends JeeslFacade
{	

}