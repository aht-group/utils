package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestReports extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestReports.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"reports.xml");
	}
    
    @Test
    public void testReports() throws FileNotFoundException
    {
    	Reports test = create();
    	Reports ref = (Reports)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Reports.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Reports create()
    {
    	Reports reports = new Reports();
    	reports.setDir("testDir");
    	reports.getReport().add(TestReport.create());
    	return reports;
    }
    
    public void save() {save(create(),fXml);}
	
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