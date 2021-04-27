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
import org.jeesl.interfaces.model.module.lf.monitoring.JeeslLfIndicatorMonitoring;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslLogFrameFacadeBean<L extends JeeslLang,D extends JeeslDescription,R extends JeeslTenantRealm<L,D,R,?>,
									LF extends JeeslLfLogframe<L,D,R,LFI,IL,IT>,
									LFI extends JeeslLfIndicator<LF,IL,IT,TTG,LFM>,
									IL extends JeeslLfIndicatorLevel<L, D,R, IL, ?>,
									IT extends JeeslLfIndicatorType<L, D,R, IT, ?>,
									TTG extends JeeslLfTargetTimeGroup<L,?>,
									TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
									TTE extends JeeslLfTargetTimeElement<L,TTG>,
									LFM extends JeeslLfIndicatorMonitoring<LFI,TTG,TTE>,
									LFC extends JeeslLfConfiguration<LF,?>>
					extends JeeslFacadeBean
					implements JeeslLogframeFacade<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC>
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslAssetFacadeBean.class);

	protected final LfFactoryBuilder<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fbLf;

	public JeeslLogFrameFacadeBean(EntityManager em, final LfFactoryBuilder<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fbLf)
	{
		super(em);
		this.fbLf=fbLf;
	}
}