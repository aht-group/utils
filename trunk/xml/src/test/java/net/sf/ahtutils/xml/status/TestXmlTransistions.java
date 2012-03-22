package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTransistions extends AbstractXmlStatusTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTransistions.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"transitions.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Transitions actual = create(true);
    	Transitions expected = (Transitions)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Transitions.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Transitions create(boolean withChilds)
    {
    	Transitions xml = new Transitions();
    	
    	if(withChilds)
    	{
    		xml.getStatus().add(TestXmlStatus.create(false));
    		xml.getStatus().add(TestXmlStatus.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestXmlTransistions.initFiles();	
		TestXmlTransistions test = new TestXmlTransistions();
		test.save();
    }
}