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
import org.jeesl.controller.handler.module.lf.IntervalCalendarHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.LfFactoryBuilder;
import org.jeesl.interfaces.bean.lf.TargetTimeBean;
import org.jeesl.interfaces.model.module.lf.JeeslLfConfiguration;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorLevel;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorType;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfUnit;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfVerificationSource;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeElement;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeGroup;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeInterval;
import org.jeesl.interfaces.model.module.lf.value.JeeslLfValue;
import org.jeesl.interfaces.model.module.lf.value.JeeslLfValueType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.jsf.handler.PositionListReorderer;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractLfTargetTimeBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
												R extends JeeslTenantRealm<L,D,R,?>,
												LF extends JeeslLfLogframe<L,D,R,LFI,IL,IT>,
												LFI extends JeeslLfIndicator<LF,IL,IT,IU,IV,TTG,LFM>,
												IL extends JeeslLfIndicatorLevel<L, D,R, IL, ?>,
												IT extends JeeslLfIndicatorType<L, D,R, IT, ?>,
												IU extends JeeslLfUnit<L,D,R,IU,?>,
												IV extends JeeslLfVerificationSource<L,D,R,IV,?>,
												TTG extends JeeslLfTimeGroup<L,TTI>,
												TTI extends JeeslLfTimeInterval<L,D,TTI,?>,
												TTE extends JeeslLfTimeElement<L,TTG>,
												LFM extends JeeslLfValue<LFI,VT,TTG,TTE>,
												VT extends JeeslLfValueType<L,D,VT,?>,
												LFC extends JeeslLfConfiguration<LF,IT>>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable,TargetTimeBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLfTargetTimeBean.class);

	protected final LfFactoryBuilder<L,D,R,LF,LFI,IL,IT,IU,IV,TTG,TTI,TTE,LFM,VT,LFC> fbLf;
	protected JeeslLogframeFacade<L,D,R,LF,LFI,IL,IT,IU,IV,TTG,TTI,TTE,LFM,VT,LFC> fLf;

	protected IntervalCalendarHandler<TTI> intervalCalendarHandler; public IntervalCalendarHandler<TTI> getIntervalCalendarHandler() {return intervalCalendarHandler;}
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

	public AbstractLfTargetTimeBean(LfFactoryBuilder<L,D,R,LF,LFI,IL,IT,IU,IV,TTG,TTI,TTE,LFM,VT,LFC> fbLf)
	{
		super(fbLf.getClassL(),fbLf.getClassD());
		this.fbLf=fbLf;
		this.timeGroups = new ArrayList<TTG>();
		this.intervalTypes = new ArrayList<TTI>();
	}

	protected void postConstructHd(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslLogframeFacade<L,D,R,LF,LFI,IL,IT,IU,IV,TTG,TTI,TTE,LFM,VT,LFC> fLf)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fLf=fLf;
		intervalCalendarHandler = new IntervalCalendarHandler<TTI>(this);
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
		intervalCalendarHandler.initIntervalSize();
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
		intervalCalendarHandler.initIntervalSize();
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
		intervalCalendarHandler.initIntervalSize();
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
		TTI interval = fLf.find(fbLf.getClassTTI(),timeGroup.getInterval());
		intervalCalendarHandler.changeInterval(interval);
	}


/*-----------------------------------------------------------------------*/
    @Override public void updateTimeGroupStartAndEndDate(Date date)
    {
    	timeGroup.setStartDate(date);
    	timeGroup.setEndDate(date);
    }

    @Override public Date getTimeGroupStartDate()
    {
    	return timeGroup.getStartDate();
    }

    @Override public void updateTimeGroupEndDate(Date date)
    {
    	timeGroup.setEndDate(date);
    }

}