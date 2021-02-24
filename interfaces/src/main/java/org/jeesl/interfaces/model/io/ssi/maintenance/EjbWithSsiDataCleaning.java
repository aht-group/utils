package org.jeesl.interfaces.model.io.ssi.maintenance;

import org.jeesl.interfaces.model.io.ssi.data.JeeslIoSsiCleaning;

public interface EjbWithSsiDataCleaning <CLEANING extends JeeslIoSsiCleaning<?,?,?,?>>
{
	public enum Attributes{cleaning}
	
	CLEANING getCleaning();
	void setCleaning(CLEANING cleaning);
}
