package net.sf.ahtutils.db.dump;

import java.util.NoSuchElementException;

import net.sf.ahtutils.interfaces.db.UtilsDbDump;
import net.sf.exlp.shell.os.OsBashFile;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractDatabaseDump
{
	final static Logger logger = LoggerFactory.getLogger(AbstractDatabaseDump.class);
	
	protected String binDump;
	
	protected String dbHost;
	protected String dbName;
	protected String dbUser;
	protected String dbPwd;
	protected String dbSchema;
	
	protected String dirSql;
	
	protected ExlpTxtWriter txtWriter;
	
	
	public AbstractDatabaseDump(Configuration config,UtilsDbDump.Operation operation)
	{
		binDump = config.getString(UtilsDbDump.cfgBinDump);
		
		try{dbHost = config.getString("db."+operation.toString()+".host");}
		catch (NoSuchElementException e){dbHost="localhost";}
		
		dbName = config.getString("db."+operation.toString()+".db");
		dbUser = config.getString("db."+operation.toString()+".user");
		dbPwd = config.getString("db."+operation.toString()+".password");
		
		try{dbSchema = config.getString("db."+operation.toString()+".schema");}
		catch (NoSuchElementException e){}
		
		dirSql = config.getString(UtilsDbDump.cfgDirSql);
		
		txtWriter = new ExlpTxtWriter();
		
		txtWriter.add(OsBashFile.prefix());
		txtWriter.add(OsBashFile.comment("Automaticall generated script for SQL "+operation.toString()));
		txtWriter.add("");
		txtWriter.add("");
	}
	
	public void debug()
	{
		logger.info("Bin: "+binDump);
		logger.info("Host: "+dbHost);
		logger.info("DB: "+dbName);
		logger.info("User: "+dbUser);
		logger.info("Pwd: "+dbPwd);
		if(dbSchema!=null){logger.info("Schema: "+dbSchema);}
	}
	
	protected void addLine(String line)
	{
		txtWriter.add(line);
	}
	
	public ExlpTxtWriter getWriter()
	{
		return txtWriter;
	}
}
