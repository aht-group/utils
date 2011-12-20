package net.sf.ahtutils.test.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
		TestReportRenderer test = new TestReportRenderer();
		test.initExample();
		test.initController();
		test.pdfOutput();
    }
}