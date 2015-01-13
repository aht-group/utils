package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUsecase extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUsecase.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Usecase.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Usecase actual = create(true);
    	Usecase expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Usecase.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Usecase create(boolean withChilds)
    {
    	Usecase xml = new Usecase();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));

    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlUsecase.initFiles();	
		TestXmlUsecase test = new TestXmlUsecase();
		test.save();
    }
}