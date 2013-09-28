package net.sf.ahtutils.interfaces.model.with;

import net.sf.ahtutils.interfaces.model.monitoring.UtilsTask;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithTask<T extends UtilsTask> extends EjbWithId
{
	T getTask();
	void setTask(T task);
}
