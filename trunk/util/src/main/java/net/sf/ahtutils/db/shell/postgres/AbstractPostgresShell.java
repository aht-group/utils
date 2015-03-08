package net.sf.ahtutils.db.shell.postgres;

import net.sf.ahtutils.db.shell.AbstractDatabaseShell;
import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.cmd.ShellCmdExport;
import net.sf.exlp.shell.cmd.ShellCmdUnset;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractPostgresShell extends AbstractDatabaseShell
{
	final static Logger logger = LoggerFactory.getLogger(AbstractPostgresShell.class);
	
	private static final String PGPWASSWORD = "PGPASSWORD";
	
	public AbstractPostgresShell(Configuration config,UtilsDbShell.Operation operation)
    {
		super(config,operation);
		//http://www.postgresonline.com/special_feature.php?sf_name=postgresql83_pg_dumprestore_cheatsheet
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
