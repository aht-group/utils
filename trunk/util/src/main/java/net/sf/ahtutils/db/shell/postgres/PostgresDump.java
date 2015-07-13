package net.sf.ahtutils.db.shell.postgres;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import net.sf.ahtutils.db.sql.SqlConnectionFactory;
import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.factory.xml.config.XmlParameterFactory;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresDump extends AbstractPostgresShell implements UtilsDbShell
{
	final static Logger logger = LoggerFactory.getLogger(PostgresDump.class);
	
	public PostgresDump(Configuration config)
    {
		super(config,UtilsDbShell.Operation.dump);
		
		pDbShell = XmlParameterFactory.build(UtilsDbShell.cfgBinDump, "Shell command for dump", false);
		try{pDbShell.setValue(config.getString(pDbShell.getKey()));}
		catch (NoSuchElementException e){pDbShell.setValue("pg_dump");}
		configurationParamter.getParameter().add(pDbShell);
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
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" --blobs");
		sb.append(" --format=c");
		sb.append(" --verbose");
		sb.append(" --file=").append(pSqlDir.getValue()+File.separator+pDbName.getValue()+".sql");
		sb.append(" ").append(pDbName.getValue());
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public String dumpTable(String table, boolean withStructure)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" --blobs");
		sb.append(" --format=c");
		sb.append(" --verbose");
//		sb.append(" -C --column-inserts -v");
		sb.append(" --file=").append(pSqlDir.getValue()+File.separator+table+".sql");
		sb.append(" -t '"+table+"'");
		sb.append(" ").append(pDbName.getValue());
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public String resotreTable(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" NYI");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public void discoverTablesSql()
	{
		SqlConnectionFactory scf = new SqlConnectionFactory(config);
		Connection c = scf.buildPostgresSqlConnection(pDbHost.getValue(), pDbName.getValue(), pDbUser.getValue(), pDbPwd.getValue());
		
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
