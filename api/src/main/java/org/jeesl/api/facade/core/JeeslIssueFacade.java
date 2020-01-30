package org.jeesl.api.facade.core;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;

import net.sf.ahtutils.interfaces.model.issue.UtilsTask;
import net.sf.ahtutils.interfaces.model.with.EjbWithTask;

public interface JeeslIssueFacade extends JeeslFacade
{	
	<T extends UtilsTask<T>, WT extends EjbWithTask<T>> T fTask(Class<T> clTask, Class<WT>  clWithTask, WT ejb) throws JeeslNotFoundException;
	<T extends UtilsTask<T>, WT extends EjbWithTask<T>> T fcTask(Class<T> clTask, Class<WT>  clWithTask, WT ejb);
	<T extends UtilsTask<T>> T load(Class<T> clTask, T task);
}