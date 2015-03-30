package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRoles extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRoles.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Roles.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Roles actual = create(true);
    	Roles expected = (Roles)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Roles.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Roles create(boolean withChilds)
    {
    	Roles xml = new Roles();
    	xml.setType("myType");
    	
    	if(withChilds)
    	{
    		xml.getRoles().add(TestXmlRoles.create(false));xml.getRoles().add(TestXmlRoles.create(false));
    		xml.getRole().add(TestXmlRole.create(false));xml.getRole().add(TestXmlRole.create(false));
    		
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlRoles.initFiles();	
		TestXmlRoles test = new TestXmlRoles();
		test.save();
    }
}