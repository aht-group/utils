package net.sf.ahtutils.jsf.functions;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateDifference
{
	final static Logger logger = LoggerFactory.getLogger(DateDifference.class);
	
    private DateDifference() {}
    
    public static Integer dateDifference(Date from, Date to)
    {
    	if(from==null || to==null){return null;}
    	DateTime startDate = new DateTime(from); 
		DateTime endDate = new DateTime(to);
		Days diff = Days.daysBetween(startDate, endDate);
		return diff.getDays();
    }
    
    public static Integer dayDeviation(Date from, Date to, Integer ref)
    {
    	Integer actual = dateDifference(from, to);
    	if(actual==null || ref==null){return null;}
		return actual-ref;
    }
    
    public static Date plusDay(Date from, Integer days)
    {
    	if(from==null || days==null){return null;}
    	DateTime dt = new DateTime(from);
		return dt.plusDays(days).toDate();
    }
}
