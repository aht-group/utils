package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.ahtutils.xml.dbseed.Seed;
import net.sf.ahtutils.xml.xpath.DbseedXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsDbXmlSeedUtil
{
	final static Logger logger = LoggerFactory.getLogger(UtilsDbXmlSeedUtil.class);
	
	public static enum DataSource{ide,jar}
	public static String configKeySeed = "db.seed";
	
	protected Db dbSeed;
	protected String seedPath,pathPrefix;
	
	public UtilsDbXmlSeedUtil(Configuration config) throws FileNotFoundException
	{
		String dbSeedFile = config.getString(configKeySeed);
		logger.info("Using seed: "+dbSeedFile);
		Db dbSeed = JaxbUtil.loadJAXB(dbSeedFile, Db.class);
		this.dbSeed=dbSeed;
		seedPath=dbSeed.getPath();
	}
	
	public UtilsDbXmlSeedUtil(Db dbSeed)
	{
		this(dbSeed,DataSource.ide);
	}
	
	public UtilsDbXmlSeedUtil(Db dbSeed, DataSource datasource)
	{
		this.dbSeed=dbSeed;
		seedPath=dbSeed.getPath();
	}
	
	public String getContentName(String extractId) throws UtilsConfigurationException
	{
		try
		{
			Seed seed = DbseedXpath.getSeed(dbSeed, extractId);
			return seed.getContent();
		}
		catch (ExlpXpathNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public String getExtractName(String extractId) throws UtilsConfigurationException
	{
		StringBuffer sb = new StringBuffer();
		if(pathPrefix!=null){sb.append(pathPrefix).append("/");}
		sb.append(seedPath).append("/");
		sb.append(getContentName(extractId));
		return sb.toString();
	}
	
	public void setPathPrefix(String pathPrefix) {this.pathPrefix = pathPrefix;}
}