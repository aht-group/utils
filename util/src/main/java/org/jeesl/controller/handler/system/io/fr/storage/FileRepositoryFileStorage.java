package org.jeesl.controller.handler.system.io.fr.storage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.controller.handler.system.io.JeeslFileRepositoryStore;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileRepositoryFileStorage<STORAGE extends JeeslFileStorage<?,?,?,?>,
									META extends JeeslFileMeta<?,?,?,?>>
	implements JeeslFileRepositoryStore<META>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(FileRepositoryFileStorage.class);

	private final File baseDir;
	
	public FileRepositoryFileStorage(STORAGE storage)
	{
		baseDir = new File(storage.getJson());
		logger.info("Storage created for "+baseDir.getAbsolutePath());
	}
	
	@Override public META saveToFileRepository(META meta, byte[] bytes) throws JeeslConstraintViolationException, JeeslLockingException
	{
		File f = build(meta.getCode());
		logger.info(meta.getCode());
		logger.info(f.getAbsolutePath());
		try {FileUtils.writeByteArrayToFile(f, bytes);}
		catch (IOException e){throw new JeeslConstraintViolationException(e.getMessage());}
		
		return meta;
	}
	
	@Override
	public byte[] loadFromFileRepository(META meta) throws JeeslNotFoundException
	{
		File f = build(meta.getCode());
		if(!f.exists()) {throw new JeeslNotFoundException("File "+f.getAbsolutePath()+" does not exist");}
		try{return FileUtils.readFileToByteArray(f);}
		catch (IOException e) {throw new JeeslNotFoundException(e.getMessage());}
	}
	
	private File build(String uid)
	{
		uid = uid.replace("-", "");
		File l1 = new File(baseDir,uid.substring(0,2));
		File l2 = new File(l1,uid.substring(2,4));
		File l3 = new File(l2,uid.substring(4,6));
		File l4 = new File(l3,uid.substring(6,8));
		File l5 = new File(l4,uid.substring(8,10));
		return new File(l5,uid);
	}

	@Override public void delteFileFromRepository(META meta) throws JeeslConstraintViolationException, JeeslLockingException
	{
		File f = build(meta.getCode());
		if(f.exists()){f.delete();}
		else {logger.warn("Requesting a delete, but file is not there!! "+f.getAbsolutePath());}
	}
}