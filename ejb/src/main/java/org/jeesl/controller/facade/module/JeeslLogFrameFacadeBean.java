package org.jeesl.controller.facade.module;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.module.JeeslLogframeFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.factory.builder.module.LfFactoryBuilder;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslLogFrameFacadeBean<L extends JeeslLang, D extends JeeslDescription,
										R extends JeeslTenantRealm<L,D,R,?>,
										LF extends JeeslLfLogframe,
										TTG extends JeeslLfTargetTimeGroup<L,TTI>,
										TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
										TTE extends JeeslLfTargetTimeElement<L,TTG>>
					extends JeeslFacadeBean
					implements JeeslLogframeFacade<L,D,R,LF,TTG,TTI,TTE>
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslAssetFacadeBean.class);

	private final LfFactoryBuilder<L,D,R,LF,TTG,TTI,TTE> fbLogFrame;

	public JeeslLogFrameFacadeBean(EntityManager em, final LfFactoryBuilder<L,D,R,LF,TTG,TTI,TTE> fbLogFrame)
	{
		super(em);
		this.fbLogFrame=fbLogFrame;
	}

}