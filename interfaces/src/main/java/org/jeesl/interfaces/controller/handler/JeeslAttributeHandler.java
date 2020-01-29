package org.jeesl.interfaces.controller.handler;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;

public interface JeeslAttributeHandler<CONTAINER extends JeeslAttributeContainer<?,?>>
{
	CONTAINER saveContainer() throws JeeslConstraintViolationException, JeeslLockingException;
}