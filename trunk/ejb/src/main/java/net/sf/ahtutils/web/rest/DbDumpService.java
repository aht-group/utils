package net.sf.ahtutils.web.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.db.EjbDbDumpFileFactory;
import net.sf.ahtutils.interfaces.facade.UtilsDbFacade;
import net.sf.ahtutils.interfaces.model.db.UtilsDbDumpFile;
import net.sf.ahtutils.interfaces.rest.UtilsDbDumpRest;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.exlp.xml.io.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbDumpService<D extends UtilsDbDumpFile> implements UtilsDbDumpRest
{
	final static Logger logger = LoggerFactory.getLogger(DbDumpService.class);
	
	private UtilsDbFacade fDb;
	private Class<D> cDump;
	private EjbDbDumpFileFactory<D> fDumpFile;
	
	public DbDumpService(final Class<D> cDump,UtilsDbFacade fDb)
	{
		this.cDump=cDump;
		this.fDb = fDb;
		
		fDumpFile = EjbDbDumpFileFactory.factory(cDump);
	}
	
	@Override
	public DataUpdate uploadDumps(Dir directory)
	{
		Set<D> set = new HashSet<D>();
		set.addAll(fDb.all(cDump));
		
		for(net.sf.exlp.xml.io.File f : directory.getFile())
		{
			D ejb;
			try
			{
				ejb = fDb.fByName(cDump,f.getName());
				set.remove(ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = fDumpFile.build(f);
					ejb = fDb.persist(ejb);
				}
				catch (UtilsContraintViolationException e1) {e1.printStackTrace();}
			}
		}
		logger.info("Size: "+set.size());
		List<D> list = new ArrayList<D>(set);
		for(D ejb : list)
		{
			try
			{
				ejb.setEndDate(new Date());
				fDb.update(ejb);
			}
			catch (UtilsContraintViolationException e) {e.printStackTrace();}
			catch (UtilsLockingException e) {e.printStackTrace();}
		}
		return new DataUpdate();
	}
	
	public void findDumps(File fDir)
	{
		Set<D> set = new HashSet<D>();
		set.addAll(fDb.all(cDump));
		
		for(File f : fDir.listFiles())
		{
			D ejb;
			try
			{
				ejb = fDb.fByName(cDump,f.getName());
				set.remove(ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = fDumpFile.build(f);
					ejb = fDb.persist(ejb);
				}
				catch (UtilsContraintViolationException e1) {e1.printStackTrace();}
			}
		}
		logger.info("Size: "+set.size());
		List<D> list = new ArrayList<D>(set);
		for(D ejb : list)
		{
			try
			{
				ejb.setEndDate(new Date());
				fDb.update(ejb);
			}
			catch (UtilsContraintViolationException e) {e.printStackTrace();}
			catch (UtilsLockingException e) {e.printStackTrace();}
		}
	}
}
