package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUsecases extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUsecases.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Usecases.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Usecases actual = create(true);
    	Usecases expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Usecases.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Usecases create(boolean withChilds)
    {
    	Usecases xml = new Usecases();
    	
    	if(withChilds)
    	{
    		xml.getUsecase().add(TestXmlUsecase.create(false));
    		xml.getUsecase().add(TestXmlUsecase.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlUsecases.initFiles();	
		TestXmlUsecases test = new TestXmlUsecases();
		test.save();
    }
}