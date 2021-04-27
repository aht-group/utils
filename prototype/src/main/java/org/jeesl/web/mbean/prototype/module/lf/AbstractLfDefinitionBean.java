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
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractLfDefinitionBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractLfDefinitionBean.class);

	private final UiSlotWidthHandler slotHandler; public UiSlotWidthHandler getSlotHandler() {return slotHandler;}
	protected final LfFactoryBuilder<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fbLf;
	protected JeeslLogframeFacade<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fLf;

	protected List<LFI> indicators; public List<LFI> getIndicators() {return indicators;} public void setIndicators(List<LFI> indicators) {this.indicators = indicators;}
	protected List<LFM> monitorings; public List<LFM> getMonitorings() {return monitorings;} public void setMonitorings(List<LFM> monitorings) {this.monitorings = monitorings;}

	protected LFI indicator; public LFI getIndicator() {return indicator;} public void setIndicator(LFI indicator) {this.indicator = indicator;}
	protected LFM monitoring; public LFM getMonitoring() {return monitoring;} public void setMonitoring(LFM monitoring) {this.monitoring = monitoring;}

	protected List<IL> indicatorLevels; public List<IL> getIndicatorLevels() {return indicatorLevels;}
	protected List<IT> indicatorTypes; public List<IT> getIndicatorTypes() {return indicatorTypes;}
	protected List<TTI> intervalTypes; public List<TTI> getIntervalTypes() {return intervalTypes;} public void setIntervalTypes(List<TTI> intervalTypes) {this.intervalTypes = intervalTypes;}
	protected TTI interval; public TTI getInterval() {return interval;} public void setInterval(TTI interval) {this.interval = interval;}
	protected List<TTG> timeGroups; public List<TTG> getTimeGroups() {return timeGroups;} public void setTimeGroups(List<TTG> timeGroups) {this.timeGroups = timeGroups;}
	protected List<TTE> timeElements; public List<TTE> getTimeElements() {return timeElements;} public void setTimeElements(List<TTE> timeElements) {this.timeElements = timeElements;}

	protected Map<IT,Map<IL,List<LFI>>> typesMap; public Map<IT,Map<IL,List<LFI>>> getTypesMap() {return typesMap;}

	private LF logframe; public LF getLogframe() {return logframe;} public void setLogframe(LF logframe) {this.logframe = logframe;}

	public AbstractLfDefinitionBean(LfFactoryBuilder<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fbLf)
	{
		super(fbLf.getClassL(),fbLf.getClassD());
		this.fbLf=fbLf;

		this.indicators = new ArrayList<LFI>();
		this.indicatorLevels = new ArrayList<IL>();
		this.indicatorTypes = new ArrayList<IT>();
		this.intervalTypes = new ArrayList<TTI>();
		this.timeGroups = new ArrayList<TTG>();

		slotHandler = new UiSlotWidthHandler();
		slotHandler.set(12);
	}

	protected void postConstructHd(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,JeeslLogframeFacade<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fLf)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fLf = fLf;
		this.indicatorLevels = fLf.all(fbLf.getClassIL());
		this.indicatorTypes = fLf.all(fbLf.getClassIT());
		this.intervalTypes = fLf.all(fbLf.getClassTTI());
		this.timeGroups = fLf.all(fbLf.getClassTTG());
		this.timeElements = fLf.all(fbLf.getClassTTE());

		reloadIndicators();
	}

	protected void reloadIndicators()
	{
		indicators = fLf.all(fbLf.getClassLFI());
		typesMap = new HashMap<>();

		for (LFI lf : indicators)
		{
			IT type = lf.getType();
			IL level = lf.getLevel();
			if(typesMap.get(type)==null)
			{
				typesMap.put(type, new HashMap<>());
				typesMap.get(type).put(level, new ArrayList<>());
				typesMap.get(type).get(level).add(lf);
			}
			else
			{
				if(typesMap.get(type).get(level)==null)
				{
				typesMap.get(type).put(level, new ArrayList<>());
				}
				typesMap.get(type).get(level).add(lf);
			}
		}
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbLf.getClassLFI(),indicators));}
	}

	public void addIndicator()
	{
		slotHandler.set(8,4);
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLf.getClassLF()));}
		indicator =  fbLf.ejbLfIndicator().build();
		indicator.setLogframe(logframe);
	}

	public void addIndicator(IL level,IT type)
	{
		slotHandler.set(8,4);
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLf.getClassLF()));}
		indicator =  fbLf.ejbLfIndicator().build();
		indicator.setLevel(fLf.find(fbLf.getClassIL(),level));
		indicator.setType(fLf.find(fbLf.getClassIT(),type));
	}

	public void saveIndicator() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(indicator));}
		indicator.setLevel(fLf.find(fbLf.getClassIL(),indicator.getLevel()));
		indicator.setType(fLf.find(fbLf.getClassIT(),indicator.getType()));
		indicator = fLf.save(indicator);
		reloadIndicators();
	}

	public void selectIndicator(LFI selectdIndicator) throws JeeslConstraintViolationException
	{
		slotHandler.set(8,4);
		indicator = fLf.find(fbLf.getClassLFI(),selectdIndicator);
		reloadMonitorings();
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(selectdIndicator));}
	}

	public void deleteIndicator() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(indicator));}
		fLf.rm(indicator);
		reloadIndicators();
		reset(true,true);
		resetIndicator();
	}

	public void resetIndicator() {reset(true,true);slotHandler.set(12);}


	/**
	 * -----------------------------------------------------------------------------------
	 */
	protected void reloadMonitorings()
	{
		monitorings = fLf.allForParent(fbLf.getClassLFM(),indicator);

		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbLf.getClassLFM(),monitorings));}
	}

	public void addMonitoring()
	{
		slotHandler.set(8,4);
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLf.getClassLF()));}
		monitoring =  fbLf.ejbLfMonitoring().build();
		monitoring.setIndicator(indicator);
	}


	public void saveMonitoring() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(monitoring));}
		monitoring = fLf.save(monitoring);
		//if(!Indicator.getMonitoringItems().contains(Monitoring)) {Indicator.getMonitoringItems().add(Monitoring);}
		//Indicator = fLf.save(Indicator);
		reloadMonitorings();
	}

	public void selectMonitoring() throws JeeslConstraintViolationException
	{
		monitoring = fLf.find(fbLf.getClassLFM(),monitoring);
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(monitoring));}
	}

	public void selectMonitoring(LFM selectdMonitoring) throws JeeslConstraintViolationException
	{
		monitoring = fLf.find(fbLf.getClassLFM(),selectdMonitoring);
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(selectdMonitoring));}
	}

	public void deleteMonitoring() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(monitoring));}
		fLf.rm(monitoring);
		//Indicator = fLf.save(Indicator);
		reloadMonitorings();
		reset(false,true);
	}

	public void resetMonitoring() {reset(false,true);}

	public MeterGaugeChartModel getTargetAchivedModel(LFM selectdMonitoring)
	{
		 List<Number> intervals = new ArrayList<Number>(){{
		 add(50);
		 add(80);
		 add(100);
		 add(200);
		 }};
		 MeterGaugeChartModel targetAchivedModel= new MeterGaugeChartModel((int) Math.round(selectdMonitoring.getTargetAchived()), intervals);
		targetAchivedModel.setGaugeLabel("%");
		targetAchivedModel.setSeriesColors("cc6666,E7E658,81ff00,66cc66");
		targetAchivedModel.setTitle("Target Achived");
	    return targetAchivedModel;
	}

	public MeterGaugeChartModel getBudgetModel(LFM selectdMonitoring)
	{
		List<Number> intervals = new ArrayList<Number>(){{
			 add(50);
			 add(80);
			 add(100);
			 add(200);
			 }};
		MeterGaugeChartModel budgetModel= new MeterGaugeChartModel((int) Math.round(selectdMonitoring.getBudgetExpences()*100/selectdMonitoring.getAllocatedBudget()), intervals);
		budgetModel.setSeriesColors("E7E658,81ff00,66cc66,cc6666");
		budgetModel.setGaugeLabel("%");
		budgetModel.setTitle("Budget Expenses");
		return budgetModel;
	}
	private void reset(boolean rInterval, boolean rMonitoring)
	{
		if(rInterval) {indicator=null; monitorings=new ArrayList<>();}
		if(rMonitoring) {monitoring=null;}
	}


	public void resetTimeGroup() {reset(true, false);}
	public void resetElement() {reset(false,true);}

	public void onIntervalChanged()
	{
		this.timeGroups = fLf.allForParent(fbLf.getClassTTG(), interval);
	}



}