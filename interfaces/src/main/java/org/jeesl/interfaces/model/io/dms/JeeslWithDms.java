package org.jeesl.interfaces.model.io.dms;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithDms<DMS extends JeeslIoDms<?,?,?,?,?,?>>
						extends EjbWithId
{
	public enum Attributes {dms}
	
	DMS getDms();
	void setDms(DMS cms);
}