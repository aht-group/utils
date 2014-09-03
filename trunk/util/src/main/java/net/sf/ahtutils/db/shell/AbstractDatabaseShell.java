package net.sf.ahtutils.db.shell;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.interfaces.util.TextWriter;
import net.sf.exlp.shell.os.OsBashFile;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractDatabaseShell
{
	final static Logger logger = LoggerFactory.getLogger(AbstractDatabaseShell.class);
	
	protected Configuration config;
	protected UtilsDbShell.Operation operation;
	
	protected String shellCommand;
	
	protected String dbHost;
	protected String dbName;
	protected String dbUser;
	protected String dbPwd;
	protected String dbSchema;
	
	protected String dirSql;
	
	protected List<String> tables;
	public List<String> getTables(){return tables;}

	protected ExlpTxtWriter txtWriter;
	
	
	public AbstractDatabaseShell(Configuration config,UtilsDbShell.Operation operation)
	{
		this.config=config;
		this.operation=operation;
		
		try{dbHost = config.getString("db."+operation.toString()+".host");}
		catch (NoSuchElementException e){dbHost="localhost";}
		
		dbName = config.getString("db."+operation.toString()+".db");
		dbUser = config.getString("db."+operation.toString()+".user");
		dbPwd = config.getString("db."+operation.toString()+".password");
		
		try{dbSchema = config.getString("db."+operation.toString()+".schema");}
		catch (NoSuchElementException e){}
		
		dirSql = config.getString(UtilsDbShell.cfgDirSql);
		
		txtWriter = new ExlpTxtWriter();
		
		txtWriter.add(OsBashFile.prefix());
		txtWriter.add(OsBashFile.comment("Automaticall generated script for SQL "+operation.toString()));
		txtWriter.add("");
		txtWriter.add("");
	}
	
	public void debug()
	{
		logger.info("Bin: "+shellCommand+" ("+UtilsDbShell.cfgBinDump+")");
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
	
	public TextWriter getWriter()
	{
		return txtWriter;
	}
	
	
	public File getShellFile()
	{
		return new File(config.getString(UtilsDbShell.cfgDirShell),config.getString("db."+operation.toString()+".shell"));
	}
	
	public void discoverTables()
	{
		tables = Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbTables));
		
		if(tables.size()==0)
		{
			switch(operation)
			{
				case dump: discoverExportTables();break;
				case restore: logger.warn("NYI");break;
			}
		}
	}
	
	public void discoverExportTables()
	{
		logger.info("No "+UtilsDbShell.cfgDbTables+" config. Discovering by SQL");
		discoverTablesSql();
	}
	
	protected void discoverTablesSql(){}
}
