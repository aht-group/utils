package net.sf.ahtutils.test.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractTgXmlTest;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.report.Report;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestReport extends AbstractTgXmlTest
{
	static Log logger = LogFactory.getLog(TestReport.class);
	
	private static final String rootDir = "src/test/resources/data/xml/report/report";
	
	private static File fReport;
	
	@BeforeClass
	public static void initFiles()
	{
		fReport = new File(rootDir,"report.xml");
	}
    
    @Test
    public void testReport() throws FileNotFoundException
    {
    	Report test = createReport();
    	Report ref = (Report)JaxbUtil.loadJAXB(fReport.getAbsolutePath(), Report.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Report report = createReport();
    	JaxbUtil.debug2(this.getClass(),report, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fReport, report, new AhtUtilsNsPrefixMapper(), true);
    	
    }
    
    public static Report createReport()
    {
    	Report report = new Report();
    	report.setDir("testDir");
    	report.setExample("testExampleXmlFile");
    	report.setId("testReportId");
    	report.setMediaPdf(TestMediaPdf.createMediaPdf());
    	report.setMediaXls(TestMediaXls.createMediaXls());
    	return report;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestReport.initFiles();	
		TestReport test = new TestReport();
		test.save();
    }
}