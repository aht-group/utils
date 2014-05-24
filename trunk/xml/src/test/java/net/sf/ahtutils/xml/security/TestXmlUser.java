package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUser extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUser.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,User.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	User actual = create(true);
    	User expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), User.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static User create(boolean withChilds)
    {
    	User xml = new User();
    	xml.setFirstName("myFirst");
    	xml.setLastName("myLast");

    	if(withChilds)
    	{
    		xml.setStaffs(TestXmlStaffs.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlUser.initFiles();
		TestXmlUser test = new TestXmlUser();
		test.save();
    }
}