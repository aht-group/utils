package net.sf.ahtutils.db.shell.postgres;

import java.util.NoSuchElementException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.shell.AbstractDatabaseShell;
import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.factory.xml.config.XmlParameterFactory;
import net.sf.exlp.shell.cmd.ShellCmdExport;
import net.sf.exlp.shell.cmd.ShellCmdUnset;

public class AbstractPostgresShell extends AbstractDatabaseShell
{
	final static Logger logger = LoggerFactory.getLogger(AbstractPostgresShell.class);
	
	private static final String PGPWASSWORD = "PGPASSWORD";
	
	public AbstractPostgresShell(Configuration config,UtilsDbShell.Operation operation)
    {
		super(config,operation);
		
		pDbShell = XmlParameterFactory.build(UtilsDbShell.cfgBinPsql, "Shell command psql", false);
		try{pDbShell.setValue(config.getString(pDbShell.getKey()));}
		catch (NoSuchElementException e){pDbShell.setValue("psql");}
		configurationParamter.getParameter().add(pDbShell);
		
    } 
	
	public void cmdPre() throws ExlpUnsupportedOsException
	{
		super.addLine(ShellCmdExport.export(PGPWASSWORD,pDbPwd.getValue()));
		super.addLine("");
	}
	
	public void cmdPost() throws ExlpUnsupportedOsException
	{
		super.addLine("");
		super.addLine(ShellCmdUnset.unset(PGPWASSWORD));
	}
}
