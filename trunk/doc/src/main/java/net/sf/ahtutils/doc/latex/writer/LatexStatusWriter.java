package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.db.xml.UtilsDbXmlSeedUtil;
import net.sf.ahtutils.doc.ofx.status.OfxStatusTableFactory;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.CrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexStatusWriter extends AbstractDocumentationLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(LatexStatusWriter.class);
	
	private final static String dirStatus = "tab/status";
	
	private UtilsDbXmlSeedUtil seedUtil;
	
	public LatexStatusWriter(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm) throws UtilsConfigurationException
	{
		super(config,translations,langs,cmm);
		
		String dbSeedFile = config.getString(UtilsDbXmlSeedUtil.configKeySeed);
		logger.debug("Using seed: "+dbSeedFile);
		try
		{
			Db dbSeed = (Db)JaxbUtil.loadJAXB(dbSeedFile, Db.class);
			seedUtil = new UtilsDbXmlSeedUtil(dbSeed);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public void buildStatusTable(String seedKeyStatus) throws UtilsConfigurationException
	{
		buildStatusTable(seedKeyStatus, 10,30,40);
	}
	public void buildStatusTable(String seedKeyStatus, String seedKeyParent) throws UtilsConfigurationException
	{
		buildStatusTable(seedKeyStatus, seedKeyParent, 15,10,30,40);
	}
	
	public void buildStatusTable(String seedKeyStatus, int... colWidths) throws UtilsConfigurationException
	{
		buildStatusTable(seedKeyStatus, null, colWidths);
	}
	public void buildStatusTable(String seedKeyStatus, String seedKeyParent, int... colWidths) throws UtilsConfigurationException
	{
		String[] headerKeys3 = {"auTableStatusCode","auTableStatusName","auTableStatusDescription"};
		String[] headerKeys4 = {"auTableStatusParent","auTableStatusCode","auTableStatusName","auTableStatusDescription"};
		
		String[] headerKeys;
		if(seedKeyParent==null){headerKeys=headerKeys3;}
		else{headerKeys=headerKeys4;}
		
		Aht athStatus;
		Aht ahtParents = null;
		
		try
		{
			athStatus = JaxbUtil.loadJAXB(seedUtil.getExtractName(seedKeyStatus), Aht.class);
			if(seedKeyParent!=null){ahtParents = JaxbUtil.loadJAXB(seedUtil.getExtractName(seedKeyParent), Aht.class);}
			
			String texName = seedUtil.getContentName(seedKeyStatus);
			texName = texName.substring(0, texName.indexOf(".xml"));
			logger.info(texName);
			for(String lang : langs)
			{
				OfxStatusTableFactory fOfx = new OfxStatusTableFactory(config,lang,translations);
				fOfx.setColWidths(colWidths);
				String content = fOfx.buildLatexTable(texName.replaceAll("/", "."),athStatus, headerKeys, ahtParents);
				File f = new File(baseLatexDir+"/"+lang+"/"+dirStatus+"/"+texName+".tex");
				StringIO.writeTxt(f, content);
			}
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}