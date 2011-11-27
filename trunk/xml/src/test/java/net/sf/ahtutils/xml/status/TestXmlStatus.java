package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStatus extends AbstractXmlStatusTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStatus.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"status.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Status actual = create();
    	Status expected = (Status)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Status.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Status create(){return create(true);}
    public static Status create(boolean withChilds)
    {
    	Status xml = new Status();
    	xml.setCode("myCode");
    	xml.setVisible(true);
    	xml.setGroup("myGroup");
    	xml.setDscr("myDescription");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.getLang().add(TestXmlLang.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlStatus.initFiles();	
		TestXmlStatus test = new TestXmlStatus();
		test.save();
    }
}