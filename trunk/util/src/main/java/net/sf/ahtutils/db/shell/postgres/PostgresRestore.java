package net.sf.ahtutils.db.shell.postgres;

import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.factory.xml.config.XmlParameterFactory;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresRestore extends AbstractPostgresShell implements UtilsDbShell
{
	final static Logger logger = LoggerFactory.getLogger(PostgresRestore.class);
	
	public PostgresRestore(Configuration config)
    {
		super(config,UtilsDbShell.Operation.restore);
		
		pShellCommand = XmlParameterFactory.build(UtilsDbShell.cfgBinRestore, "Shell command for restore", false);
		try{pShellCommand.setValue(config.getString(pShellCommand.getKey()));}
		catch (NoSuchElementException e){pShellCommand.setValue("pg_restore");}
		configurationParamter.getParameter().add(pShellCommand);
    } 
	
	public void buildCommands(boolean withStructure) throws ExlpUnsupportedOsException
	{
//		tables = new ArrayList<String>();	
//		tables.add("stationsmet");
		
		super.cmdPre();
		
		for(String table : tables){resotreTable(table);}
		for(String table : tables){fixPrimaryKey(table);}
		
		super.cmdPost();
	}
	
	
	public String resotreTable(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pShellCommand.getValue());
		sb.append(" --clean");
//		sb.append(" --create");
		sb.append(" --verbose");
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" --disable-triggers");
		sb.append(" -t '"+table+"'");
		sb.append(" ").append(pSqlDir.getValue()+File.separator+pDbName.getValue()+".sql");
		
		// Trigger http://dba.stackexchange.com/questions/23000/disable-constraints-before-using-pg-restore-exe
		// http://www.postgresonline.com/special_feature.php?sf_name=postgresql83_pg_dumprestore_cheatsheet
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public String fixPrimaryKey(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("psql");
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" -c \"").append("ALTER TABLE ").append(table).append(" ADD PRIMARY KEY (id);").append("\"");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
}
