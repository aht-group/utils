package org.jeesl.web.mbean.prototype.module.lf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslLogframeFacade;
import org.jeesl.controller.handler.ui.UiSlotWidthHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.LfFactoryBuilder;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorLevel;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorMonitoring;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorType;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractLfBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslTenantRealm<L,D,R,?>,
								LF extends JeeslLfLogframe<L,D,R,?,?,?>,
								LFI extends JeeslLfIndicator<LF,LFL,LFT,TTG,LFM>,
								LFL extends JeeslLfIndicatorLevel<L, D,R, LFL, ?>,
								LFT extends JeeslLfIndicatorType<L, D,R, LFT, ?>,
								TTG extends JeeslLfTargetTimeGroup<L,?>,
								TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
								TTE extends JeeslLfTargetTimeElement<L,TTG>,
								LFM extends JeeslLfIndicatorMonitoring<LFI,TTG,TTE>
								>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLfBean.class);

	private final UiSlotWidthHandler slotHandler; public UiSlotWidthHandler getSlotHandler() {return slotHandler;}
	protected final LfFactoryBuilder<L,D,LF,LFI,TTG,TTE,LFM> fbLfIndicator;
	protected JeeslLogframeFacade<L,D,LF,LFI,TTG,TTE,LFM> fLfIndicator;

	protected List<LFI> lfIndicators; public List<LFI> getLfIndicators() {return lfIndicators;} public void setLfIndicators(List<LFI> lfIndicators) {this.lfIndicators = lfIndicators;}
	protected List<LFM> lfMonitorings; public List<LFM> getLfMonitorings() {return lfMonitorings;} public void setLfMonitorings(List<LFM> lfMonitorings) {this.lfMonitorings = lfMonitorings;}

	protected LFI lfIndicator; public LFI getLfIndicator() {return lfIndicator;} public void setLfIndicator(LFI lfIndicator) {this.lfIndicator = lfIndicator;}
	protected LFM lfMonitoring; public LFM getLfMonitoring() {return lfMonitoring;} public void setLfMonitoring(LFM lfMonitoring) {this.lfMonitoring = lfMonitoring;}

	protected List<LFL> lfIndicatorLevels; public List<LFL> getLfIndicatorLevels() {return lfIndicatorLevels;}
	protected List<LFT> lfIndicatorTypes; public List<LFT> getLfIndicatorTypes() {return lfIndicatorTypes;}
	protected List<TTI> intervalTypes; public List<TTI> getIntervalTypes() {return intervalTypes;} public void setIntervalTypes(List<TTI> intervalTypes) {this.intervalTypes = intervalTypes;}
	protected TTI interval; public TTI getInterval() {return interval;} public void setInterval(TTI interval) {this.interval = interval;}
	protected List<TTG> timeGroups; public List<TTG> getTimeGroups() {return timeGroups;} public void setTimeGroups(List<TTG> timeGroups) {this.timeGroups = timeGroups;}
	protected List<TTE> timeElements; public List<TTE> getTimeElements() {return timeElements;} public void setTimeElements(List<TTE> timeElements) {this.timeElements = timeElements;}

	protected Map<LFT,Map<LFL,List<LFI>>> lfTypesMap; public Map<LFT,Map<LFL,List<LFI>>> getLfTypesMap() {return lfTypesMap;}

	protected final Class<LFL> cLFL; public Class<LFL>  getClassLFL() {return cLFL;}
	protected final Class<LFT> cLFT; public Class<LFT>  getClassLFT() {return cLFT;}
	private Class cTTI; public Class<TTI>  getClassTTI() {return cTTI;}
	private LF logframe;


	private void findDefaultLogFrame()
	{
		try
		{
			logframe=fLfIndicator.fByName(fbLfIndicator.getClassLF(), "test");
		} catch (JeeslNotFoundException e)
		{
			logframe =  fbLfIndicator.ejbLfLogFrame().build();
			logframe.setName("test");
			try
			{
				logframe = fLfIndicator.save(logframe);
			} catch (JeeslConstraintViolationException | JeeslLockingException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public AbstractLfBean(LfFactoryBuilder<L,D,LF,LFI,TTG,TTE,LFM> fbLf,Class<LFL> cLFL,Class<LFT> cLFT,Class<TTI> cTTI)
	{
		super(fbLf.getClassL(),fbLf.getClassD());
		this.fbLfIndicator=fbLf;
		this.lfIndicators = new ArrayList<LFI>();
		this.lfIndicatorLevels = new ArrayList<LFL>();
		this.lfIndicatorTypes = new ArrayList<LFT>();
		this.intervalTypes = new ArrayList<TTI>();
		this.timeGroups = new ArrayList<TTG>();

		this.cLFL = cLFL;
		this.cLFT = cLFT;
		this.cTTI = cTTI;
		slotHandler = new UiSlotWidthHandler();
		slotHandler.set(12);
	}

	protected void postConstructHd(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslLogframeFacade<L,D,LF,LFI,TTG,TTE,LFM> fLf)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fLfIndicator= fLf;
		this.lfIndicatorLevels = fLf.all(this.cLFL);
		this.lfIndicatorTypes = fLf.all(this.cLFT);
		this.intervalTypes = fLf.all(this.cTTI);
		this.timeGroups = fLf.all(fbLfIndicator.getClassTTG());
		this.timeElements = fLf.all(fbLfIndicator.getClassTTE());

		findDefaultLogFrame();

		reloadLfIndicators();
	}

	protected void reloadLfIndicators()
	{
		lfIndicators = fLfIndicator.all(fbLfIndicator.getClassLFI());
		lfTypesMap = new HashMap<>();

		for (LFI lf : lfIndicators)
		{
			LFT type = lf.getType();
			LFL level = lf.getLevel();
			if(lfTypesMap.get(type)==null)
			{
				lfTypesMap.put(type, new HashMap<>());
				lfTypesMap.get(type).put(level, new ArrayList<>());
				lfTypesMap.get(type).get(level).add(lf);
			}
			else
			{
				if(lfTypesMap.get(type).get(level)==null)
				{
				lfTypesMap.get(type).put(level, new ArrayList<>());
				}
				lfTypesMap.get(type).get(level).add(lf);
			}
		}
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbLfIndicator.getClassLFI(),lfIndicators));}
	}

	public void addLfIndicator()
	{
		slotHandler.set(8,4);
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLfIndicator.getClassLF()));}
		lfIndicator =  fbLfIndicator.ejbLfIndicator().build();
		lfIndicator.setLogframe(logframe);
	}

	public void addLfIndicator(LFL level,LFT type)
	{
		slotHandler.set(8,4);
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLfIndicator.getClassLF()));}
		lfIndicator =  fbLfIndicator.ejbLfIndicator().build();
		lfIndicator.setLevel(fLfIndicator.find(this.cLFL,level));
		lfIndicator.setType(fLfIndicator.find(this.cLFT,type));
	}

	public void saveLfIndicator() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(lfIndicator));}
		lfIndicator.setLevel(fLfIndicator.find(this.cLFL,lfIndicator.getLevel()));
		lfIndicator.setType(fLfIndicator.find(this.cLFT,lfIndicator.getType()));
		lfIndicator = fLfIndicator.save(lfIndicator);
		reloadLfIndicators();
	}

	public void selectLfIndicator(LFI selectdLfIndicator) throws JeeslConstraintViolationException
	{
		slotHandler.set(8,4);
		lfIndicator = fLfIndicator.find(fbLfIndicator.getClassLFI(),selectdLfIndicator);
		reloadLfMonitorings();
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(selectdLfIndicator));}
	}

	public void deleteLfIndicator() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(lfIndicator));}
		fLfIndicator.rm(lfIndicator);
		reloadLfIndicators();
		reset(true,true);
		resetLfIndicator();
	}

	public void resetLfIndicator() {reset(true,true);slotHandler.set(12);}


	/**
	 * -----------------------------------------------------------------------------------
	 */
	protected void reloadLfMonitorings()
	{
		lfMonitorings = fLfIndicator.allForParent(fbLfIndicator.getClassLFM(),lfIndicator);

		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbLfIndicator.getClassLFM(),lfMonitorings));}
	}

	public void addLfMonitoring()
	{
		slotHandler.set(8,4);
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLfIndicator.getClassLF()));}
		lfMonitoring =  fbLfIndicator.ejbLfMonitoring().build();
		lfMonitoring.setIndicator(lfIndicator);
	}


	public void saveLfMonitoring() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(lfMonitoring));}
		lfMonitoring = fLfIndicator.save(lfMonitoring);
		//if(!lfIndicator.getMonitoringItems().contains(lfMonitoring)) {lfIndicator.getMonitoringItems().add(lfMonitoring);}
		//lfIndicator = fLfIndicator.save(lfIndicator);
		reloadLfMonitorings();
	}

	public void selectLfMonitoring() throws JeeslConstraintViolationException
	{
		lfMonitoring = fLfIndicator.find(fbLfIndicator.getClassLFM(),lfMonitoring);
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(lfMonitoring));}
	}

	public void selectLfMonitoring(LFM selectdLfMonitoring) throws JeeslConstraintViolationException
	{
		lfMonitoring = fLfIndicator.find(fbLfIndicator.getClassLFM(),selectdLfMonitoring);
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(selectdLfMonitoring));}
	}

	public void deleteLfMonitoring() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(lfMonitoring));}
		fLfIndicator.rm(lfMonitoring);
		//lfIndicator = fLfIndicator.save(lfIndicator);
		reloadLfMonitorings();
		reset(false,true);
	}

	public void resetLfMonitoring() {reset(false,true);}

	public MeterGaugeChartModel getTargetAchivedModel(LFM selectdLfMonitoring)
	{
		 List<Number> intervals = new ArrayList<Number>(){{
		 add(50);
		 add(80);
		 add(100);
		 add(200);
		 }};
		 MeterGaugeChartModel targetAchivedModel= new MeterGaugeChartModel((int) Math.round(selectdLfMonitoring.getTargetAchived()), intervals);
		targetAchivedModel.setGaugeLabel("%");
		targetAchivedModel.setSeriesColors("cc6666,E7E658,81ff00,66cc66");
		targetAchivedModel.setTitle("Target Achived");
	    return targetAchivedModel;
	}

	public MeterGaugeChartModel getBudgetModel(LFM selectdLfMonitoring)
	{
		List<Number> intervals = new ArrayList<Number>(){{
			 add(50);
			 add(80);
			 add(100);
			 add(200);
			 }};
		MeterGaugeChartModel budgetModel= new MeterGaugeChartModel((int) Math.round(selectdLfMonitoring.getBudgetExpences()*100/selectdLfMonitoring.getAllocatedBudget()), intervals);
		budgetModel.setSeriesColors("E7E658,81ff00,66cc66,cc6666");
		budgetModel.setGaugeLabel("%");
		budgetModel.setTitle("Budget Expenses");
		return budgetModel;
	}
	private void reset(boolean rInterval, boolean rMonitoring)
	{
		if(rInterval) {lfIndicator=null; lfMonitorings=new ArrayList<>();}
		if(rMonitoring) {lfMonitoring=null;}
	}


	public void resetTimeGroup() {reset(true, false);}
	public void resetElement() {reset(false,true);}

	public void onIntervalChanged()
	{
		this.timeGroups = fLfIndicator.allForParent(fbLfIndicator.getClassTTG(), interval);
	}



}