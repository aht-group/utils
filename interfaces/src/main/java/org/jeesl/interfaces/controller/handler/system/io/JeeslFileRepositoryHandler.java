package org.jeesl.interfaces.controller.handler.system.io;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslWithFileRepositoryContainer;

public interface JeeslFileRepositoryHandler <STORAGE extends JeeslFileStorage<?,?,?,?>,
											CONTAINER extends JeeslFileContainer<STORAGE,?>,
											META extends JeeslFileMeta<?,CONTAINER,?,?>>
		extends Serializable
{
	void setDebugOnInfo(boolean debugOnInfo);
	
//	void reset();
	
	STORAGE getStorage();
	void setStorage(STORAGE storage);
	
	CONTAINER getContainer();
	List<META> getMetas();
	
	<W extends JeeslWithFileRepositoryContainer<CONTAINER>> void initSilent(W with);
	<W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(W with) throws JeeslConstraintViolationException, JeeslLockingException;
	<W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(STORAGE storage, W with) throws JeeslConstraintViolationException, JeeslLockingException;
	
	InputStream download(META meta) throws JeeslNotFoundException;
	
	void copyTo(JeeslFileRepositoryHandler<STORAGE,CONTAINER,META> target) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException;
	
	void deleteFile() throws JeeslConstraintViolationException, JeeslLockingException;
	
//	StreamedContent fileStream() throws Exception;
}