package org.jeesl.interfaces.model.with.primitive.code;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithNrString extends EjbWithId
{	
	public static String attributeNr = "nr";
	
	public String getNr();
	public void setNr(String nr);
}