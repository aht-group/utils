package org.jeesl.controller.facade.module;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.module.JeeslLogframeFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.factory.builder.module.LfFactoryBuilder;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslLogFrameFacadeBean<L extends JeeslLang,D extends JeeslDescription,R extends JeeslTenantRealm<L,D,R,?>,
									LF extends JeeslLfLogframe<L,D,R,I,IL,IT>,
									I extends JeeslLfIndicator<LF,IL,IT,IU,IV,TTG,V>,
									IL extends JeeslLfIndicatorLevel<L, D,R, IL, ?>,
									IT extends JeeslLfIndicatorType<L, D,R, IT, ?>,
									IU extends JeeslLfUnit<L,D,R,IU,?>,
									IV extends JeeslLfVerificationSource<L,D,R,IV,?>,
									TTG extends JeeslLfTimeGroup<L,?>,
									TTI extends JeeslLfTimeInterval<L,D,TTI,?>,
									TTE extends JeeslLfTimeElement<L,TTG>,
									V extends JeeslLfValue<I,VT,TTG,TTE>,
									VT extends JeeslLfValueType<L,D,R,VT,?>,
									LFC extends JeeslLfConfiguration<LF,?>>
					extends JeeslFacadeBean
					implements JeeslLogframeFacade<L,D,R,LF,I,IL,IT,IU,IV,TTG,TTI,TTE,V,VT,LFC>
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslAssetFacadeBean.class);

	protected final LfFactoryBuilder<L,D,R,LF,I,IL,IT,IU,IV,TTG,TTI,TTE,V,VT,LFC> fbLf;

	public JeeslLogFrameFacadeBean(EntityManager em, final LfFactoryBuilder<L,D,R,LF,I,IL,IT,IU,IV,TTG,TTI,TTE,V,VT,LFC> fbLf)
	{
		super(em);
		this.fbLf=fbLf;
	}
}