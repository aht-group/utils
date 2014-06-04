package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlDeclaration;
import net.sf.ahtutils.xml.status.TestXmlStatement;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTest extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,net.sf.ahtutils.xml.qa.Test.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	net.sf.ahtutils.xml.qa.Test actual = create(true);
    	net.sf.ahtutils.xml.qa.Test expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), net.sf.ahtutils.xml.qa.Test.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static net.sf.ahtutils.xml.qa.Test create(boolean withChilds)
    {
    	net.sf.ahtutils.xml.qa.Test xml = new net.sf.ahtutils.xml.qa.Test();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
    		xml.setStatus(TestXmlStatus.create(false));
    		xml.setDeclaration(TestXmlDeclaration.create(false));
    		xml.setStatement(TestXmlStatement.create(false));
    		
    		xml.setReference(TestXmlReference.create());
    		xml.setDescription(TestXmlDescription.create());
    		xml.setPreCondition(TestXmlPreCondition.create());
    		xml.setSteps(TestXmlSteps.create());
    		xml.setExpected(TestXmlExpected.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlTest.initJaxb();
		TestXmlTest.initFiles();	
		TestXmlTest test = new TestXmlTest();
		test.save();
    }
}