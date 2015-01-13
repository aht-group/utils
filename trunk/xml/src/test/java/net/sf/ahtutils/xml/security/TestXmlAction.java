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

public class TestXmlAction extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAction.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Action.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Action actual = create(true);
    	Action expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Action.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Action create(boolean withChilds)
    {
    	Action xml = new Action();
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
			
		TestXmlAction.initFiles();	
		TestXmlAction test = new TestXmlAction();
		test.save();
    }
}