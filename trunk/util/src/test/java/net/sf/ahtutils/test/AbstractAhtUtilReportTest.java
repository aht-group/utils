package net.sf.ahtutils.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.ahtutils.report.ReportController;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.ahtutils.xml.report.Resources;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

public class AbstractAhtUtilReportTest extends AbstractAhtUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilReportTest.class);
	
	protected static String reportRoot;
	protected static String configFile;
    protected static String resourcesFile;
    protected static String reportId;
    
    protected static Reports reports;
    protected static Resources resources;
    
    protected Document docReport;
    protected Report report;
    protected ReportController reportController;
	
	@BeforeClass
    public static void initReport() throws FileNotFoundException
	{
		reportRoot    ="src/main/resources/reports.ahtutils-util/";
		configFile    ="reports.xml";
	    resourcesFile ="resources.xml";
	    
		reports = (Reports)JaxbUtil.loadJAXB(reportRoot +configFile, Reports.class);
		resources = (Resources)JaxbUtil.loadJAXB(reportRoot +resourcesFile, Resources.class);
    }
	
	@Before
	public void initController()
	{
		reportController = new ReportController(reports, resources);
	}
	
	protected void initExample(String id) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		reportId = id;
		report = ReportXpath.getReport(reports, id);
		org.jdom.Document jdomDoc =  JDomUtil.load(report.getExample());
		jdomDoc = JDomUtil.unsetNameSpace(jdomDoc);
		
		docReport = JDomUtil.toW3CDocument(jdomDoc);
	}
	
	protected void assertEmptyPage(byte[] data) throws IOException
	{
		 PdfTextExtractor parser =new PdfTextExtractor(new PdfReader(data));
		 String textOfFirstPage = parser.getTextFromPage(1);
		 Assert.assertTrue("First generated page counts zero characters",textOfFirstPage.length()>0);
	}
}