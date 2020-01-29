package org.jeesl.api.bean.callback;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslFileRepositoryCallback
{	
	void fileRepositoryContainerSaved(EjbWithId id) throws JeeslConstraintViolationException, JeeslLockingException;
}