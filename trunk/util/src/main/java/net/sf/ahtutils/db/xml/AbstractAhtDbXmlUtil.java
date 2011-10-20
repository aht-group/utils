package net.sf.ahtutils.db.xml;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.ahtutils.xml.dbseed.Seed;
import net.sf.ahtutils.xml.xpath.DbseedXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractAhtDbXmlUtil
{
	static Log logger = LogFactory.getLog(AbstractAhtDbXmlUtil.class);
	
	public static enum DataSource{ide,jar}
	
	protected Configuration config;
	protected Db dbSeed;
	protected String prefix;
	
	public AbstractAhtDbXmlUtil(Configuration config, DataSource datasource)
	{
		this.config=config;
		setPrefix(datasource);
	}
	
	public AbstractAhtDbXmlUtil(Db dbSeed, DataSource datasource)
	{
		this.dbSeed=dbSeed;
		setPrefix(datasource);
	}
	
	private void setPrefix(DataSource datasource)
	{
		prefix=dbSeed.getPath();
		
		if(datasource.toString().equals(DataSource.jar.toString()))
		{
			MultiResourceLoader mrl = new MultiResourceLoader();
			mrl.setDebugError(false);
			String searchPath = config.getString("db/prefix[@type='"+DataSource.jar.toString()+"']")+"/active";
			if(!mrl.isAvailable(searchPath))
			{
				logger.warn("Datasource jar is selected, but Token not available "+searchPath);
				logger.warn("Fallback to "+DataSource.ide);
				setPrefix(DataSource.ide);
			}
		}
		logger.info("Prefix is "+prefix);
	}
	
	protected String getTargetName(String extractId) throws AhtUtilsConfigurationException
	{
		if(config!=null){return config.getString("db/extract/file[@id='"+extractId+"']/@target");}
		else if(dbSeed!=null)
		{
			try
			{
				Seed seed = DbseedXpath.getSeed(dbSeed, extractId);
				return seed.getContent();
			}
			catch (ExlpXpathNotFoundException e) {throw new AhtUtilsConfigurationException(e.getMessage());}
			catch (ExlpXpathNotUniqueException e) {throw new AhtUtilsConfigurationException(e.getMessage());}
		}
		throw new AhtUtilsConfigurationException("config AND dbSeed == null !!");
	}
	
	protected String getExtractName(String extractId) throws AhtUtilsConfigurationException
	{
		return prefix+"/"+getTargetName(extractId);
	}
}