package org.jeesl.controller.facade.module;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.module.JeeslLogframeFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.factory.builder.module.LfFactoryBuilder;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslLogFrameFacadeBean<	L extends JeeslLang, D extends JeeslDescription,
										LF extends JeeslLfLogframe<L, D, ?, ?>,
//										LFL extends JeeslLfLevel<?,?,LFL,?>,
//										LFT extends JeeslLfType<?,?,LFT,?>,
										TTG extends JeeslLfTargetTimeGroup<L,?>,
//										TTI extends JeeslLfTargetTimeInterval<?,?,TTI,?>,
										TTE extends JeeslLfTargetTimeElement<L,TTG>>
					extends JeeslFacadeBean
					implements JeeslLogframeFacade<L,D,LF,TTG,TTE>
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslAssetFacadeBean.class);

	private final LfFactoryBuilder<L,D,LF,TTG,TTE> fbLogFrame;

	public JeeslLogFrameFacadeBean(EntityManager em, final LfFactoryBuilder<L,D,LF,TTG,TTE> fbLogFrame)
	{
		super(em);
		this.fbLogFrame=fbLogFrame;
	}
}