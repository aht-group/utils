package net.sf.ahtutils.xml.dbseed;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDb extends AbstractXmlDbseedTest
{
	static Log logger = LogFactory.getLog(TestDb.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"db.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Db test = createDbWithChilds();
    	Db ref = (Db)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Db.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Db xml = createDbWithChilds();
    	JaxbUtil.debug2(this.getClass(),xml, nsPrefixMapper);
    	JaxbUtil.save(fXml, xml, nsPrefixMapper, true);
    }
    
    public static Db createDb()
    {
    	Db xml = new Db();
    	return xml;
    }
    
    public static Db createDbWithChilds()
    {
    	Db xml = createDb();
    	xml.getSeed().add(TestSeed.createSeed());
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestDb.initPrefixMapper();
		TestDb.initFiles();	
		TestDb test = new TestDb();
		test.save();
    }
}