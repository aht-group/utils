package net.sf.ahtutils.doc.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsListingCopy
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsListingCopy.class);
		
	private String prefix = "listing.aht-utils";
	
	public static final String codeApp = "@@@APP@@@";
	
	public static final String dbPostgresPrepartionDebian = "admin/db/postgres/dump/debian/preparation.sh";
	public static final String dbPostgresCronDebian = "admin/db/postgres/dump/debian/cron.tab";
	public static final String dbPostgresDumpDebian = "admin/db/postgres/dump/debian/dump.sh";
	
	
	public static final String dbPostgresCreateDebianWheezy = "admin/db/postgres/create/debian/wheezy.sql";
	public static final String dbPostgresCreateOsxYosemite = "admin/db/postgres/create/osx/yosemite.sql";
	
	public static final String dbPostgresTemplateDebianWheezy = "admin/db/postgres/template/debian/wheezy.sql";
	
	public static final String dbPostgresTerminateDebianWheezy = "admin/db/postgres/terminate/debian/wheezy.sql";
	
	public static final String dbPostgresTuneDebianWheezy = "admin/db/postgres/tuning/debian/wheezy.sql";
	public static final String dbPostgresTuneOsxYosemite = "admin/db/postgres/tuning/osx/yosemite.sql";
	
	public static final String jbServicePreparation = "admin/installation/jboss/service/preparation.txt";
	public static final String jbService = "admin/installation/jboss/service/etcInitD.txt";
	
	public static final String jbEap6StartLog = "admin/installation/jboss/deployment/eap6/log/start.txt";
	public static final String jbEap6DeployCli = "admin/installation/jboss/deployment/eap6/cli/deploy.sh";
	public static final String jbEap6DeployLog = "admin/installation/jboss/deployment/eap6/log/deploy.txt";
	public static final String jbEap6UndeployCli = "admin/installation/jboss/deployment/eap6/cli/undeploy.sh";
	public static final String jbEap6UndeployLog = "admin/installation/jboss/deployment/eap6/log/undeploy.txt";
	public static final String jbEap6UpdateCli = "admin/installation/jboss/deployment/eap6/cli/update.sh";
	public static final String jbEap6ConfigMem = "admin/installation/jboss/config/eap6/standalone.mem.txt";
	
	public static final String apacheProxy = "admin/installation/apache/proxy.txt";
	public static final String apacheRedirect = "admin/installation/apache/redirect.html";
	
	
	private MultiResourceLoader mrl;
	private File dirListing;
	
	public UtilsListingCopy(Configuration config)
	{
		mrl = new MultiResourceLoader();
		File baseDoc = new File(config.getString(UtilsDocumentation.keyBaseDocDir));
		dirListing = new File(baseDoc,"listing");
		logger.info("Using base.dir ("+UtilsDocumentation.keyBaseDocDir+"): "+baseDoc.getAbsolutePath());
		logger.info("Using listing.dir (base.dir/listing): "+dirListing.getAbsolutePath());
	}
	
	public UtilsListingCopy(String prefix,File baseDoc)
	{
		this.prefix=prefix;
		mrl = new MultiResourceLoader();
		dirListing = new File(baseDoc,"listing");
		logger.info("Using base.dir ("+UtilsDocumentation.keyBaseDocDir+"): "+baseDoc.getAbsolutePath());
		logger.info("Using listing.dir (base.dir/listing): "+dirListing.getAbsolutePath());
	}
	
	public void copy(String src, String dst, String find, String replace) throws UtilsConfigurationException
	{
		try
		{
			InputStream is = mrl.searchIs(prefix+"/"+src);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bris = new BufferedReader(isr);
			
			ExlpTxtWriter writer = new ExlpTxtWriter();
			String line; 
			while(null != (line = bris.readLine()))
			{
				line = line.replace(find,replace);
				writer.add(line);
				logger.trace("Line: "+line);
			}
			File target = new File(dirListing,dst);
			logger.info("Writing to :"+target.getAbsolutePath());
			writer.writeFile(target);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}