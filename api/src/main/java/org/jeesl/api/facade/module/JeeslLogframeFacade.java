package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;

public interface JeeslLogframeFacade <L extends JeeslLang, D extends JeeslDescription,
									R extends JeeslTenantRealm<L,D,R,?>,
									TTG extends JeeslLfTargetTimeGroup<L,TTI>,
									TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
									TTE extends JeeslLfTargetTimeElement<L,TTG>
									>
			extends JeeslFacade
{

}