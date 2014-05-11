package net.sf.ahtutils.db.dump;

import java.io.File;

import net.sf.ahtutils.interfaces.db.UtilsDbDump;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresDump extends AbstractDatabaseDump
{
	final static Logger logger = LoggerFactory.getLogger(PostgresDump.class);
	
	public PostgresDump(Configuration config,UtilsDbDump.Operation operation)
    {
		super(config,operation);
    } 
	
	public String dumpTable(String table, boolean withStructure)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(binDump);
		sb.append(" -h ").append(dbHost);
		sb.append(" -U ").append(dbUser);
		sb.append(" -F p -C --column-inserts -v");
		sb.append(" -f ").append(dirSql+File.separator+table+".sql");
		sb.append(" -t '"+table+"' "+dbSchema);
		
		super.addLine(sb.toString());
		return sb.toString();
	}
}
