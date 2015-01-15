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

public class TestXmlRole extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRole.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Role.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Role actual = create(true);
    	Role expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Role.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Role create(boolean withChilds)
    {
    	Role xml = new Role();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setActions(TestXmlActions.create(false));
    		xml.setViews(TestXmlViews.create(false));
    		xml.setUsecases(TestXmlUsecases.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlRole.initFiles();	
		TestXmlRole test = new TestXmlRole();
		test.save();
    }
}