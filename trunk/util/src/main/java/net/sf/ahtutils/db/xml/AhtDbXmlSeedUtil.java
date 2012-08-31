package net.sf.ahtutils.db.xml;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.ahtutils.xml.dbseed.Seed;
import net.sf.ahtutils.xml.xpath.DbseedXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AhtDbXmlSeedUtil
{
	final static Logger logger = LoggerFactory.getLogger(AhtDbXmlSeedUtil.class);
	
	public static enum DataSource{ide,jar}
	
	protected Db dbSeed;
	protected String seedPath,pathPrefix;
	
	public AhtDbXmlSeedUtil(Db dbSeed)
	{
		this(dbSeed,DataSource.ide);
	}
	
	public AhtDbXmlSeedUtil(Db dbSeed, DataSource datasource)
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