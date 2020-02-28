package net.sf.ahtutils.interfaces.model.with;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

import net.sf.ahtutils.interfaces.model.issue.UtilsTask;

public interface EjbWithTask<T extends UtilsTask<T>> extends EjbWithId
{
	T getTask();
	void setTask(T task);
}
