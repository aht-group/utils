package net.sf.ahtutils.test.report;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import net.sf.ahtutils.report.ReportHandler;
import net.sf.ahtutils.report.AbstractReportControl.Direction;
import net.sf.ahtutils.report.AbstractReportControl.Output;
import net.sf.ahtutils.report.exception.ReportException;
import net.sf.ahtutils.test.AbstractAhtUtilReportTest;
import net.sf.ahtutils.test.AhtUtilTstBootstrap;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestReportRenderer extends AbstractAhtUtilReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestReportRenderer.class);
    
	@Before
	public void initExample() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		initExample("testReport");
	}
	
	@Test
	public void emptyPages() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException, ReportException, IOException
	{
		reportController.createJasperPrint(report.getId(), Output.pdf, Direction.ltr, docReport);
		ByteArrayOutputStream os = (ByteArrayOutputStream)reportController.exportToPdf();
		assertEmptyPage(os.toByteArray());	    
	}
	
	@Test
	public void pdfOutput() throws ReportException
	{
	    reportController.createJasperPrint(report.getId(), Output.pdf, Direction.ltr, docReport);
	    reportController.setExportParameter("target/" ,report.getId());
	    reportController.exportPdf();
	}
			
	public static void main(String[] args) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException, ReportException, IOException
    {
		AhtUtilTstBootstrap.init();
		
		TestReportRenderer.initReport();
		ReportHandler reportHandler = new ReportHandler("src/main/resources/reports.ahtutils-util/reports.xml");
		
		TestReportRenderer test = new TestReportRenderer();
		test.initExample("testReport");
		
		ByteArrayOutputStream pdf = reportHandler.createUsingJDom(reportId, test.jdomReport, ReportHandler.Format.pdf, Locale.GERMAN);
		String pdfFile = "target/" +reportId +".pdf";
		OutputStream outputStream = new FileOutputStream (pdfFile);
		pdf.writeTo(outputStream);
		logger.info("Writing PDF stream to file: "+pdfFile);
    }
}