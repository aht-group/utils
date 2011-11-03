package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.Info;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestInfo extends AbstractXmlReportTest
{
	static Log logger = LogFactory.getLog(TestInfo.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"info.xml");
	}
    
    @Test
    public void testInfo() throws FileNotFoundException
    {
    	Info test = createInfo();
    	Info ref = (Info)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Info.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Info info = createInfo();
    	JaxbUtil.debug2(this.getClass(),info, nsPrefixMapper);
    	JaxbUtil.save(fXml, info, nsPrefixMapper, true);
    }
    
    public static Info createInfo()
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
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestInfo.initPrefixMapper();
		TestInfo.initFiles();	
		TestInfo test = new TestInfo();
		test.save();
    }
}