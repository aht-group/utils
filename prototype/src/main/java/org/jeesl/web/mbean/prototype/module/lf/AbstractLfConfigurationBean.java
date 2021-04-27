package org.jeesl.web.mbean.prototype.module.lf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslLogframeFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.LfFactoryBuilder;
import org.jeesl.factory.ejb.module.lf.EjbLfConfigurationFactory;
import org.jeesl.interfaces.model.module.lf.JeeslLfConfiguration;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractLfConfigurationBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslTenantRealm<L,D,R,?>,
								LF extends JeeslLfLogframe<L,D,R,?,?,LFT>,
								LFT extends JeeslLfIndicatorType<L,D,R,LFT,?>,
								LFC extends JeeslLfConfiguration<LF,LFT>>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLfConfigurationBean.class);

	protected final LfFactoryBuilder<L,D,LF,?,?,?,?,LFC> fbLf;
	protected JeeslLogframeFacade<L,D,LF,?,?,?,?,LFC> fLf;
	
	private final EjbLfConfigurationFactory<LFC> efConfiguration;

	protected List<LFT> lfIndicatorTypes; public List<LFT> getLfIndicatorTypes() {return lfIndicatorTypes;}

	protected final Class<LFT> cLFT; public Class<LFT>  getClassLFT() {return cLFT;}

	private LF logframe; public LF getLogframe() {return logframe;} public void setLogframe(LF logframe) {this.logframe = logframe;}
	private List<LFC> lfConfigurations; public List<LFC> getLfConfigurations() {return lfConfigurations;}
	private LFC lfConfiguration; public LFC getLfConfiguration() {return lfConfiguration;} public void setLfConfiguration(LFC lfConfiguration) {this.lfConfiguration = lfConfiguration;}

	public AbstractLfConfigurationBean(LfFactoryBuilder<L,D,LF,?,?,?,?,LFC> fbLf,Class<LFT> cLFT)
	{
		super(fbLf.getClassL(),fbLf.getClassD());
		this.fbLf=fbLf;
		
		efConfiguration = fbLf.ejbLfConfiguration();

		this.lfIndicatorTypes = new ArrayList<LFT>();

		this.cLFT = cLFT;

	}

	protected void postConstructHd(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslLogframeFacade<L,D,LF,?,?,?,?,LFC> fLf)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fLf = fLf;
		this.lfIndicatorTypes = fLf.all(this.cLFT);
		reloadLfConfigurations();
	}

	protected void reloadLfConfigurations()
	{
		lfConfigurations = new ArrayList<>();
		if(logframe.getId() != 0){lfConfigurations = fLf.allForParent(fbLf.getClassLFC(), logframe);}

		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbLf.getClassLFC(),lfConfigurations));}
	}

	public void addLfConfiguration()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLf.getClassLFC()));}
		lfConfiguration =  efConfiguration.build();
		lfConfiguration.setLogframe(logframe);
	}

	public void saveLfConfiguration() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		logger.info(lfConfiguration.getLogframe().toString());
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(lfConfiguration));}
		LFT type = fLf.find(this.cLFT, lfConfiguration.getType().getId());
		lfConfiguration.setType(type);
		lfConfiguration = fLf.save(lfConfiguration);
		reloadLfConfigurations();
	}

	public void selectLfConfiguration(LFC selectdLfConfiguration) throws JeeslConstraintViolationException
	{
		lfConfiguration = fLf.find(fbLf.getClassLFC(),selectdLfConfiguration);
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(selectdLfConfiguration));}
	}

	public void deleteLfConfiguration() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(lfConfiguration));}
		fLf.rm(lfConfiguration);
		reloadLfConfigurations();
		reset();
		resetLfConfiguration();
	}

	public void resetLfConfiguration() {reset();}

	private void reset()
	{
		lfConfiguration=null; lfConfigurations=new ArrayList<>();
	}


}