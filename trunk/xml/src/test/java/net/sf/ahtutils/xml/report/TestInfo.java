package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestInfo extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestInfo.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"info.xml");
	}
    
    @Test
    public void testInfo() throws FileNotFoundException
    {
    	Info test = create();
    	Info ref = (Info)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Info.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Info create()
    {
    	Info info = new Info();
    	Info.Title title = new Info.Title();
    	title.setValue("testTitle");
    	info.setTitle(title);
    	Info.Subtitle subtitle = new Info.Subtitle();
    	subtitle.setValue("testSubTitle");
    	info.setSubtitle(subtitle);
    	Info.Footer footer = new Info.Footer();
    	footer.setValue("testFooter");
    	info.setFooter(footer);
    	Info.Record record = new Info.Record();
    	record.setValue(DateUtil.getXmlGc4D(getDefaultDate()));
    	info.setRecord(record);
    	return info;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestInfo.initJaxb();
		TestInfo.initFiles();	
		TestInfo test = new TestInfo();
		test.save();
    }
}