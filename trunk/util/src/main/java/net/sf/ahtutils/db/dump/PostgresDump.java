package net.sf.ahtutils.db.dump;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.db.sql.SqlConnectionFactory;
import net.sf.ahtutils.interfaces.db.UtilsDbDump;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresDump extends AbstractDatabaseDump implements UtilsDbDump
{
	final static Logger logger = LoggerFactory.getLogger(PostgresDump.class);
	
	public PostgresDump(Configuration config,UtilsDbDump.Operation operation)
    {
		super(config,operation);
    } 
	
	public void buildCommands(boolean withStructure)
	{
		for(String table : tables)
		{
			switch(operation)
			{
				case dump: dumpTable(table, withStructure);break;
				case restore: resotreTable(table);break;
			}	
		}
	}
	
	public String dumpTable(String table, boolean withStructure)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(binDump);
		sb.append(" -h ").append(dbHost);
		sb.append(" -U ").append(dbUser);
		sb.append(" -F p -C --column-inserts -v");
		sb.append(" -f ").append(dirSql+File.separator+table+".sql");
		sb.append(" -t '"+table+"' "+dbName);
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public String resotreTable(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(binDump);
		sb.append(" NYI");
		
		super.addLine(sb.toString());
		return sb.toString();
	}

	@Override public void discoverTablesSql()
	{
		SqlConnectionFactory scf = new SqlConnectionFactory(config);
		Connection c = scf.buildPostgresSqlConnection(dbHost, dbName, dbUser, dbPwd);
		
		List<String> sTables = new ArrayList<String>();
		try
		{
			Statement s = c.createStatement();
			
			String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='"+dbSchema+"' AND table_type='BASE TABLE';";
			
			logger.info("SQL: "+sql);
			ResultSet rs = s.executeQuery(sql);
			while(rs.next())
			{	
				sTables.add(rs.getString("table_name"));
		    }
			s.close();
		}
		catch (SQLException e) {e.printStackTrace();}
		tables = new String[sTables.size()];
		for(int i=0;i<sTables.size();i++){tables[i]=sTables.get(i);}
	}
}
