package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlInfo extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlInfo.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix,"info");
	}
    
    @Test
    public void testInfo() throws FileNotFoundException
    {
    	Info test = create();
    	Info ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Info.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Info create()
    {
    	Info xml = new Info();
    	Info.Title title = new Info.Title();
    	title.setValue("testTitle");
    	xml.setTitle(title);
    	Info.Subtitle subtitle = new Info.Subtitle();
    	subtitle.setValue("testSubTitle");
    	xml.setSubtitle(subtitle);
    	Info.Footer footer = new Info.Footer();
    	footer.setValue("testFooter");
    	xml.setFooter(footer);
    	Info.Record record = new Info.Record();
    	record.setValue(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	xml.setRecord(record);
    	
    	xml.setFile(TestXmlFile.create());
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlInfo.initJaxb();
		TestXmlInfo.initFiles();	
		TestXmlInfo test = new TestXmlInfo();
		test.save();
    }
}