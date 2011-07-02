package net.sf.ahtutils.test.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.Reports;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestReports extends AbstractXmlReportTest
{
	static Log logger = LogFactory.getLog(TestReports.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"reports.xml");
	}
    
    @Test
    public void testReports() throws FileNotFoundException
    {
    	Reports test = createReports();
    	Reports ref = (Reports)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Reports.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Reports reports = createReports();
    	JaxbUtil.debug2(this.getClass(),reports, nsPrefixMapper);
    	JaxbUtil.save(fXml, reports, nsPrefixMapper, true);
    }
    
    public static Reports createReports()
    {
    	Reports reports = new Reports();
    	reports.setDir("testDir");
    	reports.getReport().add(TestReport.createReport());
    	return reports;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestReports.initPrefixMapper();
		TestReports.initFiles();	
		TestReports test = new TestReports();
		test.save();
    }
}