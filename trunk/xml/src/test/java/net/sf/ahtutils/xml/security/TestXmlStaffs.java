package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStaffs extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStaffs.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Staffs.class);}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Staffs actual = create(true);
    	Staffs expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Staffs.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Staffs create(boolean withChilds)
    {
    	Staffs xml = new Staffs();

    	if(withChilds)
    	{
    		xml.getStaff().add(TestXmlStaff.create(false));xml.getStaff().add(TestXmlStaff.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlStaffs.initFiles();
		TestXmlStaffs test = new TestXmlStaffs();
		test.save();
    }
}