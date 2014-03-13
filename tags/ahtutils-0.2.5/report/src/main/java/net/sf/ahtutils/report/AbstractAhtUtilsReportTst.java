package net.sf.ahtutils.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import net.sf.ahtutils.report.exception.ReportException;
import net.sf.ahtutils.xml.report.Info;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.ahtutils.xml.report.Resources;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

public class AbstractAhtUtilsReportTst
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilsReportTst.class);
	
	protected static NsPrefixMapperInterface nsPrefixMapper;
	protected File f;
	protected boolean saveReference=false;

	protected static File fTarget;
	
	protected static void setfTarget(File myTarget) {fTarget=myTarget;}

	protected static String reportRoot;
	protected static String configFile;
    protected static String resourcesFile;
    protected static String reportId;
    
    protected static Reports reports;
    protected static Resources resources;
    
    protected Document docReport;
    protected org.jdom2.Document jdomReport;
    protected Report report;
    protected ReportHandler reportHandler;
    protected ByteArrayOutputStream pdf;
    protected static String reportFileLocation;
    protected static String loggerConfigFile;
    protected static String loggerConfigPath;
	
	public static void initFile()
	{
		if(!LoggerInit.isLog4jInited()){initLogger();}
		String dirTarget = System.getProperty("targetDir");
		if(dirTarget==null){dirTarget="target";}
		setfTarget(new File(dirTarget));
		logger.debug("Using targeDir "+fTarget.getAbsolutePath());
	}
	
	public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit(loggerConfigFile);
			loggerInit.addAltPath(loggerConfigPath);
			loggerInit.init();
		}
    }
	
	@Before
	public void initHandler() throws ReportException, FileNotFoundException
	{
		reportHandler = new ReportHandler(reportFileLocation);
		reports       = reportHandler.getReports();
	}
	
	protected void initExample(String id) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		reportId = id;
		report = ReportXpath.getReport(reports, id);
		
		//Load the JDom representation of the example for further processing in ReportHandler
		jdomReport = JDomUtil.load(report.getExample());
		
		//		logger.info("Reading XML demo data from:" +report.getExample());
		//		JDomUtil.debug(jdomDoc);
		docReport = JDomUtil.toW3CDocument(JDomUtil.unsetNameSpace(jdomReport));
	}
	
	protected void createPdf() throws ReportException
	{
		pdf = reportHandler.createUsingJDom(reportId, this.jdomReport, ReportHandler.Format.pdf, Locale.GERMAN);
	}
	
	protected void writePdf() throws IOException
	{
		Info info = ReportXpath.getReportInfo(jdomReport);
		
		StringBuffer sb = new StringBuffer();
		sb.append("target/");
		if(info!=null && info.isSetFile() && info.getFile().getValue().length()>0)
		{
			sb.append(info.getFile().getValue());
		}
		else
		{
			sb.append(reportId);
		}
		sb.append(".pdf");
		
		OutputStream outputStream = new FileOutputStream (sb.toString());
		pdf.writeTo(outputStream);
	}
	
	protected void assertEmptyPage(byte[] data) throws IOException
	{
		 PdfTextExtractor parser =new PdfTextExtractor(new PdfReader(data));
		 String textOfFirstPage = parser.getTextFromPage(1);
		 Assert.assertTrue("First generated page counts zero characters",textOfFirstPage.length()>0);
	}
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("actual XML differes from expected XML",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
}