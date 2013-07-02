package net.sf.ahtutils.xml.monitoring;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlComponent extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlComponent.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Component.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Component actual = create(true);
    	Component expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Component.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Component create(boolean withChilds)
    {
    	Component xml = new Component();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.getIndicator().add(TestXmlIndicator.create(false));xml.getIndicator().add(TestXmlIndicator.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlComponent.initJaxb();
		TestXmlComponent.initFiles();	
		TestXmlComponent test = new TestXmlComponent();
		test.save();
    }
}