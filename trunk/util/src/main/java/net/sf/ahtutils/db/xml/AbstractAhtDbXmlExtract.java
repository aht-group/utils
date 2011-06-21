package net.sf.ahtutils.db.xml;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.exlp.util.io.compression.JarExtractor;
import net.sf.exlp.util.io.compression.JarStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractAhtDbXmlExtract extends AbstractAhtDbXmlUtil
{
	static Log logger = LogFactory.getLog(AbstractAhtDbXmlExtract.class);

	protected String templateDir;
	protected JarStream jarStream;
	private Set<String> extractedIds;
	
	public AbstractAhtDbXmlExtract(Configuration config, DataSource datasource, JarStream jarStream)
	{
		super(config, datasource);
		this.jarStream=jarStream;
		templateDir=config.getString("db/extract/@dirTemplate");
		extractedIds = new HashSet<String>();
	}
	
	protected String getTemplate(String extractId)
	{
		return templateDir+"/"+config.getString("db/extract/file[@id='"+extractId+"']/@template");
	}
	
	protected void addExtractId(String id)
	{
		logger.debug(id+" "+getTemplate(id)+" -> "+getExtractName(id));
		if(extractedIds.contains(id)){logger.warn("extractedIds already containes "+id);}
		extractedIds.add(id);
	}
	
	public void ideUpdate()
	{
		Iterator<String> iterator = extractedIds.iterator();
		while(iterator.hasNext())
		{
			singleJarExtract(iterator.next());
		}
	}
	
	public void singleJarExtract(String extractId)
	{
		
		String from = getExtractName(extractId);
		String to = config.getString("db/prefix[@type='ide']")+"/"+getTargetName(extractId);
		singleJarExtract(from, to);
	}
	
	public void singleJarExtract(String from, String to)
	{
		String jarName = config.getString("db/prefix[@type='jar']/@file");
		StringBuffer sb = new StringBuffer();
			sb.append("Extracting "+jarName);
			sb.append(" from jar to "+to);
		logger.debug(sb);
		JarExtractor.extract(jarName, from,to);
	}
}