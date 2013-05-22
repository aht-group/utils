package net.sf.ahtutils.model.interfaces.with;

import java.util.Date;


public interface EjbWithTimeline extends EjbWithId
{
	public Date getStartDate();
	public void setStartDate(Date startDate);
	
	public Date getEndDate();
	public void setEndDate(Date endDate);
	
	public boolean isAllDay();
	public void setAllDay(boolean allDay);
}
