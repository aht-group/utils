package net.sf.ahtutils.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import net.sf.ahtutils.xml.report.Jr;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ReportUtilCreator
{
	
	private Logger logger = LoggerFactory.getLogger(ReportUtilCreator.class);
	
	private String configFile;
	private String jrxmlDir;
    private String resourcesFile;
    private String reportId;
    private String testPackage;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute() throws JRException, TemplateException, IOException
    {	
		logger.info("Using " +configFile +" for report configuration.");
        Reports reports;
		reports = (Reports)JaxbUtil.loadJAXB(configFile, Reports.class);
		
		ReportUtilTemplate templateManager = null;
		templateManager = new ReportUtilTemplate(reports.getDir());
		
		
        logger.info("Adding " +reportId +" report to system.");
        
		Report report = new Report();
		report.setId(reportId);
		report.setLtr(true);
		report.setDir(reportId);
		report.setExample("src/test/resources/data/xml/report/" +reportId +".xml");
		
		Media pdf = new Media();
		pdf.setType("pdf");
		Jr jasperReport = new Jr();
		jasperReport.setName(reportId);
		jasperReport.setType("mr");
		
		pdf.getJr().add(jasperReport);
		report.getMedia().add(pdf);
		
		reports.getReport().add(report);
		JaxbUtil.save(new File(configFile), reports, true);
		logger.info("Reports.xml updated.");
		
		String reportFilename = reports.getDir() +"/" +reportId +"/" +"mr" +reportId +".jrxml";
		logger.info("Creating basic "+reportFilename +".");
		JasperDesign design = templateManager.create();
		//JRXmlWriter cares about writing the in-memory design to an OutputStream
		Boolean dirCreated = new File(jrxmlDir +"/" +reportId +"/pdf/").mkdirs();
		logger.info("Creation of new jrxml directory reports: " +dirCreated);
		JRXmlWriter.writeReport(design, jrxmlDir +"/" +reportId +"/pdf/mr" +reportId +".jrxml", "UTF-8");
		
		logger.info("Creating JUnit test.");
		Map datamodel = new HashMap();
		datamodel.put("reportId", reportId);
		datamodel.put("testPackage", testPackage);
		datamodel.put("classname", "Test" +reportId +"Renderer");
		Configuration cfg = new Configuration();
		Template tpl = cfg.getTemplate("src/main/resources/freemarker/report-test.java");
		String testFileName = "src/test/java/" +testPackage.replaceAll("\\.", "/") +"/" +"Test" +reportId +"Renderer.java";
		logger.info("Writing JUnit test to " +testFileName);
		File testFile = new File(testFileName);
		OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(testFile));
		tpl.process(datamodel, output);
		
		logger.info("Creating demo XML.");
		Element demo = new Element("demo");
		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		xmlOutput.output(demo, new FileWriter(report.getExample()));
        
    }
	
   public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public String getResourcesFile() {
		return resourcesFile;
	}

	public void setResourcesFile(String resourcesFile) {
		this.resourcesFile = resourcesFile;
	}

	public String getJrxmlDir() {
		return jrxmlDir;
	}

	public void setJrxmlDir(String jrxmlDir) {
		this.jrxmlDir = jrxmlDir;
	}

	public String getTestPackage() {
		return testPackage;
	}

	public void setTestPackage(String testPackage) {
		this.testPackage = testPackage;
	}

}
