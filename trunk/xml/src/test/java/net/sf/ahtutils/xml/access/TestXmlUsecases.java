package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlUsecases extends AbstractXmlAccessTest
{
	static Log logger = LogFactory.getLog(TestXmlUsecases.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"usecases.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Usecases actual = create();
    	Usecases expected = (Usecases)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Usecases.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Usecases create(){return create(true);}
    public static Usecases create(boolean withChilds)
    {
    	Usecases xml = new Usecases();
    	
    	if(withChilds)
    	{
    		xml.getUsecase().add(TestXmlUsecase.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlUsecases.initFiles();	
		TestXmlUsecases test = new TestXmlUsecases();
		test.save();
    }
}