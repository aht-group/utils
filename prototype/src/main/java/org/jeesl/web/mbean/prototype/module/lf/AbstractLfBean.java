package org.jeesl.web.mbean.prototype.module.lf;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslLogframeFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
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
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLfBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslTenantRealm<L,D,R,?>,
								LF extends JeeslLfLogframe<L,D,R,LFI,IL,IT>,
								LFI extends JeeslLfIndicator<LF,IL,IT,TTG,LFM>,
								IL extends JeeslLfIndicatorLevel<L, D,R, IL, ?>,
								IT extends JeeslLfIndicatorType<L, D,R, IT, ?>,
								TTG extends JeeslLfTargetTimeGroup<L,TTI>,
								TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
								TTE extends JeeslLfTargetTimeElement<L,TTG>,
								LFM extends JeeslLfIndicatorMonitoring<LFI,TTG,TTE>,
								LFC extends JeeslLfConfiguration<LF,IT>>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLfBean.class);

	protected final LfFactoryBuilder<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fbLf;
	protected JeeslLogframeFacade<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fLf;

	private LF logframe; public LF getLogframe() {return logframe;} public void setLogframe(LF logframe) {this.logframe = logframe;}

	public AbstractLfBean(LfFactoryBuilder<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fbLf)
	{
		super(fbLf.getClassL(),fbLf.getClassD());
		this.fbLf=fbLf;
	}

	protected void postConstructHd(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslLogframeFacade<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fLf)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fLf = fLf;

		List<LF> list = fLf.all(fbLf.getClassLF(),1);
		if(list.isEmpty())
		{
			try
			{
				logframe = fbLf.ejbLfLogFrame().build();
				logframe.setName("JEESL Logframe");
				logframe = fLf.save(logframe);
				list =  fLf.all(fbLf.getClassLF(),1);
			}
			catch (JeeslConstraintViolationException | JeeslLockingException e) {e.printStackTrace();}
		}
		else
		{
			logframe = list.get(0);
		}
		logger.info("logframe id: -----------------------------------------------------" + logframe.getId());
	}

}