package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.lf.JeeslLfConfiguration;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorLevel;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorType;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfUnit;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfVerificationSource;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeElement;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeGroup;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeInterval;
import org.jeesl.interfaces.model.module.lf.value.JeeslLfValue;
import org.jeesl.interfaces.model.module.lf.value.JeeslLfValueType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;

public interface JeeslLogframeFacade <L extends JeeslLang,D extends JeeslDescription,R extends JeeslTenantRealm<L,D,R,?>,
									LF extends JeeslLfLogframe<L,D,R,I,IL,IT>,
									I extends JeeslLfIndicator<LF,IL,IT,IU,IV,TG,VM>,
									IL extends JeeslLfIndicatorLevel<L,D,R,IL,?>,
									IT extends JeeslLfIndicatorType<L,D,R, IT,?>,
									IU extends JeeslLfUnit<L,D,R,IU,?>,
									IV extends JeeslLfVerificationSource<L,D,R,IV,?>,
									TG extends JeeslLfTimeGroup<L,?>,
									TI extends JeeslLfTimeInterval<L,D,TI,?>,
									TE extends JeeslLfTimeElement<L,TG>,
									VM extends JeeslLfValue<I,VT,TG,TE>,
									VT extends JeeslLfValueType<L,D,VT,?>,
									LFC extends JeeslLfConfiguration<LF,?>>
			extends JeeslFacade
{

}