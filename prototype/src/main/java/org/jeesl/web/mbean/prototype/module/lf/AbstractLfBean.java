package org.jeesl.web.mbean.prototype.module.lf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslLogframeFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.LfFactoryBuilder;
import org.jeesl.interfaces.model.module.lf.JeeslLfLevel;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.JeeslLfType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractLfBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslTenantRealm<L,D,R,?>,
								LF extends JeeslLfLogframe<L,D,LFL,LFT>,
								LFL extends JeeslLfLevel<L, D, LFL, ?>,
								LFT extends JeeslLfType<L, D, LFT, ?>
								>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLfBean.class);

	protected final LfFactoryBuilder<L,D,LF,?,?> fbLogFrame;
	protected JeeslLogframeFacade<L,D,LF,?,?> fLogFrame;

	protected List<LF> logFrames; public List<LF> getLogFrames() {return logFrames;} public void setLogFrames(List<LF> logFrames) {this.logFrames = logFrames;}
	protected LF logFrame; public LF getLogFrame() {return logFrame;} public void setLogFrame(LF logFrame) {this.logFrame = logFrame;}

	protected List<LFL> logFrameLevels; public List<LFL> getLogFrameLevels() {return logFrameLevels;}
	protected List<LFT> logFrameTypes; public List<LFT> getLogFrameTypes() {return logFrameTypes;}

	protected final Class<LFL> cLFL; public Class<LFL>  getClassLFL() {return cLFL;}
	protected final Class<LFT> cLFT; public Class<LFT>  getClassLFT() {return cLFT;}

	public AbstractLfBean(LfFactoryBuilder<L,D,LF,?,?> fbLf,Class<LFL> cLFL,Class<LFT> cLFT)
	{
		super(fbLf.getClassL(),fbLf.getClassD());
		this.fbLogFrame=fbLf;
		this.logFrames = new ArrayList<LF>();
		this.logFrameLevels = new ArrayList<LFL>();
		this.logFrameTypes = new ArrayList<LFT>();
		this.cLFL = cLFL;
		this.cLFT = cLFT;
	}

	protected void postConstructHd(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslLogframeFacade<L,D,LF,?,?> fLf)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fLogFrame= fLf;
		this.logFrameLevels = fLf.all(this.cLFL);
		this.logFrameTypes = fLf.all(this.cLFT);
		reloadLogFrames();
	}

	protected void reloadLogFrames()
	{
		logFrames = fLogFrame.all(fbLogFrame.getClassLF());
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbLogFrame.getClassLF(),logFrames));}
	}

	public void addLogFrame()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLogFrame.getClassLF()));}
		logFrame = fbLogFrame.ejbLogFrame().build();
	}

	public void saveLogFrame() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(logFrame));}
		logFrame.setLevel(fLogFrame.find(this.cLFL,logFrame.getLevel()));
		logFrame.setType(fLogFrame.find(this.cLFT,logFrame.getType()));
		logFrame = fLogFrame.save(logFrame);
		reloadLogFrames();
	}

	public void selectLogFrame() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(logFrame));}
		logFrame = fLogFrame.find(fbLogFrame.getClassLF(),logFrame);
	}

	public void deleteLogFrame() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(logFrame));}
		fLogFrame.rm(logFrame);
		reloadLogFrames();
		resetLogFrame();
	}

	public void resetLogFrame() {logFrame=null;}


}