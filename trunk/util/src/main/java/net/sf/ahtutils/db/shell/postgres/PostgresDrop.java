package net.sf.ahtutils.db.shell.postgres;

import java.util.Arrays;
import java.util.NoSuchElementException;

import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.factory.xml.config.XmlParameterFactory;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresDrop extends AbstractPostgresShell implements UtilsDbShell
{
	final static Logger logger = LoggerFactory.getLogger(PostgresDrop.class);
	
	public PostgresDrop(Configuration config)
    {
		super(config,UtilsDbShell.Operation.restore);
		
		pShellCommand = XmlParameterFactory.build(UtilsDbShell.cfgBinDrop, "Shell command for drop", false);
		try{pShellCommand.setValue(config.getString(pShellCommand.getKey()));}
		catch (NoSuchElementException e){pShellCommand.setValue("psql");}
		configurationParamter.getParameter().add(pShellCommand);
    }
	
	public void buildCommands(boolean withStructure) throws ExlpUnsupportedOsException
	{		
		super.cmdPre();

		for(String table : Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbTablesDrop))){dropTable(table);}
		
		super.cmdPost();
	}
	
	private String dropTable(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pShellCommand.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" -c '");
		sb.append("DROP TABLE IF EXISTS ").append(table);
		sb.append(";'");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
}
