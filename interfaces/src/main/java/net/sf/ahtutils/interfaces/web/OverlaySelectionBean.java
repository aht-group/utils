package net.sf.ahtutils.interfaces.web;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface OverlaySelectionBean
{
	void opCallbackAdd(EjbWithId t) throws JeeslConstraintViolationException, JeeslLockingException;
	void opCallbackRemove(EjbWithId t) throws JeeslConstraintViolationException, JeeslLockingException;
}