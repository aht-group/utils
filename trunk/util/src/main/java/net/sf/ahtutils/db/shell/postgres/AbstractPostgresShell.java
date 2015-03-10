package net.sf.ahtutils.db.shell.postgres;

import java.util.NoSuchElementException;

import net.sf.ahtutils.db.shell.AbstractDatabaseShell;
import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.factory.xml.config.XmlParameterFactory;
import net.sf.exlp.shell.cmd.ShellCmdExport;
import net.sf.exlp.shell.cmd.ShellCmdUnset;
import net.sf.exlp.xml.config.Parameter;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractPostgresShell extends AbstractDatabaseShell
{
	final static Logger logger = LoggerFactory.getLogger(AbstractPostgresShell.class);
	
	private static final String PGPWASSWORD = "PGPASSWORD";
	
	protected Parameter pPsql;
	
	public AbstractPostgresShell(Configuration config,UtilsDbShell.Operation operation)
    {
		super(config,operation);
		
		pPsql = XmlParameterFactory.build(UtilsDbShell.cfgBinRestore, "Shell command psql", false);
		try{pPsql.setValue(config.getString(pPsql.getKey()));}
		catch (NoSuchElementException e){pPsql.setValue("psql");}
		configurationParamter.getParameter().add(pPsql);
		
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
