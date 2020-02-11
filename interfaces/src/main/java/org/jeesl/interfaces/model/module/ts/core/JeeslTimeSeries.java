package org.jeesl.interfaces.model.module.ts.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTimeSeries <SCOPE extends JeeslTsScope<?,?,?,?,?,?,INT>,
									BRIDGE extends JeeslTsBridge<?>,
									INT extends JeeslStatus<INT,?,?>
//									,STAT extends JeeslTsStatistic<?,?,STAT,?>
>
		extends EjbWithId,Serializable,EjbRemoveable,EjbPersistable
{
	public enum Attributes{scope,interval,bridge}
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	INT getInterval();
	void setInterval(INT interval);
	
	BRIDGE getBridge();
	void setBridge(BRIDGE bridge);
}