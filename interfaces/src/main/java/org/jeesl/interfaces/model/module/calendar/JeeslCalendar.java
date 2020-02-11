package org.jeesl.interfaces.model.module.calendar;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslCalendar <L extends JeeslLang, D extends JeeslDescription,
								CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								CT extends JeeslStatus<CT,L,D>,
								ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								IT extends JeeslStatus<IT,L,D>
								>
		extends EjbWithId
{
	
}