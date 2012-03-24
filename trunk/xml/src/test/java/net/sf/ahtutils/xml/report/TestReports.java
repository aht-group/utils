package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
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
    	reports.setResources("reports.ahtutils-util/resources.xml");
    	reports.setTemplates("reports.ahtutils-util/templates.xml");
    	reports.getReport().add(TestReport.create());
    	return reports;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestReports.initPrefixMapper();
		TestReports.initFiles();	
		TestReports test = new TestReports();
		test.save();
    }
}