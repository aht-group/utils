package org.jeesl.controller.facade.module;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.module.JeeslLogframeFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.factory.builder.module.LfFactoryBuilder;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorMonitoring;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslLogFrameFacadeBean<	L extends JeeslLang, D extends JeeslDescription,
										LF extends JeeslLfLogframe<L, D,?,?,?,?>,
										LFI extends JeeslLfIndicator<LF, ?, ?,TTG,LFM>,
										TTG extends JeeslLfTargetTimeGroup<L,?>,
										TTE extends JeeslLfTargetTimeElement<L,TTG>,
										LFM extends JeeslLfIndicatorMonitoring<LFI,TTG,TTE>>
					extends JeeslFacadeBean
					implements JeeslLogframeFacade<L,D,LF,LFI,TTG,TTE,LFM>
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslAssetFacadeBean.class);

	private final LfFactoryBuilder<L,D,LF,LFI,TTG,TTE,LFM> fbLogFrame;

	public JeeslLogFrameFacadeBean(EntityManager em, final LfFactoryBuilder<L,D,LF,LFI,TTG,TTE,LFM> fbLogFrame)
	{
		super(em);
		this.fbLogFrame=fbLogFrame;
	}
}