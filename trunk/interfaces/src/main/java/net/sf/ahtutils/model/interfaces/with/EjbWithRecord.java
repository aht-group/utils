package net.sf.ahtutils.model.interfaces.with;

import java.util.Date;

public interface EjbWithRecord extends EjbWithId
{
	public Date getRecord();
	public void setRecord(Date record);
}
