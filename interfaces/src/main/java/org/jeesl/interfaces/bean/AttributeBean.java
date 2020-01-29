package org.jeesl.interfaces.bean;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.controller.handler.JeeslAttributeHandler;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;

public interface AttributeBean<CONTAINER extends JeeslAttributeContainer<?,?>>
{
	void save(JeeslAttributeHandler<CONTAINER> handler) throws JeeslConstraintViolationException, JeeslLockingException;
}