package net.sf.ahtutils.xml.dbseed;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDb extends AbstractXmlDbseedTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlDbseedTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"db.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Db actual = create();
    	Db expected = (Db)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Db.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Db create() {return create(true);}
    public static Db create(boolean withChilds)
    {
    	Db xml = new Db();
    	xml.setPath("myPath");
    	
    	if(withChilds)
    	{
    		xml.getSeed().add(TestSeed.createSeed(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
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