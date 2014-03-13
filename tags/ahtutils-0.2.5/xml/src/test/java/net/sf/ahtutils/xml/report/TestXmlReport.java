package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlReport extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlReport.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,"report");
	}
    
    @Test
    public void testReport() throws FileNotFoundException
    {
    	Report test = create();
    	Report ref = (Report)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Report.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Report create(){return create("testReportId");}
    public static Report create(String id)
    {
    	Report report = new Report();
    	report.setId(id);
    	report.setDir("testDir");
    	report.setExample("testExampleXmlFile");
    	report.getMedia().add(TestMedia.create("pdf"));
    	report.getMedia().add(TestMedia.create("xls"));
    	report.setLtr(true);
    	report.setRtl(false);
    	return report;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlReport.initJaxb();
		TestXmlReport.initFiles();
		TestXmlReport test = new TestXmlReport();
		test.save();
    }
}