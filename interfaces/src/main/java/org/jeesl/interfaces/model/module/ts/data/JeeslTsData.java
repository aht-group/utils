package org.jeesl.interfaces.model.module.ts.data;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface JeeslTsData <TS extends JeeslTimeSeries<?,?,?>,
								TRANSACTION extends JeeslTsTransaction<?,?,?,?>,
								SAMPLE extends JeeslTsSample, 
								WS extends JeeslStatus<WS,?,?>>
		extends JeeslTsValue,EjbSaveable,Serializable,EjbRemoveable,EjbPersistable
{
	enum Attributes{transaction,timeSeries,workspace,record,value}
	
	TS getTimeSeries();
	void setTimeSeries(TS timeSeries);
	
	WS getWorkspace();
	void setWorkspace(WS workspace);
	
	TRANSACTION getTransaction();
	void setTransaction(TRANSACTION transaction);
	
	SAMPLE getSample();
	void setSample(SAMPLE sample);
}