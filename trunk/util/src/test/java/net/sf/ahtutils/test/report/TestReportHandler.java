package net.sf.ahtutils.test.report;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import net.sf.ahtutils.report.ReportHandler;
import net.sf.ahtutils.report.exception.ReportException;
import net.sf.ahtutils.test.AbstractAhtUtilReportTest;
import net.sf.ahtutils.test.AhtUtilTstBootstrap;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestReportHandler extends AbstractAhtUtilReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestReportHandler.class);
			
	public static void main(String[] args) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException, ReportException, IOException
    {
		AhtUtilTstBootstrap.init();
		ReportHandler reportHandler = new ReportHandler("src/main/resources/reports.ahtutils-util/reports.xml");
		TestReportHandler.initReport();
		
		TestReportHandler test = new TestReportHandler();
		test.initExample("testReport");
		
		//This example shows how to do the standard jrxml->jasper->map filling->exporting workflow using the ReportHandler system
		
		//1. load the JasperDesign from the jrxml associated with the reportId in the reports.xml
		JasperDesign masterDesign = reportHandler.getMasterReport(reportId, "pdf");
		
		//2. compile the JasperDesign to a JasperReport
		JasperReport masterReport = reportHandler.getCompiledReport(masterDesign);
		
		//3. a) fill the reports parameter map including the XML data file
		Map<String, Object> reportParameterMap = reportHandler.getParameterMap(test.docReport, Locale.GERMAN);
		
		//3. b) get all compiled subreports as referenced in reports.xml as elements of a map to be added to the parameters map
		reportParameterMap.putAll(reportHandler.getSubreportsMap(reportId, "pdf"));
		
		//4. Create the JasperPrint
		
		//5. Export the report to PDF and XLS
    }
}