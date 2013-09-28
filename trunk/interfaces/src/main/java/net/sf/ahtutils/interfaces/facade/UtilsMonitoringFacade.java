package net.sf.ahtutils.interfaces.facade;

import net.sf.ahtutils.interfaces.model.monitoring.UtilsTask;
import net.sf.ahtutils.interfaces.model.with.EjbWithTask;

public interface UtilsMonitoringFacade extends UtilsFacade
{	
	<T extends UtilsTask<T>, WT extends EjbWithTask<T>> T fcTask(Class<T> clTask, Class<WT>  clWithTask, WT ejb);
}
