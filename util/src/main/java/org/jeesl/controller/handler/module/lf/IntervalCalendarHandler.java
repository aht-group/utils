package org.jeesl.controller.handler.module.lf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.bean.lf.TargetTimeBean;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IntervalCalendarHandler<TTI extends JeeslLfTimeInterval<?,?,TTI,?>> implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(IntervalCalendarHandler.class);
	private boolean debug = true;
	private final TargetTimeBean bean;

	protected List<IntervalStatus> intervalConfigs; public List<IntervalStatus> getIntervalConfigs() {return intervalConfigs;} public void setIntervalConfigs(List<IntervalStatus> intervalConfigs) {this.intervalConfigs = intervalConfigs;}
	protected IntervalStatus intervalConfig; public IntervalStatus getIntervalConfig() {return intervalConfig;} public void setIntervalConfig(IntervalStatus intervalConfig) {this.intervalConfig = intervalConfig;}
	private int noOfIntervalRequired; public int getNoOfIntervalRequired() {return noOfIntervalRequired;} public void setNoOfIntervalRequired(int noOfIntervalRequired) {this.noOfIntervalRequired = noOfIntervalRequired;}
	private TTI interval;

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

	public IntervalCalendarHandler(TargetTimeBean bean)
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
		bean.updateTimeGroupStartAndEndDate(c.getTime());

	}


    public void onChangeNoOfIntervalRequired()
	{
		Calendar cal = Calendar.getInstance();
		Date record = bean.getTimeGroupStartDate();
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
		bean.updateTimeGroupEndDate(cal.getTime());
	}


	public void changeInterval(TTI interval)
	{
		this.interval=interval;
		intervalConfigs = new ArrayList<IntervalStatus>();

		if(interval.getCode().equals("week")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_WEEK); intervalConfigs.add(IntervalStatus.LAST_DAY_OF_WEEK);}
		if(interval.getCode().equals("month")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_MONTH);intervalConfigs.add(IntervalStatus.LAST_DAY_OF_MONTH);}
		if(interval.getCode().equals("year")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_YEAR);intervalConfigs.add(IntervalStatus.LAST_DAY_OF_YEAR);}
		if(interval.getCode().equals("quarter")){intervalConfigs.add(IntervalStatus.FIRST_DAY_OF_QUARTER);intervalConfigs.add(IntervalStatus.LAST_DAY_OF_QUARTER);}
	}


}
