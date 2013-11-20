package net.sf.ahtutils.interfaces.model.date;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithDateRange extends EjbWithId
{
	public Date getStartDate();
	public void setStartDate(Date startDate);
	
	public Date getEndDate();
	public void setEndDate(Date endDate);
	
	public boolean isAllDay();
	public void setAllDay(boolean allDay);
}