package net.sf.ahtutils.xml.monitoring;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.identity.User;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTransmission extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTransmission.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Transmission.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Transmission actual = create(true);
    	Transmission expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Transmission.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Transmission create(boolean withChilds)
    {
    	Transmission xml = new Transmission();
    	
    	if(withChilds)
    	{
    		xml.setUser(new User());
    		xml.getDataSet().add(TestXmlDataSet.create(false));xml.getDataSet().add(TestXmlDataSet.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();

		TestXmlTransmission.initFiles();	
		TestXmlTransmission test = new TestXmlTransmission();
		test.save();
    }
}