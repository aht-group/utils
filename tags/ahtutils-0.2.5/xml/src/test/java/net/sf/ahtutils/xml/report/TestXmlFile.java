package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFile extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFile.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix,File.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	File test = create();
    	File ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), File.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static File create()
    {
    	File xml = new File();
    	xml.setValue("myFileName");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlFile.initJaxb();
		TestXmlFile.initFiles();	
		TestXmlFile test = new TestXmlFile();
		test.save();
    }
}