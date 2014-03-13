package net.sf.ahtutils.monitor.util;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.MutableDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntervalHelper
{
	final static Logger logger = LoggerFactory.getLogger(IntervalHelper.class);
			
	public static MutableDateTime ceil(DateTime dt, Duration d) throws UtilsProcessingException
	{
		MutableDateTime floor = floor(dt, d);
		floor.add(d);
		return floor;
	}
	
	public static MutableDateTime floor(DateTime dt, Duration d) throws UtilsProcessingException
	{
		checkDuration(d);
		MutableDateTime mdt = new MutableDateTime(dt);
	
		if(d.getStandardDays()!=0)
		{
			mdt.setHourOfDay(0);
			mdt.setMinuteOfHour(0);
			mdt.setSecondOfMinute(0);
			mdt.setMillisOfSecond(0);
		}
		else if(d.getStandardHours()!=0)
		{
			mdt.setMinuteOfHour(0);
			mdt.setSecondOfMinute(0);
			mdt.setMillisOfSecond(0);
		}
		else if(d.getStandardMinutes()!=0)
		{
			mdt.setSecondOfMinute(0);
			mdt.setMillisOfSecond(0);
		}
		else if(d.getStandardSeconds()!=0)
		{
			mdt.setMillisOfSecond(0);
		}
		return mdt;
	}
	
	private static void checkDuration(Duration d) throws UtilsProcessingException
	{
		if(d.getStandardDays()!=0)
		{
			if(d.getStandardHours()!=(24*d.getStandardDays())){throw new UtilsProcessingException("It's not allowed to set hours if days are set");}
			if(d.getStandardMinutes()!=(60*24*d.getStandardDays())){throw new UtilsProcessingException("It's not allowed to set minutes if days are set");}
			if(d.getStandardSeconds()!=(60*60*24*d.getStandardDays())){throw new UtilsProcessingException("It's not allowed to set seconds if days are set");}
			if(d.getStandardDays()!=1){throw new UtilsProcessingException("Currently only days=1 is upported");}
			if(d.getMillis()!=(1000*60*60*24*d.getStandardDays())){throw new UtilsProcessingException("It's not allowed to set millis if days are set");}
		}
		else if(d.getStandardHours()!=0)
		{
			if(d.getStandardMinutes()!=(60*d.getStandardHours())){throw new UtilsProcessingException("It's not allowed to set minutes if hours are set");}
			if(d.getStandardSeconds()!=(60*60*d.getStandardHours())){throw new UtilsProcessingException("It's not allowed to set seconds if hours are set");}
			if(d.getMillis()!=(1000*60*60*d.getStandardHours())){throw new UtilsProcessingException("It's not allowed to set millis if hours are set");}
			if(d.getStandardHours()!=1){throw new UtilsProcessingException("Currently only hours=1 is upported");}
			if(24%d.getStandardHours()!=0){throw new UtilsProcessingException("24h cannot be divided by "+d.getStandardHours());}
		}
		else if(d.getStandardMinutes()!=0)
		{
			if(d.getStandardSeconds()!=(60*d.getStandardMinutes())){throw new UtilsProcessingException("It's not allowed to set seconds if minutes are set");}
			if(d.getMillis()!=(1000*60*d.getStandardMinutes())){throw new UtilsProcessingException("It's not allowed to set millis if minutes are set");}
			if(d.getStandardMinutes()!=1){throw new UtilsProcessingException("Currently only minutes=1 is upported");}
			if(60%d.getStandardMinutes()!=0){throw new UtilsProcessingException("60m cannot be divided by "+d.getStandardMinutes());}
		}
		else if(d.getStandardSeconds()!=0)
		{
			if(d.getMillis()!=(1000*d.getStandardSeconds())){throw new UtilsProcessingException("It's not allowed to set millis if seconds are set");}
			if(d.getStandardSeconds()!=1){throw new UtilsProcessingException("Currently only second=1 is upported");}
			if(60%d.getStandardSeconds()!=0){throw new UtilsProcessingException("60s cannot be divided by "+d.getStandardSeconds());}
		}
		else if(d.getMillis()!=0)
		{
			throw new UtilsProcessingException("Durations in milliseconds are not supported");
		}
	}
	
	public static void debug(Duration d)
	{
		logger.debug("Debugging "+Duration.class.getSimpleName()+" "+d);
		logger.debug("     Days "+d.getStandardDays());
		logger.debug("    Hours "+d.getStandardHours());
		logger.debug("  Minutes "+d.getStandardMinutes());
		logger.debug("  Seconds "+d.getStandardSeconds());
		logger.debug("   Millis "+d.getMillis());
	}
	
}