package net.sf.ahtutils.db.xml;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.exlp.util.io.compression.JarExtractor;
import net.sf.exlp.util.io.compression.JarStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractAhtDbXmlExtract extends AbstractAhtDbXmlUtil
{
	static Log logger = LogFactory.getLog(AbstractAhtDbXmlExtract.class);

	protected String templateDir;
	protected JarStream jarStream;
	private Set<String> extractedIds;
	
	public AbstractAhtDbXmlExtract(Db dbSeed, DataSource datasource, JarStream jarStream)
	{
		super(dbSeed, datasource);
		this.jarStream=jarStream;
		logger.warn("NYI: TemplateDir");
//		templateDir=config.getString("db/extract/@dirTemplate");
		extractedIds = new HashSet<String>();
	}
	
	protected String getTemplate(String extractId)
	{
		logger.warn("NYI: getTemplate");
		return null;//templateDir+"/"+config.getString("db/extract/file[@id='"+extractId+"']/@template");
	}
	
	protected void addExtractId(String id) throws AhtUtilsConfigurationException
	{
		logger.debug(id+" "+getTemplate(id)+" -> "+getExtractName(id));
		if(extractedIds.contains(id)){logger.warn("extractedIds already containes "+id);}
		extractedIds.add(id);
	}
	
	public void ideUpdate() throws AhtUtilsConfigurationException
	{
		Iterator<String> iterator = extractedIds.iterator();
		while(iterator.hasNext())
		{
			singleJarExtract(iterator.next());
		}
	}
	
	public void singleJarExtract(String extractId) throws AhtUtilsConfigurationException
	{
		String from = getExtractName(extractId);
		logger.warn("NYI: singleJarExtract");
		String to = null;//= config.getString("db/prefix[@type='ide']")+"/"+getContentName(extractId);
		singleJarExtract(from, to);
	}
	
	public void singleJarExtract(String from, String to)
	{
		logger.warn("NYI: singleJarExtract");
		String jarName = null;//config.getString("db/prefix[@type='jar']/@file");
		StringBuffer sb = new StringBuffer();
			sb.append("Extracting "+jarName);
			sb.append(" from jar to "+to);
		logger.debug(sb);
		JarExtractor.extract(jarName, from,to);
	}
}