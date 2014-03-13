package net.sf.ahtutils.net.auth;

import net.sf.ahtutils.net.auth.ads.ActiveDirectoryAuthenticator;
import net.sf.ahtutils.test.AhtUtilsTstBootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TstActiveDirectoryAuthenticator
{
	static Log logger = LogFactory.getLog(TstActiveDirectoryAuthenticator.class);
		
	private ActiveDirectoryAuthenticator adsAuth;
	
	public TstActiveDirectoryAuthenticator()
	{
		String domain = ".local";
		String ldapHost = "ldap://192.168.x.x:389";

		adsAuth = new ActiveDirectoryAuthenticator(domain,ldapHost);
		
	}
	
	public void direct()
	{
		logger.debug(adsAuth.authenticate("y", ""));
	}
	
	public static void main (String[] args) throws Exception
	{
		AhtUtilsTstBootstrap.init();
		TstActiveDirectoryAuthenticator test = new TstActiveDirectoryAuthenticator();
		test.direct();
		
	}
}