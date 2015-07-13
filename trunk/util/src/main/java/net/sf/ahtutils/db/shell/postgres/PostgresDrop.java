package net.sf.ahtutils.db.shell.postgres;

import java.util.Arrays;

import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresDrop extends AbstractPostgresShell implements UtilsDbShell
{
	final static Logger logger = LoggerFactory.getLogger(PostgresDrop.class);
	
	public PostgresDrop(Configuration config)
    {
		super(config,UtilsDbShell.Operation.restore);
    }
	
	public void buildCommands(boolean withStructure) throws ExlpUnsupportedOsException
	{		
		super.cmdPre();

		for(String table : Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbTablesDrop))){dropTable(table);}
		for(String seq : Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbSequenceDrop))){dropSequence(seq);}
		debugDatabase();
		super.cmdPost();
	}
	
	private String dropTable(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" -c '");
		sb.append("DROP TABLE IF EXISTS ").append(table);
		sb.append(";'");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	private String dropSequence(String seq)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" -c '");
		sb.append("DROP SEQUENCE IF EXISTS ").append(seq);
		sb.append(";'");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	private String debugDatabase()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" -c '");
		sb.append("\\d ");
		sb.append(";'");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
}
