package net.sf.ahtutils.xml.project;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.ahtutils.xml.security.TestXmlRole;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStaff extends AbstractXmlProjectTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStaff.class);
	
	@BeforeClass
	public static void initFiles()
	{
        fXml = new File(getXmlDir(dirSuffix),Staff.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Staff actual = create(true);
    	Staff expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Staff.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Staff create(boolean withChilds)
    {
    	Staff xml = new Staff();

    	if(withChilds)
    	{
    		xml.setRole(TestXmlRole.create(false));
    		xml.setStatus(TestXmlStatus.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlStaff.initFiles();
		TestXmlStaff test = new TestXmlStaff();
		test.save();
    }
}