package net.sf.ahtutils.db.xml;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.ahtutils.xml.dbseed.Seed;
import net.sf.ahtutils.xml.xpath.DbseedXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAhtDbXmlUtil
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtDbXmlUtil.class);
	
	public static enum DataSource{ide,jar}
	
	protected Db dbSeed;
	protected String prefix;
		
	public AbstractAhtDbXmlUtil(Db dbSeed, DataSource datasource)
	{
		this.dbSeed=dbSeed;
		prefix=dbSeed.getPath();
	}
	
	
	protected String getContentName(String extractId) throws AhtUtilsConfigurationException
	{
		try
		{
			Seed seed = DbseedXpath.getSeed(dbSeed, extractId);
			return seed.getContent();
		}
		catch (ExlpXpathNotFoundException e) {throw new AhtUtilsConfigurationException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new AhtUtilsConfigurationException(e.getMessage());}
	}
	
	protected String getExtractName(String extractId) throws AhtUtilsConfigurationException
	{
		return prefix+"/"+getContentName(extractId);
	}
}