package org.jeesl.interfaces.model.module.calendar;

import java.io.Serializable;
import java.util.Date;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslCalendarItem <L extends JeeslLang,
									D extends JeeslDescription,
									CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									CT extends JeeslStatus<CT,L,D>,
									ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									IT extends JeeslStatus<IT,L,D>
									>
		extends  Serializable,EjbWithId,
					EjbSaveable
{
	public enum Attributes {calendar,startDate,endDate}
	
	IT getType();
	void setType(IT type);
	
	Date getStartDate();
	void setStartDate(Date startDate);

	ZONE getStartZone();
	void setStartZone(ZONE startZone);
	
	ZONE getEndZone();
	void setEndZone(ZONE endZone);
	
	Date getEndDate();
	void setEndDate(Date endDate);
	
	boolean isAllDay();
	void setAllDay(boolean allDay);
}