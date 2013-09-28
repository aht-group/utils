package net.sf.ahtutils.interfaces.model.with;

import net.sf.ahtutils.interfaces.model.task.Task;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithTask<T extends Task> extends EjbWithId
{
	T getTask();
	void setTask(T task);
}
