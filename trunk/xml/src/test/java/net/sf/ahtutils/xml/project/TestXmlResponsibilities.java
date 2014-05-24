package net.sf.ahtutils.xml.project;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.security.TestXmlRoles;
import net.sf.ahtutils.xml.security.TestXmlUser;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlResponsibilities extends AbstractXmlProjectTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlResponsibilities.class);
	
	@BeforeClass
	public static void initFiles()
	{
        fXml = new File(getXmlDir(dirSuffix),Responsibilities.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Responsibilities actual = create(true);
    	Responsibilities expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Responsibilities.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Responsibilities create(boolean withChilds)
    {
    	Responsibilities xml = new Responsibilities();

    	if(withChilds)
    	{
    		xml.setRoles(TestXmlRoles.create(false));
    		xml.getUser().add(TestXmlUser.create(false));xml.getUser().add(TestXmlUser.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlResponsibilities.initFiles();
		TestXmlResponsibilities test = new TestXmlResponsibilities();
		test.save();
    }
}