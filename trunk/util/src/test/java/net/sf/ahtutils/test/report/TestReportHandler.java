package net.sf.ahtutils.test.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import net.sf.ahtutils.report.ReportHandler;
import net.sf.ahtutils.report.exception.ReportException;
import net.sf.ahtutils.test.AbstractAhtUtilReportTest;
import net.sf.ahtutils.test.AhtUtilTstBootstrap;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestReportHandler extends AbstractAhtUtilReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestReportHandler.class);
			
	@Test
	public void test() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException, ReportException, IOException
    {
		AhtUtilTstBootstrap.init();
		ReportHandler reportHandler = new ReportHandler("src/main/resources/reports.ahtutils-util/reports.xml");
		TestReportHandler.initReport();
		
		TestReportHandler test = new TestReportHandler();
		test.initExample("testReport");
		
		//This example shows how to do the standard jrxml->jasper->map filling->exporting workflow using the ReportHandler system
		
		//1. load the JasperDesign from the jrxml associated with the reportId in the reports.xml
		logger.info("Trying to read " +reportId);
		JasperDesign masterDesign = reportHandler.getMasterReport(reportId, "pdf");
		logger.info("Got jrxml with report title " +masterDesign.getName());
		
		//2. compile the JasperDesign to a JasperReport
		logger.info("Trying to compile " +reportId);
		JasperReport masterReport = reportHandler.getCompiledReport(masterDesign);
		logger.info("Got jasper object with report title " +masterReport.getName());
		
		//3. a) fill the reports parameter map including the XML data file
		Map<String, Object> reportParameterMap = reportHandler.getParameterMap(test.docReport, Locale.GERMAN);
		
		//3. b) get all compiled subreports as referenced in reports.xml as elements of a map to be added to the parameters map
		reportParameterMap.putAll(reportHandler.getSubreportsMap(reportId, "pdf"));
		
		//4. Create the JasperPrint
		JasperPrint print = reportHandler.getJasperPrint(masterReport, reportParameterMap);
		
		//5. Export the report to PDF and XLS
		ByteArrayOutputStream pdf = reportHandler.exportToPdf(print);
		ByteArrayOutputStream xls = reportHandler.exportToXls(print);
    }
}