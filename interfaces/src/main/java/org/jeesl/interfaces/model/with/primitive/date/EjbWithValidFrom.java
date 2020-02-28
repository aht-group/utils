package org.jeesl.interfaces.model.with.primitive.date;

import java.util.Date;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithValidFrom extends EjbWithId
{
	public static enum Attributes{validFrom}
	
	public Date getValidFrom();
	public void setValidFrom(Date validFrom);
}