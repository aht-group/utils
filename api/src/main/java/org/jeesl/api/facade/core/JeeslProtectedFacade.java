package org.jeesl.api.facade.core;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslProtectedFacade extends JeeslFacade
{	
	<T extends EjbWithId> T saveProtected(T o) throws JeeslConstraintViolationException,JeeslLockingException;
	<T extends Object> void rmProtected(T o) throws JeeslConstraintViolationException;
}