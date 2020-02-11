package org.jeesl.interfaces.model.with.date;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithValidFrom extends EjbWithId
{
	public static enum Attributes{validFrom}
	
	public Date getValidFrom();
	public void setValidFrom(Date validFrom);
}