package net.sf.ahtutils.interfaces.model.date;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithTimeline extends EjbWithId,EjbWithDateRange
{	
	public boolean isAllDay();
	public void setAllDay(boolean allDay);
}