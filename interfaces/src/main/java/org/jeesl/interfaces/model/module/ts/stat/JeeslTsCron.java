package org.jeesl.interfaces.model.module.ts.stat;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslTsCron <SCOPE extends JeeslTsScope<?,?,?,?,?,?,INT>,
									INT extends JeeslStatus<INT,?,?>,
									STAT extends JeeslTsStatistic<?,?,STAT,?>>
		extends EjbWithId,Serializable,EjbRemoveable,EjbPersistable,
				EjbWithVisible,EjbSaveable
{
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	INT getIntervalExec();
	void setIntervalExec(INT intervalExec);

	INT getIntervalSrc();
	void setIntervalSrc(INT intervalSrc);

	INT getIntervalDst();
	void setIntervalDst(INT intervalDst);
	
	STAT getStatistic();
	void setStatistic(STAT statistic);
	
	int getFallbackSteps();
	void setFallbackSteps(int fallbackSteps);
}