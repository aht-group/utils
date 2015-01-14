package net.sf.ahtutils.controller.job.admin.db;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbDumpFinderJob<D extends UtilsDbDumpFile>
{
	final static Logger logger = LoggerFactory.getLogger(DbDumpFinderJob.class);
	
	private UtilsDbFacade fDb;
	private Class<D> cDump;
	private File fDir;
	private EjbDbDumpFileFactory<D> fDumpFile;
	
	public DbDumpFinderJob(final Class<D> cDump,UtilsDbFacade fDb,File fDir)
	{
		this.cDump=cDump;
		this.fDb = fDb;
		this.fDir = fDir;
		
		fDumpFile = EjbDbDumpFileFactory.factory(cDump);
	}
	
	public void findDumps()
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
