package org.jeesl.interfaces.model.with.primitive.date;

import java.util.Date;

public interface EjbWithValidUntil
{
	public static enum Attributes{validUntil}
	
	public Date getValidUntil();
	public void setValidUntil(Date validUntil);
}
