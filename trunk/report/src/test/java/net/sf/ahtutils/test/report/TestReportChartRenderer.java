package net.sf.ahtutils.test.report;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import net.sf.ahtutils.report.ReportHandler;
import net.sf.ahtutils.report.exception.ReportException;
import net.sf.ahtutils.test.AbstractAhtUtilsReportTst;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestReportChartRenderer extends AbstractAhtUtilsReportTst
{
	final static Logger logger = LoggerFactory.getLogger(TestReportChartRenderer.class);
    
	@Before
	public void initExample() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		initExample("testReportChart");
	}
	
	@Test
	public void emptyPages() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException, ReportException, IOException
	{
		ReportHandler reportHandler = new ReportHandler("src/main/resources/reports.ahtutils-report/reports.xml");
		ByteArrayOutputStream pdf = reportHandler.createUsingJDom(reportId, this.jdomReport, ReportHandler.Format.pdf, Locale.GERMAN);
		String pdfFile = "target/" +reportId +".pdf";
		OutputStream outputStream = new FileOutputStream (pdfFile);
		pdf.writeTo(outputStream);
		logger.info("Writing PDF stream to file: "+pdfFile);
		assertEmptyPage(pdf.toByteArray());	    
	}
			
	public static void main(String[] args) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException, ReportException, IOException
    {
		initLogger();
		
		TestReportRenderer.initReport();
		ReportHandler reportHandler = new ReportHandler("src/main/resources/reports.ahtutils-report/reports.xml");
		
		TestReportChartRenderer test = new TestReportChartRenderer();
		test.initExample("testReportChart");
		

		ByteArrayOutputStream pdf = reportHandler.createUsingJDom(reportId, test.jdomReport, ReportHandler.Format.pdf, Locale.GERMAN);
		String pdfFile = "target/" +reportId +".pdf";
		OutputStream outputStream = new FileOutputStream (pdfFile);
		pdf.writeTo(outputStream);
		logger.info("Writing PDF stream to file: "+pdfFile);
    }
}