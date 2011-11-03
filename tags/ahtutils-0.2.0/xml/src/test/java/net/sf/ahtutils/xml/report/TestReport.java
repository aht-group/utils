package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.Report;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestReport extends AbstractXmlReportTest
{
	static Log logger = LogFactory.getLog(TestReport.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"report.xml");
	}
    
    @Test
    public void testReport() throws FileNotFoundException
    {
    	Report test = createReport();
    	Report ref = (Report)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Report.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Report report = createReport();
    	JaxbUtil.debug2(this.getClass(),report, nsPrefixMapper);
    	JaxbUtil.save(fXml, report, nsPrefixMapper, true);
    }
    
    public static Report createReport(){return createReport("testReportId");}
    public static Report createReport(String id)
    {
    	Report report = new Report();
    	report.setId(id);
    	report.setDir("testDir");
    	report.setExample("testExampleXmlFile");
    	report.getMedia().add(TestMedia.createMedia("pdf"));
    	report.getMedia().add(TestMedia.createMedia("xls"));
    	report.setLtr(true);
    	report.setRtl(false);
    	return report;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestReport.initPrefixMapper();
		TestReport.initFiles();	
		TestReport test = new TestReport();
		test.save();
    }
}