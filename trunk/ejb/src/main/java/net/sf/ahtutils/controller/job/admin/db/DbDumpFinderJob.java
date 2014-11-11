package net.sf.ahtutils.controller.job.admin.db;

import java.io.File;

import net.sf.ahtutils.interfaces.facade.UtilsDbFacade;
import net.sf.ahtutils.interfaces.model.db.UtilsDbDumpFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbDumpFinderJob<D extends UtilsDbDumpFile>
{
	final static Logger logger = LoggerFactory.getLogger(DbDumpFinderJob.class);
	
	private Class<D> cDump;
	
	public DbDumpFinderJob(final Class<D> cDump,UtilsDbFacade fDb,File fDir)
	{
		this.cDump=cDump;
		logger.info("Dumps "+fDb.all(cDump).size());
	}
}
