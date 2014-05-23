package net.sf.ahtutils.db.dump;

import java.io.File;
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
	
	protected Configuration config;
	protected UtilsDbDump.Operation operation;
	
	protected String binDump;
	
	protected String dbHost;
	protected String dbName;
	protected String dbUser;
	protected String dbPwd;
	protected String dbSchema;
	
	protected String dirSql;
	
	protected String[] tables;

	protected ExlpTxtWriter txtWriter;
	
	
	public AbstractDatabaseDump(Configuration config,UtilsDbDump.Operation operation)
	{
		this.config=config;
		this.operation=operation;
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
		logger.info("Bin: "+binDump+" ("+UtilsDbDump.cfgBinDump+")");
		logger.info("Host: "+dbHost+" (db."+operation.toString()+".user)");
		logger.info("DB: "+dbName+" (db."+operation.toString()+".db)");
		logger.info("User: "+dbUser+" (db."+operation.toString()+".user)");
		logger.info("Pwd: "+dbPwd+" (db."+operation.toString()+".password)");
		if(dbSchema!=null){logger.info("Schema: "+dbSchema+" (db."+operation.toString()+".schema)");}
	}
	
	protected void addLine(String line)
	{
		txtWriter.add(line);
	}
	
	public ExlpTxtWriter getWriter()
	{
		return txtWriter;
	}
	
	public String[] getTables() {
		return tables;
	}

	public void setTables(String[] tables) {
		this.tables = tables;
	}
	
	public File getShellFile()
	{
		return new File(config.getString(UtilsDbDump.cfgDirShell),config.getString("db."+operation.toString()+".shell"));
	}
	
	public void discoverTables()
	{
		switch(operation)
		{
			case dump: discoverTablesSql();break;
			case restore: logger.warn("NYI");break;
		}
	}
	
	void discoverTablesSql(){};
	
}
