package org.jeesl.interfaces.controller.handler.system.io;

import java.io.Serializable;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;

public interface JeeslFileRepositoryStore <META extends JeeslFileMeta<?,?,?,?>> extends Serializable
{
	public META saveToFileRepository(META meta, byte[] bytes) throws JeeslConstraintViolationException, JeeslLockingException;
	public byte[] loadFromFileRepository(META meta) throws JeeslNotFoundException;
	public void delteFileFromRepository(META meta) throws JeeslConstraintViolationException, JeeslLockingException;
}