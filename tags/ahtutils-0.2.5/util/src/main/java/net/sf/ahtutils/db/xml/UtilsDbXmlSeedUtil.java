package net.sf.ahtutils.db.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

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
	
	public static enum DataSource{ide,jar,path}
	public static String configKeySeed = "db.seed";
	public static String configKeyPathExport = "db.export.path";
	
	protected Db dbSeed;
	protected String seedPath;
	protected String pathPrefix;
	
	public UtilsDbXmlSeedUtil(Configuration config) throws FileNotFoundException
	{
		String dbSeedFile = config.getString(configKeySeed);
		logger.info("Using seed: "+dbSeedFile);
		dbSeed = JaxbUtil.loadJAXB(dbSeedFile, Db.class);
		try{dbSeed.setPathExport(config.getString(configKeyPathExport));} catch (NoSuchElementException e){}
		seedPath=dbSeed.getPathIde();
	}
	
	public UtilsDbXmlSeedUtil(Db dbSeed)
	{
		this(dbSeed,DataSource.ide);
	}
	
	public UtilsDbXmlSeedUtil(Db dbSeed, DataSource datasource)
	{
		this.dbSeed=dbSeed;
		seedPath=dbSeed.getPathIde();
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
	
	public String getExtractName(String extractId) throws UtilsConfigurationException {return getExtractName(DataSource.ide,extractId);}
	public String getExtractName(DataSource ds, String extractId) throws UtilsConfigurationException
	{
		StringBuffer sb = new StringBuffer();
		if(pathPrefix!=null){sb.append(pathPrefix).append("/");}
		
		switch(ds)
		{
			case ide: sb.append(seedPath);break;
			case path: sb.append(dbSeed.getPathExport());break;
			default: logger.warn("NYI ds="+ds);
		}
		
		sb.append("/");
		sb.append(getContentName(extractId));
		
		switch(ds)
		{
			case path: checkPath(sb.toString());break;
			default: ;
		}
		
		return sb.toString();
	}
	
	public void setPathPrefix(String pathPrefix) {this.pathPrefix = pathPrefix;}
	
	private void checkPath(String fileName) throws UtilsConfigurationException
	{
		if(!dbSeed.isSetPathExport()){throw new UtilsConfigurationException("Config element "+configKeyPathExport+" is not set!");}
		File base = new File(dbSeed.getPathExport());
		if(!base.exists() || !base.isDirectory()){throw new UtilsConfigurationException("Path "+dbSeed.getPathExport()+" does not exist");}
		
		File f = new File(fileName);
		if(!f.getParentFile().exists()){f.getParentFile().mkdirs();}
	}
	
	public void writeXml(DataSource ds, String key, Object o) throws UtilsConfigurationException
	{
		String fileName = getExtractName(ds,key);
		logger.info("Writing "+fileName);
		JaxbUtil.info(o);
		JaxbUtil.save(new File(fileName), o, true);
	}
}