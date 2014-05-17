package net.sf.ahtutils.xml.monitoring;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlActors extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlActors.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix,Actors.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Actors actual = create(true);
    	Actors expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Actors.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Actors create(boolean withChilds)
    {
    	Actors xml = new Actors();
    	
    	if(withChilds)
    	{
    		xml.getActor().add(TestXmlActor.create(false));
    		xml.getActor().add(TestXmlActor.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlActors.initJaxb();
		TestXmlActors.initFiles();	
		TestXmlActors test = new TestXmlActors();
		test.save();
    }
}