package net.sf.ahtutils.interfaces.facade;

import net.sf.ahtutils.interfaces.model.issue.UtilsTask;
import net.sf.ahtutils.interfaces.model.with.EjbWithTask;

public interface UtilsIssueFacade extends UtilsFacade
{	
	<T extends UtilsTask<T>, WT extends EjbWithTask<T>> T fcTask(Class<T> clTask, Class<WT>  clWithTask, WT ejb);
	<T extends UtilsTask<T>> T load(Class<T> clTask, T task);
}
