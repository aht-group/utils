package org.jeesl.web.mbean.prototype.module.lf;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslLogframeFacade;
import org.jeesl.factory.builder.module.LfFactoryBuilder;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLfTargetTimeBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslTenantRealm<L,D,R,?>, RREF extends EjbWithId,
								TTG extends JeeslLfTargetTimeGroup<TTI>,
								TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
								TTE extends JeeslLfTargetTimeElement<TTG>
								>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLfTargetTimeBean.class);
	
	protected final LfFactoryBuilder<L,D,LOC,R,TTG,TTI,TTE> fbLf;

	protected JeeslLogframeFacade<L,D,TTG,TTI,TTE> fLf;
	
	
	public AbstractLfTargetTimeBean(LfFactoryBuilder<L,D,LOC,R,TTG,TTI,TTE> fbLf)
	{
		super(fbLf.getClassL(),fbLf.getClassD());
		this.fbLf=fbLf;
		
		
	}

	protected void postConstructHd(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslLogframeFacade<L,D,TTG,TTI,TTE> fLf)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fLf=fLf;

	}

}