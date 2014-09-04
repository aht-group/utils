package net.sf.ahtutils.db.shell.postgres;

import java.io.File;

import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresRestore extends AbstractPostgresShell implements UtilsDbShell
{
	final static Logger logger = LoggerFactory.getLogger(PostgresRestore.class);
	
	public PostgresRestore(Configuration config)
    {
		super(config,UtilsDbShell.Operation.restore);
		
		if(config.containsKey(UtilsDbShell.cfgBinRestore)){shellCommand = config.getString(UtilsDbShell.cfgBinRestore);}
		else
		{
			shellCommand = "pg_restore";
			logger.info("Using default command ("+shellCommand+"), can be overriden in "+UtilsDbShell.cfgBinRestore);
		}
    } 
	
	public void buildCommands(boolean withStructure) throws ExlpUnsupportedOsException
	{
		super.cmdPre();
		
		for(String table : tables)
		{
			resotreTable(table);
		}
		super.cmdPost();
	}
	
	
	public String resotreTable(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(shellCommand);
		sb.append(" --clean");
//		sb.append(" --create");
		sb.append(" --verbose");
		sb.append(" -h ").append(dbHost);
		sb.append(" -U ").append(dbUser);
		sb.append(" -d ").append(dbName);
		sb.append(" --disable-triggers");
		sb.append(" -t '"+table+"'");
		sb.append(" ").append(dirSql+File.separator+dbName+".sql");
		
		// Trigger http://dba.stackexchange.com/questions/23000/disable-constraints-before-using-pg-restore-exe
		// http://www.postgresonline.com/special_feature.php?sf_name=postgresql83_pg_dumprestore_cheatsheet
		
		super.addLine(sb.toString());
		return sb.toString();
	}
}
