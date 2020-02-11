package org.jeesl.factory.json.util;

import java.util.Date;

import org.jeesl.interfaces.model.with.date.EjbWithYear;
import org.jeesl.interfaces.model.with.date.EntityWithDate;
import org.jeesl.interfaces.model.with.date.EntityWithDay;
import org.jeesl.interfaces.model.with.date.EntityWithMonth;
import org.jeesl.interfaces.model.with.date.EntityWithQuarter;
import org.jeesl.interfaces.model.with.date.EntityWithWeek;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonDateFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonDateFactory.class);
	
	public static void build(EntityWithDate json, Date date) 
	{
		DateTime dt = new DateTime(date);
		
		if((json instanceof EjbWithYear)){((EjbWithYear) json).setYear(dt.getYear());}
		if((json instanceof EntityWithMonth)){((EntityWithMonth) json).setMonth(dt.getMonthOfYear());}
		if((json instanceof EntityWithQuarter))
		{
			int quarter = (dt.getMonthOfYear() / 3) + 1;
			((EntityWithQuarter) json).setQuarter("Q"+quarter);
		}
		if((json instanceof EntityWithWeek)){((EntityWithWeek) json).setWeek(dt.getWeekOfWeekyear());}
		if((json instanceof EntityWithDay)){((EntityWithDay) json).setDay(dt.getDayOfMonth());}
	}
}