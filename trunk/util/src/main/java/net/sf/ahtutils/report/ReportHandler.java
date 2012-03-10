package net.sf.ahtutils.report;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.ahtutils.report.exception.ReportException;
import net.sf.ahtutils.xml.report.Jr;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.ahtutils.xml.report.Resource;
import net.sf.ahtutils.xml.report.Resources;
import net.sf.ahtutils.xml.report.Templates;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class ReportHandler {
	
	final static Logger logger = LoggerFactory.getLogger(ReportHandler.class);
	protected Reports config;
	protected Resources resources;
	protected Templates templates;
	protected String reportRoot;
	protected String configFileLocation;
	protected MultiResourceLoader mrl;
	
	
	/**
	 * This class contains methods to work with the elements configured in reports.xml, resources.xml and templates.xml - Additional functionality needs to be implemented in dedicated classes (e.g. working with specific data XML files, report)
	 * @param reportFile Name of the configuration file
	 * @throws ReportException
	 */
	public ReportHandler(String reportFile) throws ReportException
	{
		
		logger.info("Initializing report handling system");
		mrl = new MultiResourceLoader();
		configFileLocation = reportFile.substring(0, reportFile.lastIndexOf("/")) +"/";
		
		try {
			config = JaxbUtil.loadJAXB(reportFile, Reports.class);
			JaxbUtil.debug(Reports.class, config);
		} catch (FileNotFoundException e) {
			throw new ReportException("Problem loading configuration file: " +e.getMessage());
		}
		
		//Reading reportRoot directory from configuration file and add all directories covered by the conventions of the AHTUtils reporting system
		reportRoot = config.getDir() +"/";
		mrl.addPath(reportRoot);
		mrl.addPath(configFileLocation);
		mrl.addPath("src/main/resource/" +reportRoot);
		mrl.addPath("src/main/" +reportRoot);
		try {
			mrl.searchIs(reportRoot +"reports.xml");
		} catch (FileNotFoundException e1) {
			throw new ReportException("Error: reportRoot " +reportRoot +" does not exist!");
		}
			
		
		logger.info("Read report configuration from " +configFileLocation +" containing " +config.getReport().size() +" reports.");
		
		//Reading resources file from reportRoot if available
		try {
			resources = (Resources)JaxbUtil.loadJAXB(reportRoot +"resources.xml", Resources.class);
			logger.info("Read resources configuration from " +reportRoot +"resources.xml");
		} catch (FileNotFoundException e) {
			logger.warn("Problem loading resources.xml from " +reportRoot +" - no images etc. needed?");
		}
		
		//Reading resources file from reportRoot if available
		try {
			templates = (Templates)JaxbUtil.loadJAXB(reportRoot +"templates.xml", Templates.class);
			logger.info("Read templates definitions from " +reportFile +"templates.xml");
		} catch (FileNotFoundException e) {
			logger.warn("Problem loading templates.xml from " +reportRoot +" - no templates needed?");
		}
	}

	/**
	 * Validating the report resources increases performance hence no compilation logic is executed without the needed files
	 * @throws ReportException
	 */
	public void validateReportResources() throws ReportException
	{
		//Checking for report layout files
		ArrayList<String> missingReports = new ArrayList<String>(); 
		for (Report report : config.getReport())
		{
			for (Media media : report.getMedia())
			{
				for (Jr jasperReport : media.getJr())
				{
					String filename     = jasperReport.getType() +jasperReport.getName() +".jrxml";
					String fileLocation = "jrxml/" +report.getDir() +"/" +media.getType() +"/" +filename;
					try {
						mrl.searchIs(fileLocation);
					} catch (FileNotFoundException e1) {
						missingReports.add(fileLocation);
						logger.warn("File not found!" +e1.getMessage());
					}
				}
			}
		}
		if (!missingReports.isEmpty())
		{
			throw new ReportException("Validation of resources failed - " +missingReports.size() +" reports are missing: " +missingReports.toString());
		}
	}
	
	/**
	 * Get the master report associated with the given report. The JasperDesign object will be loaded from a .jrxml file. 
	 * @throws ReportException
	 */
	public JasperDesign getMasterReport(String id, String format) throws ReportException
	{
		Jr master = (Jr)JXPathContext.newContext(config).getValue("jr[parent::media/@format='" +format +" and @type='mr' + and parent::media/parent::report/@id='"+ id +"']");
		String reportDir = (String)JXPathContext.newContext(config).getValue("report[@id='"+ id +"']/@dir");
		String location = reportDir +"/" +format +"/mr" +master.getName() +".jrxml";
		JasperDesign design;
		try {
			design = (JasperDesign)JRLoader.loadObject(mrl.searchIs(location));
		} catch (FileNotFoundException e) {
			throw new ReportException("Requested report design jrxml file for report " +id +" could not be found at " +location +"!");
		} catch (JRException e) {
			throw new ReportException("Internal JasperReports error when trying to load requested report design jrxml file for report " +id +": " +e.getMessage());
		}
		return design;
	}
	
	/**
	 * Compile a JasperDesign to a JasperReport
	 * @throws ReportException
	 */
	public JasperReport getCompiledReport(JasperDesign jasperDesign) throws ReportException
	{
		JasperReport report;
		try {
			report = JasperCompileManager.compileReport(jasperDesign);
		} catch (JRException e) {
			throw new ReportException("Internal JasperReports error when compiling JasperDesign to JasperReport: " +e.getMessage());
		}
		return report;
	}
	
	/**
	 * Get a Map of all standard parameters plus the given locale and the included data XML file 
	 * @throws ReportException
	 */
	public Map<String,Object> getParameterMap(Document doc, Locale locale)
	{	
		Map<String,Object> mapReportParameter = new Hashtable<String,Object>();
		
		//Set standard parameters
		mapReportParameter.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, doc);
		mapReportParameter.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
		mapReportParameter.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.00");
		mapReportParameter.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
		mapReportParameter.put(JRParameter.REPORT_LOCALE, locale);
		mapReportParameter.put("REPORT_LOCALE", locale);
		
		//Add all resources configured in resources.xml
		for (Resource res : resources.getResource())
		{
			logger.info("Adding resource of type " +res.getType() +" with id='" +res.getName() +"' loaded from " +res.getValue().getValue());
			if (res.getType().equals("image"))
				
				{
					BufferedImage image = null;
					try {
						String imgLocation = reportRoot +"/resources/" +res.getType() +"/" +res.getValue().getValue();
						logger.info("Including image resource: " +imgLocation);
						image = ImageIO.read(mrl.searchIs(imgLocation));} 
					catch (FileNotFoundException e) {logger.error(e.getMessage());}
					catch (IOException e) {logger.error(e.getMessage());}
					mapReportParameter.put(res.getName(), image);
				}
		}
		return mapReportParameter;
	}
	
	/**
	 * Get a Map of all standard parameters plus the given locale and the included data XML file 
	 * @throws ReportException
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getSubreportsMap(String reportId, String format) throws ReportException
	{	
		Map<String,Object> mapReportParameter = new Hashtable<String,Object>();
		
		Report  report  = (Report)JXPathContext.newContext(config).getValue("report[id='" +reportId +"']");
		ArrayList<Jr> reports = (ArrayList<Jr>) JXPathContext.newContext(config).getValue("jr[parent::media/parent::report/id='" +reportId +"' and parent::media/@type='" +format +"' and @type='sr']");
		for (Jr jr : reports)
		{
					String location = report.getDir() +"/" +format +"/sr" +jr.getName() +".jrxml";
					JasperDesign design;
					try {
						design = (JasperDesign)JRLoader.loadObject(mrl.searchIs(location));
					} catch (FileNotFoundException e) {
						throw new ReportException("Requested report design jrxml file for subreport " +jr.getName() +" of report " +reportId +" could not be found at " +location +"!");
					} catch (JRException e) {
						throw new ReportException("Internal JasperReports error when trying to load requested report design jrxml file for subreport " +jr.getName() +" of report " +reportId +": " +e.getMessage());
					}
					JasperReport jreport = getCompiledReport(design);
					mapReportParameter.put("sr" +jr.getName(), jreport);
		}
		return mapReportParameter;
	}
}