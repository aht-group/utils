package net.sf.ahtutils.factory.ejb.db;

import java.io.File;
import java.util.Date;

import net.sf.ahtutils.interfaces.model.db.UtilsDbDumpFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbDbDumpFileFactory<F extends UtilsDbDumpFile>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDbDumpFileFactory.class);
	
	final Class<F> cDumpFile;
    
	public EjbDbDumpFileFactory(final Class<F> cDumpFile)
	{       
        this.cDumpFile = cDumpFile;
	}
	
	public static <F extends UtilsDbDumpFile> EjbDbDumpFileFactory<F> factory(final Class<F> cDumpFile)
	{
		return new EjbDbDumpFileFactory<F>(cDumpFile);
	}
    
	public F build(net.sf.exlp.xml.io.File file)
	{
		return build(file.getName(),file.getSize(),file.getLastModifed().toGregorianCalendar().getTime());
    }
	
	public F build(File file)
	{
		return build(file.getName(),file.length(),new Date(file.lastModified()));
    }
	
	public F build(String name, long size, Date record)
	{
		F ejb = null;
		try
		{
			 ejb = cDumpFile.newInstance();
			 ejb.setName(name);
			 ejb.setSize(size);
			 ejb.setStartDate(record);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}

}