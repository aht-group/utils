package org.jeesl.interfaces.model.with.primitive.date;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithTimeline extends EjbWithId,EjbWithDateRange
{	
	public boolean isAllDay();
	public void setAllDay(boolean allDay);
}