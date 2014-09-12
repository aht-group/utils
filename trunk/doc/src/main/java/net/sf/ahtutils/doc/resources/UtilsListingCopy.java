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
		
	private static final String prefix = "listing.aht-utils";
	
	public static final String codeApp = "@@@APP@@@";
	
	public static final String dbPostgresPrepartion = "admin/db/postgres/preparation.sh";
	public static final String dbPostgresDump = "admin/db/postgres/dump.sh";
	public static final String dbPostgresCrontab = "admin/db/postgres/cron.tab";
	
	
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
				logger.info("Line: "+line);
			}
			File target = new File(dirListing,dst);
			logger.info("Writing to :"+target.getAbsolutePath());
			writer.writeFile(target);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}