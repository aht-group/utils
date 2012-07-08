package net.sf.ahtutils.test.report;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.ahtutils.report.ReportUtilCreator;
import net.sf.ahtutils.test.AbstractAhtUtilsReportTest;
import net.sf.jasperreports.engine.JRException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;


public class TestReportCreator extends AbstractAhtUtilsReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestReportCreator.class);
    
	@Test
	public void createReport() throws JRException, TemplateException, IOException
	{
		ReportUtilCreator creator = new ReportUtilCreator();
		creator.setConfigFile(reportFileLocation +"/");
		creator.setResourcesFile("src/main/resources/reports.ahtutils-report/resources.xml");
		creator.setJrxmlDir("src/main/resources/reports.ahtutils-report/jrxml");
		creator.setReportId("demo");
		creator.setTestPackage("net.sf.ahtutils.test.report");
		creator.execute();
	}
}