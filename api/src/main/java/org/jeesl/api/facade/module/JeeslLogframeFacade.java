package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.lf.JeeslLfConfiguration;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorLevel;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorType;
import org.jeesl.interfaces.model.module.lf.monitoring.JeeslLfIndicatorMonitoring;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;

public interface JeeslLogframeFacade <L extends JeeslLang,D extends JeeslDescription,R extends JeeslTenantRealm<L,D,R,?>,
									LF extends JeeslLfLogframe<L,D,R,LFI,IL,IT>,
									LFI extends JeeslLfIndicator<LF,IL,IT,TTG,LFM>,
									IL extends JeeslLfIndicatorLevel<L, D,R, IL, ?>,
									IT extends JeeslLfIndicatorType<L, D,R, IT, ?>,
									TTG extends JeeslLfTargetTimeGroup<L,?>,
									TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
									TTE extends JeeslLfTargetTimeElement<L,TTG>,
									LFM extends JeeslLfIndicatorMonitoring<LFI,TTG,TTE>,
									LFC extends JeeslLfConfiguration<LF,?>>
			extends JeeslFacade
{

}