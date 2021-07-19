package net.sf.ahtutils.net.auth;

import org.jeesl.JeeslUtilTestBootstrap;
import org.jeesl.util.db.ActiveDirectoryAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.jboss.CliJbossConfigConfigurator;

public class TstActiveDirectoryAuthenticator
{
	final static Logger logger = LoggerFactory.getLogger(CliJbossConfigConfigurator.class);
		
	private ActiveDirectoryAuthenticator adsAuth;
	
	public TstActiveDirectoryAuthenticator()
	{
		String domain = ".local";
		String ldapHost = "ldap://192.168.x.x:389";

		adsAuth = new ActiveDirectoryAuthenticator(domain,ldapHost);
		
	}
	
	public void direct()
	{
		logger.debug(""+adsAuth.authenticate("y", ""));
	}
	
	public static void main (String[] args) throws Exception
	{
		JeeslUtilTestBootstrap.init();
		TstActiveDirectoryAuthenticator test = new TstActiveDirectoryAuthenticator();
		test.direct();
	}
}