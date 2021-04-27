package org.jeesl.web.mbean.prototype.module.lf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
import org.jeesl.jsf.handler.PositionListReorderer;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.primefaces.event.SlideEndEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractLfTargetTimeBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractLfTargetTimeBean.class);

	protected final LfFactoryBuilder<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fbLf;
	protected JeeslLogframeFacade<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fLf;

	protected boolean uiGenerate;
	public boolean getUiGenerate()
	{
		if(getUiAllowGenerate() && uiGenerate)
		{
			if(timeGroup.getEndDate()!= null && timeGroup.getStartDate()!= null && timeGroup.getValues() > 0) {return false;}
			return true;
		}
		return false;
	}

	protected boolean uiAllowGenerate;
	public boolean getUiAllowGenerate()
	{
		if(timeGroup!=null && timeGroup.getId() > 0 && timeGroup.getValues()==0) {return true;}
		return false;
	}
	protected List<TTG> timeGroups; public List<TTG> getTimeGroups() {return timeGroups;} public void setTimeGroups(List<TTG> timeGroups) {this.timeGroups = timeGroups;}
	protected TTG timeGroup; public TTG getTimeGroup() {return timeGroup;} public void setTimeGroup(TTG timeGroup) {this.timeGroup = timeGroup;}
	protected List<TTE> elements; public List<TTE> getElements() {return elements;} public void setElements(List<TTE> elements) {this.elements = elements;}
	protected TTE element; public TTE getElement() {return element;} public void setElement(TTE element) {this.element = element;}
	protected List<TTI> intervalTypes; public List<TTI> getIntervalTypes() {return intervalTypes;} public void setIntervalTypes(List<TTI> intervalTypes) {this.intervalTypes = intervalTypes;}
	protected List<IntervalStatus> intervalConfigs; public List<IntervalStatus> getIntervalConfigs() {return intervalConfigs;} public void setIntervalConfigs(List<IntervalStatus> intervalConfigs) {this.intervalConfigs = intervalConfigs;}
	protected IntervalStatus intervalConfig; public IntervalStatus getIntervalConfig() {return intervalConfig;} public void setIntervalConfig(IntervalStatus intervalConfig) {this.intervalConfig = intervalConfig;}
	private int noOfIntervalRequired; public int getNoOfIntervalRequired() {return noOfIntervalRequired;} public void setNoOfIntervalRequired(int noOfIntervalRequired) {this.noOfIntervalRequired = noOfIntervalRequired;}


	public enum IntervalStatus
	{
	    FIRST_DAY_OF_YEAR("First Day of Year"),
	    LAST_DAY_OF_YEAR("Last Day of Year"),
	    FIRST_DAY_OF_QUARTER("First Day of Quarter"),
	    LAST_DAY_OF_QUARTER("Last Day of Quarter"),
	    FIRST_DAY_OF_MONTH("First Day of Month"),
	    LAST_DAY_OF_MONTH("Last Day of Month"),
	    FIRST_DAY_OF_WEEK("First Day of Week"),
	    LAST_DAY_OF_WEEK("Last Day of Week");

	    private String label;

	    private IntervalStatus(String label) {
	        this.label = label;
	    }

	    public String getLabel() {
	        return label;
	    }
	}

	public AbstractLfTargetTimeBean(LfFactoryBuilder<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fbLf)
	{
		super(fbLf.getClassL(),fbLf.getClassD());
		this.fbLf=fbLf;
		this.timeGroups = new ArrayList<TTG>();
		this.intervalTypes = new ArrayList<TTI>();
		intervalConfigs = new ArrayList<>();
	}

	protected void postConstructHd(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
			JeeslLogframeFacade<L,D,R,LF,LFI,IL,IT,TTG,TTI,TTE,LFM,LFC> fLf)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fLf=fLf;
		this.intervalTypes = fLf.all(fbLf.getClassTTI());
		reloadTimeGroups();
	}

	protected void reloadTimeGroups()
	{
		timeGroups = fLf.all(fbLf.getClassTTG());
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbLf.getClassTTG(),timeGroups));}
		uiGenerate = false;
	}

	public void addTimeGroup()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLf.getClassTTG()));}
		timeGroup = fbLf.ejbTargetTimeGroup().build();
		elements = new ArrayList<>();
		noOfIntervalRequired=1;
		reset(false,true);
	}

	public void saveTimeGroup() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(timeGroup));}
		timeGroup.setInterval(fLf.find(fbLf.getClassTTI(),timeGroup.getInterval()));
		timeGroup = fLf.save(timeGroup);
		reloadTimeGroups();
		reloadElements();
		resetElement();
	}

	public void selectTimeGroup() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(timeGroup));}
		timeGroup = fLf.find(fbLf.getClassTTG(),timeGroup);
		reloadElements();
		resetElement();
	}

	public void deleteTimeGroup() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(timeGroup));}
		elements = fLf.allForParent(fbLf.getClassTTE(),timeGroup);
		for (TTE tte : elements){fLf.rm(tte);}
		elements.clear();
		fLf.rm(timeGroup);
		reloadTimeGroups();
		reset(true,true);
		noOfIntervalRequired=1;
	}

	public void reorderTimeGroups() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fLf, timeGroups);}
	public void addElement()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLf.getClassTTE()));}
		if(element ==null || element.getId()!=0)
		{
			element = fbLf.ejbTargetTimeElement().build();
		}
	}

	public void selectElement()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(timeGroup));}
	}

	public void saveElement() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(element));}
		element.setGroup(timeGroup);
		element = fLf.save(element);
		elements.add(element);
		reloadElements();
		saveElementTimeGroup();
		reloadTimeGroups();
	}

	public void deleteElement() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(timeGroup));}
		fLf.rm(element);

		reloadElements();
		saveElementTimeGroup();

		reloadTimeGroups();
		reset(false,true);
	}

	private void saveElementTimeGroup() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(elements.size() > 0)
		{
			timeGroup.setStartDate(elements.get(0).getRecord());
			timeGroup.setEndDate(elements.get(elements.size()-1).getRecord());
		}
		if(elements.size() == 0)
		{
			timeGroup.setStartDate(null);
			timeGroup.setEndDate(null);
		}
		timeGroup.setValues(elements.size());
		timeGroup = fLf.save(timeGroup);
	}
	public void reloadElements()
	{
		elements = fLf.allForParent(fbLf.getClassTTE(),timeGroup);
		if(elements==null) {elements=new ArrayList<>();}
		uiGenerate = false;
		noOfIntervalRequired=1;
	}

	public void generateTimeGroup() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info("generateTimeGroup" + AbstractLogMessage.saveEntity(timeGroup));}
		saveTimeGroup();
		Calendar cal = Calendar.getInstance();
		Date record = timeGroup.getStartDate();
		TTI interval = timeGroup.getInterval();

		int count = 0;
		while (record.getTime() <= timeGroup.getEndDate().getTime() && !interval.getCode().equals("none"))
		{
			count++;
			//element = fbLf.ejbTargetTimeElement().build(timeGroup,elements);
			//element.setRecord(record);
			generateElement(record,String.valueOf(count));

			if(interval.getCode().equals("week")){cal.setTime(record); cal.add(Calendar.WEEK_OF_MONTH, 1);}
			if(interval.getCode().equals("month")){cal.setTime(record); cal.add(Calendar.MONTH, 1);}
			if(interval.getCode().equals("year")){cal.setTime(record); cal.add(Calendar.YEAR, 1);}
			if(interval.getCode().equals("quarter")){cal.setTime(record); cal.add(Calendar.MONTH, 3);}
			record = cal.getTime();
		}
		timeGroup.setValues(count);
		saveTimeGroup();
		reloadElements();
	}

	private  void generateElement(Date record, String name) throws JeeslConstraintViolationException, JeeslLockingException{
		logger.info("generating element with record  " + record.toString());
		addElement();
		element.setRecord(record);
		element.setGroup(timeGroup);
		element.setName(name);
		logger.info("saving element with record  " + record.toString());
		element = fLf.save(element);
		elements.add(element);
		logger.info("save" + record.toString());
	}
	private void reset(boolean rTimeGroup, boolean rElement)
	{
		if(rTimeGroup) {timeGroup=null;}
		if(rElement) {element=null;}
	}


	public void resetTimeGroup() {reset(true, false);}
	public void resetElement() {reset(false,true);}

	public void selectDay()
	{
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.clear(Calendar.MINUTE);
		c.clear(Calendar.SECOND);
		c.clear(Calendar.MILLISECOND);
		switch (intervalConfig)
		{
		case FIRST_DAY_OF_YEAR:
			c.set(Calendar.DAY_OF_YEAR, 1);
			break;
		case LAST_DAY_OF_YEAR:
			c.set(Calendar.DAY_OF_YEAR, 1);
			c.add(Calendar.MONTH, 12);
			c.add(Calendar.DATE, -1);
			break;
		case FIRST_DAY_OF_QUARTER:
			 c.set(Calendar.DAY_OF_MONTH, 1);
			 c.set(Calendar.MONTH, c.get(Calendar.MONTH)/3 * 3);
			 break;
		case LAST_DAY_OF_QUARTER:
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.MONTH, c.get(Calendar.MONTH)/3 * 3 + 2);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			break;
		case FIRST_DAY_OF_MONTH:
			 c.set(Calendar.DAY_OF_MONTH, 1);
			break;
		case LAST_DAY_OF_MONTH:
			c.add(Calendar.MONTH, 1);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.DATE, -1);
			break;
		case FIRST_DAY_OF_WEEK:
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
			break;
		case LAST_DAY_OF_WEEK:
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
			c.add(Calendar.WEEK_OF_YEAR, 1);
			c.add(Calendar.DATE, -1);
			break;
		default:
			break;
		}
		timeGroup.setStartDate(c.getTime());
		timeGroup.setEndDate(c.getTime());
	}

	public void selectIntervalType()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(timeGroup));}
		resetElement();
		if(this.elements.size() > 0)
		{
			timeGroup = fLf.find(fbLf.getClassTTG(),timeGroup);
			FacesContext context = FacesContext.getCurrentInstance();
	        FacesMessage message = new FacesMessage("Changing Interval not allowed");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        context.addMessage("msgTimeGroup", message);
		}
		else
		{
		timeGroup.setInterval(fLf.find(fbLf.getClassTTI(),timeGroup.getInterval()));
		updateUiTimeGroup();
		}
	}

	public void updateUiTimeGroup()
	{
		uiGenerate = true;
		intervalConfigs = new ArrayList<IntervalStatus>();
		TTI interval = fLf.find(fbLf.getClassTTI(),timeGroup.getInterval());
		if(interval.getCode().equals("week")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_WEEK); intervalConfigs.add(IntervalStatus.LAST_DAY_OF_WEEK);}
		if(interval.getCode().equals("month")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_MONTH);intervalConfigs.add(IntervalStatus.LAST_DAY_OF_MONTH);}
		if(interval.getCode().equals("year")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_YEAR);intervalConfigs.add(IntervalStatus.LAST_DAY_OF_YEAR);}
		if(interval.getCode().equals("quarter")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_QUARTER);intervalConfigs.add(IntervalStatus.LAST_DAY_OF_QUARTER);}
	}


    public void onChangeNoOfIntervalRequired()
	{
		Calendar cal = Calendar.getInstance();
		Date record = timeGroup.getStartDate();
		TTI interval = fLf.find(fbLf.getClassTTI(),timeGroup.getInterval());
		logger.info(interval.getCode());
		logger.info("noOfIntervalRequired:" + noOfIntervalRequired);
		noOfIntervalRequired--;
		logger.info("record:" + record.toString());

		if(interval.getCode().equals("week")){cal.setTime(record); cal.add(Calendar.WEEK_OF_MONTH, 1*noOfIntervalRequired);}
		if(interval.getCode().equals("month")){cal.setTime(record); cal.add(Calendar.MONTH, 1*noOfIntervalRequired);}
		if(interval.getCode().equals("year")){cal.setTime(record); cal.add(Calendar.YEAR, 1*noOfIntervalRequired);}
		if(interval.getCode().equals("quarter")){cal.setTime(record); cal.add(Calendar.MONTH, 3*noOfIntervalRequired);}

		logger.info("record:" + cal.getTime().toString());
		noOfIntervalRequired++;
		timeGroup.setEndDate(cal.getTime());
	}

    public void onSlideEnd(SlideEndEvent event)
 	{
 		Calendar cal = Calendar.getInstance();
 		Date record = timeGroup.getStartDate();
 		TTI interval = fLf.find(fbLf.getClassTTI(),timeGroup.getInterval());
 		logger.info(interval.getCode());
 		logger.info("event.getValue : " + event.getValue());
 		logger.info("noOfIntervalRequired:" + noOfIntervalRequired);
 		noOfIntervalRequired = event.getValue();
 		noOfIntervalRequired--;
 		logger.info("record:" + record.toString());

 		if(interval.getCode().equals("week")){cal.setTime(record); cal.add(Calendar.WEEK_OF_MONTH, 1*noOfIntervalRequired);}
 		if(interval.getCode().equals("month")){cal.setTime(record); cal.add(Calendar.MONTH, 1*noOfIntervalRequired);}
 		if(interval.getCode().equals("year")){cal.setTime(record); cal.add(Calendar.YEAR, 1*noOfIntervalRequired);}
 		if(interval.getCode().equals("quarter")){cal.setTime(record); cal.add(Calendar.MONTH, 3*noOfIntervalRequired);}

 		logger.info("record:" + cal.getTime().toString());
 		noOfIntervalRequired++;
 		timeGroup.setEndDate(cal.getTime());
 	}

}