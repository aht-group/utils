package net.sf.ahtutils.db.shell;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.factory.xml.config.XmlParameterFactory;
import net.sf.exlp.factory.xml.config.XmlParametersFactory;
import net.sf.exlp.interfaces.util.TextWriter;
import net.sf.exlp.shell.os.OsBashFile;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.exlp.xml.config.Parameter;
import net.sf.exlp.xml.config.Parameters;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractDatabaseShell
{
	final static Logger logger = LoggerFactory.getLogger(AbstractDatabaseShell.class);
	
	protected Configuration config;
	protected UtilsDbShell.Operation operation;
	
	public void setOperation(UtilsDbShell.Operation operation) {
		this.operation = operation;
	}

	protected Parameter pShellCommand;
	
	protected Parameter pDbHost,pDbName,pDbUser,pDbPwd;
	
	protected Parameter pSqlDir;
	
	protected String dbSchema;
	
	protected List<String> tables;
	public List<String> getTables(){return tables;}
	
	protected ExlpTxtWriter txtWriter;
	
	public AbstractDatabaseShell(Configuration config,UtilsDbShell.Operation operation)
	{
		this.config=config;
		this.operation=operation;
		
		configurationParamter = XmlParametersFactory.build();
		
		pDbHost = XmlParameterFactory.build("db."+operation.toString()+".host", "DB Host for "+operation.toString(), false);
		try{pDbHost.setValue(config.getString(pDbHost.getKey()));}
		catch (NoSuchElementException e){pDbHost.setValue("localhost");}
		configurationParamter.getParameter().add(pDbHost);
		
		pDbName = XmlParameterFactory.build("db."+operation.toString()+".db", "DB Name for "+operation.toString(), true);
		pDbName.setValue(config.getString(pDbName.getKey()));
		configurationParamter.getParameter().add(pDbName);
		
		pDbUser = XmlParameterFactory.build("db."+operation.toString()+".user", "DB User for athentication of "+operation.toString(), true);
		pDbUser.setValue(config.getString(pDbUser.getKey()));
		configurationParamter.getParameter().add(pDbUser);
		
		pDbPwd = XmlParameterFactory.build("db."+operation.toString()+".password", "DB Password for athentication of "+operation.toString(), true);
		pDbPwd.setValue(config.getString(pDbPwd.getKey()));
		configurationParamter.getParameter().add(pDbPwd);
		
		try{dbSchema = config.getString("db."+operation.toString()+".schema");}
		catch (NoSuchElementException e){}
		
		pSqlDir = XmlParameterFactory.build(UtilsDbShell.cfgDirSql, "Directory for SQL files", true);
		pSqlDir.setValue(config.getString(pSqlDir.getKey()));
		configurationParamter.getParameter().add(pSqlDir);
		
		txtWriter = new ExlpTxtWriter();
		
		txtWriter.add(OsBashFile.prefix());
		txtWriter.add(OsBashFile.comment("Automaticall generated script for SQL "+operation.toString()));
		txtWriter.add("");
		txtWriter.add("");
	}
	
	public void debug()
	{
		logger.info("Bin: "+pShellCommand.getValue()+" ("+UtilsDbShell.cfgBinDump+")");
		logger.info("Host: "+pDbHost.getValue()+" (db."+operation.toString()+".user)");
		logger.info("DB: "+pDbName.getValue()+" (db."+operation.toString()+".db)");
		logger.info("User: "+pDbUser.getValue()+" (db."+operation.toString()+".user)");
		logger.info("Pwd: "+pDbPwd.getValue()+" (db."+operation.toString()+".password)");
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
	
	//Configuration Parameter
	protected Parameters configurationParamter;
	public Parameters getConfigurationParameter(){return configurationParamter;}
}
