package org.jeesl.controller.handler.module.lf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.bean.lf.LfTimeBean;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeElement;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeGroup;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IntervalCalendarHandler<TI extends JeeslLfTimeInterval<?,?,TI,?>, TG extends JeeslLfTimeGroup<?,TI>, TE extends JeeslLfTimeElement<?,TG>> implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(IntervalCalendarHandler.class);
	private boolean debug = true;
	private final LfTimeBean<TI, TG, TE> bean;

	protected List<IntervalStatus> intervalConfigs; public List<IntervalStatus> getIntervalConfigs() {return intervalConfigs;} public void setIntervalConfigs(List<IntervalStatus> intervalConfigs) {this.intervalConfigs = intervalConfigs;}
	protected IntervalStatus intervalConfig; public IntervalStatus getIntervalConfig() {return intervalConfig;} public void setIntervalConfig(IntervalStatus intervalConfig) {this.intervalConfig = intervalConfig;}

	private int noOfIntervalRequired; public int getNoOfIntervalRequired() {return noOfIntervalRequired;} public void setNoOfIntervalRequired(int noOfIntervalRequired) {this.noOfIntervalRequired = noOfIntervalRequired;}
	private TI interval;

	public enum IntervalStatus
	{
	    FIRST_DAY_OF_YEAR("First Day of Year"),
	    LAST_DAY_OF_YEAR("Last Day of Year"),
	    FIRST_DAY_OF_QUARTER("First Day of Quarter"),
	    LAST_DAY_OF_QUARTER("Last Day of Quarter"),
	    FIRST_DAY_OF_MONTH("First Day of Month"),
	    LAST_DAY_OF_MONTH("Last Day of Month"),
	    FIRST_DAY_OF_WEEK("First Day of Week"),
	    LAST_DAY_OF_WEEK("Last Day of Week"),
		SELECTED_DAY("Repeat on Selected Day");

	    private String label;

	    private IntervalStatus(String label) {
	        this.label = label;
	    }

	    public String getLabel() {
	        return label;
	    }
	}

	public IntervalCalendarHandler(LfTimeBean<TI, TG, TE> bean)
	{
		this.bean = bean;
		intervalConfigs = new ArrayList<>();
		initIntervalSize();
	}

	public void initIntervalSize()
	{
		noOfIntervalRequired=1;
	}

	public void selectDay()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
		bean.updateTimeGroupStartAndEndDate(translateDateToSelectedInterval(cal.getTime()));
	}


    public void onChangeNoOfIntervalRequired()
	{
		Calendar cal = Calendar.getInstance();
		Date record = bean.getTimeGroupStartDate();
		logger.info(interval.getCode());
		logger.info("noOfIntervalRequired:" + noOfIntervalRequired);
		noOfIntervalRequired--;
		logger.info("record:" + record.toString());

		if(interval.getCode().equals("year"))
		{
			cal.setTime(record);
			cal.add(Calendar.YEAR, 1*noOfIntervalRequired);
			switch(intervalConfig)
			{
			case FIRST_DAY_OF_YEAR:
				bean.updateTimeGroupEndDate(getFirstDayOfYear(cal.getTime()));
				break;
			case LAST_DAY_OF_YEAR:
				bean.updateTimeGroupEndDate(getLastDayOfYear(cal.getTime()));
				break;
			default:
				bean.updateTimeGroupEndDate(cal.getTime());
				break;
			}
		}

		if(interval.getCode().equals("quarter"))
		{
			cal.setTime(record);
			cal.add(Calendar.MONTH, 3*noOfIntervalRequired);
			bean.updateTimeGroupEndDate(translateDateToSelectedInterval(cal.getTime()));
		}

		if(interval.getCode().equals("month"))
		{
			cal.setTime(record);
			cal.add(Calendar.MONTH, 1*noOfIntervalRequired);
			bean.updateTimeGroupEndDate(translateDateToSelectedInterval(cal.getTime()));
		}

		if(interval.getCode().equals("week"))
		{
			cal.setTime(record);
			cal.add(Calendar.WEEK_OF_MONTH, 1*noOfIntervalRequired);
			bean.updateTimeGroupEndDate(translateDateToSelectedInterval(cal.getTime()));
		}

		logger.info("record:" + cal.getTime().toString());
		noOfIntervalRequired++;
	}


	public void changeInterval(TI interval)
	{
		this.interval=interval;
		intervalConfigs = new ArrayList<IntervalStatus>();

		if(interval.getCode().equals("week")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_WEEK); intervalConfigs.add(IntervalStatus.LAST_DAY_OF_WEEK); intervalConfigs.add(IntervalStatus.SELECTED_DAY);}
		if(interval.getCode().equals("month")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_MONTH);intervalConfigs.add(IntervalStatus.LAST_DAY_OF_MONTH); intervalConfigs.add(IntervalStatus.SELECTED_DAY);}
		if(interval.getCode().equals("year")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_YEAR);intervalConfigs.add(IntervalStatus.LAST_DAY_OF_YEAR); intervalConfigs.add(IntervalStatus.SELECTED_DAY);}
		if(interval.getCode().equals("quarter")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_QUARTER);intervalConfigs.add(IntervalStatus.LAST_DAY_OF_QUARTER); intervalConfigs.add(IntervalStatus.SELECTED_DAY);}
	}

	public List<TE> generateTimeElements(TG timeGroup) throws JeeslConstraintViolationException, JeeslLockingException
	{
		List<TE> elements = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		Date record = translateDateToSelectedInterval(timeGroup.getStartDate()); logger.info("Start date: " + timeGroup.getStartDate() + " ::  record" + record);
		TI interval = timeGroup.getInterval(); logger.info("interval" + interval);

		int count = 0;
		while (record.getTime() <= timeGroup.getEndDate().getTime() && !interval.getCode().equals("none"))
		{
			count++;
			elements.add(createElement(record,String.valueOf(count), timeGroup));

			if(interval.getCode().equals("year"))
			{
				cal.setTime(record);
				cal.add(Calendar.YEAR, 1);
				record = translateDateToSelectedInterval(cal.getTime());
			}

			if(interval.getCode().equals("quarter"))
			{
				cal.setTime(record);
				cal.add(Calendar.MONTH, 3);
				record = translateDateToSelectedInterval(cal.getTime());
			}

			if(interval.getCode().equals("month"))
			{
				cal.setTime(record);
				cal.add(Calendar.MONTH, 1);
				record = translateDateToSelectedInterval(cal.getTime());
			}

			if(interval.getCode().equals("week"))
			{
				cal.setTime(record);
				cal.add(Calendar.WEEK_OF_MONTH, 1);
				record = translateDateToSelectedInterval(cal.getTime());
			}
		}
		return elements;
	}

	private  TE createElement(Date record, String name, TG timeGroup)
	{
		logger.info("generating element with record  " + record.toString());
		TE element = bean.createNewTimeElement();
		element.setRecord(record);
		element.setGroup(timeGroup);
		element.setName(name);
		return element;
	}


	private Date translateDateToSelectedInterval(Date date)
	{
		switch (intervalConfig)
		{
		case FIRST_DAY_OF_YEAR:
			return getFirstDayOfYear(date);
		case LAST_DAY_OF_YEAR:
			return getLastDayOfYear(date);
		case FIRST_DAY_OF_QUARTER:
			return getFirstDayOfQuarter(date);
		case LAST_DAY_OF_QUARTER:
			return getLastDayOfQuarter(date);
		case FIRST_DAY_OF_MONTH:
			return getFirstDayOfMonth(date);
		case LAST_DAY_OF_MONTH:
			return getLastDayOfMonth(date);
		case FIRST_DAY_OF_WEEK:
			return getFirstDayOfWeek(date);
		case LAST_DAY_OF_WEEK:
			return getLastDayOfWeek(date);
		}
		return date;
	}
	private Date getFirstDayOfYear(Date date)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.DAY_OF_YEAR, 1);
	    cal.set(Calendar.MONTH, 0);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
	    return cal.getTime();
	}

	private Date getLastDayOfYear(Date date)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.DAY_OF_YEAR, 1);
	    cal.set(Calendar.MONTH, 11);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
	    return cal.getTime();
	}

	private Date getFirstDayOfQuarter(Date date)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)/3 * 3);
	    return cal.getTime();
	}

	private Date getLastDayOfQuarter(Date date)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)/3 * 3 + 2);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	    return cal.getTime();
	}

	private Date getFirstDayOfMonth(Date date)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
	    return cal.getTime();
	}

	private Date getLastDayOfMonth(Date date)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	    return cal.getTime();
	}

	private Date getFirstDayOfWeek(Date date)
	{
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.DAY_OF_WEEK, cal.MONDAY);
	    return cal.getTime();
	}

	private Date getLastDayOfWeek(Date date)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.DAY_OF_WEEK, cal.FRIDAY);
	    return cal.getTime();
	}

}
