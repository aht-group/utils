package net.sf.ahtutils.db.shell.postgres;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.sf.ahtutils.db.sql.SqlConnectionFactory;
import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresDump extends AbstractPostgresShell implements UtilsDbShell
{
	final static Logger logger = LoggerFactory.getLogger(PostgresDump.class);
	
	public PostgresDump(Configuration config)
    {
		super(config,UtilsDbShell.Operation.dump);
		
		if(config.containsKey(UtilsDbShell.cfgBinDump)){shellCommand = config.getString(UtilsDbShell.cfgBinDump);}
		else
		{
			shellCommand = "pg_dump";
			logger.info("Using default command ("+shellCommand+"), can be overriden in "+UtilsDbShell.cfgBinDump);
		}
    } 
	
	public void buildCommands(boolean withStructure) throws ExlpUnsupportedOsException
	{
		super.cmdPre();
		
//		for(String table : tables){dumpTable(table, withStructure);}
		dumpDatabase();
		
		super.cmdPost();
	}
	
	public String dumpDatabase()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(shellCommand);
		sb.append(" -h ").append(dbHost);
		sb.append(" -U ").append(dbUser);
		sb.append(" --blobs");
		sb.append(" --format=c");
		sb.append(" --verbose");
		sb.append(" --file=").append(dirSql+File.separator+dbName+".sql");
		sb.append(" ").append(dbName);
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public String dumpTable(String table, boolean withStructure)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(shellCommand);
		sb.append(" -h ").append(dbHost);
		sb.append(" -U ").append(dbUser);
		sb.append(" --blobs");
		sb.append(" --format=c");
		sb.append(" --verbose");
//		sb.append(" -C --column-inserts -v");
		sb.append(" --file=").append(dirSql+File.separator+table+".sql");
		sb.append(" -t '"+table+"'");
		sb.append(" ").append(dbName);
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public String resotreTable(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(shellCommand);
		sb.append(" NYI");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	@Override public void discoverTablesSql()
	{
		SqlConnectionFactory scf = new SqlConnectionFactory(config);
		Connection c = scf.buildPostgresSqlConnection(dbHost, dbName, dbUser, dbPwd);
		
		tables = new ArrayList<String>();
		try
		{
			Statement s = c.createStatement();
			
			String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='"+dbSchema+"' AND table_type='BASE TABLE';";
			
			logger.info("SQL: "+sql);
			ResultSet rs = s.executeQuery(sql);
			while(rs.next())
			{	
				tables.add(rs.getString("table_name"));
		    }
			s.close();
		}
		catch (SQLException e) {e.printStackTrace();}
		logger.info("Discovered "+tables.size());
	}
}
