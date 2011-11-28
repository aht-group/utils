package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUsecaseCategory extends AbstractXmlAccessTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUsecases.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"usecaseCategory.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	UsecaseCategory actual = create();
    	UsecaseCategory expected = (UsecaseCategory)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), UsecaseCategory.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static UsecaseCategory create(){return create(true);}
    public static UsecaseCategory create(boolean withChilds)
    {
    	UsecaseCategory xml = new UsecaseCategory();
    	xml.setCode("myCode");
    	xml.setIndex(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setUsecases(TestXmlUsecases.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlUsecaseCategory.initFiles();	
		TestXmlUsecaseCategory test = new TestXmlUsecaseCategory();
		test.save();
    }
}