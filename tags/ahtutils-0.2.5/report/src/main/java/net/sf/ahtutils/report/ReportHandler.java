package net.sf.ahtutils.report;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.ahtutils.report.exception.ReportException;
import net.sf.ahtutils.xml.report.Info;
import net.sf.ahtutils.xml.report.Jr;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.ahtutils.xml.report.Resource;
import net.sf.ahtutils.xml.report.Resources;
import net.sf.ahtutils.xml.report.Templates;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.jxpath.JXPathContext;
import org.jdom2.Namespace;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.OfxChartRenderer;
import org.openfuxml.xml.addon.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * @author helgehemmer
 *
 */
public class ReportHandler {
	
	final static Logger logger = LoggerFactory.getLogger(ReportHandler.class);
	protected Reports reports;
	protected Resources resources;
	protected Templates templates;
	protected String reportRoot;
	protected String configFileLocation;
	protected MultiResourceLoader mrl;	
	
	public enum Format {pdf, xls};
	
	/**
	 * This class contains methods to work with the elements configured in reports.xml,
	 * resources.xml and templates.xml - Additional functionality needs to be implemented
	 * in dedicated classes (e.g. working with specific data XML files, report)
	 * @param reportFile Name of the configuration file
	 * @throws ReportException
	 */
	public ReportHandler(String reportFile) throws ReportException
	{
		
		logger.info("Initializing report handling system");
		mrl = new MultiResourceLoader();
		configFileLocation = reportFile.substring(0, reportFile.lastIndexOf("/")) +"/";
		
		try
		{
			reports = JaxbUtil.loadJAXB(reportFile, Reports.class);
//			JaxbUtil.debug2(reports);
		}
		catch (FileNotFoundException e)
		{
			throw new ReportException("Problem loading configuration file: " +e.getMessage());
		}
		
		//Reading reportRoot directory from configuration file and add all directories covered by the conventions of the AHTUtils reporting system
		reportRoot = reports.getDir() +"/";
		mrl.addPath(reports.getDir());
		mrl.addPath(configFileLocation);
		mrl.addPath("src/main/resource/" +reportRoot);
		mrl.addPath("src/main/" +reportRoot);
		try {
			mrl.searchIs("reports.xml");
		} catch (FileNotFoundException e1) {
			throw new ReportException("Error: reportRoot " +reportRoot +" does not exist!");
		}
			
		
		logger.info("Read report configuration from " +configFileLocation +" containing " +reports.getReport().size() +" reports.");
		
		//Reading resources file from reportRoot if available
		try
		{
			resources = (Resources)JaxbUtil.loadJAXB(reportRoot +"resources.xml", Resources.class);
			logger.info("Read resources configuration from " +reportRoot +"resources.xml");
		}
		catch (FileNotFoundException e)
		{
			resources = new Resources();
			logger.warn("Problem loading resources.xml from " +reportRoot +" - no images etc. needed?");
		}
		
		//Reading resources file from reportRoot if available
		try {
			templates = (Templates)JaxbUtil.loadJAXB(reportRoot +"templates.xml", Templates.class);
			logger.info("Read templates definitions from " +reportRoot +"templates.xml");
		} catch (FileNotFoundException e) {
			logger.warn("Problem loading templates.xml from " +reportRoot +" - no templates needed?");
		}
	}
	
	/**
	 * This class contains methods to work with the elements configured in reports.xml, resources.xml and templates.xml - Additional functionality needs to be implemented in dedicated classes (e.g. working with specific data XML files, report)
	 * @param reports Reports object
	 * @param reports Resources object
	 * @param reports Templates object
	 * @throws ReportException
	 */
	public ReportHandler(Reports reports, Resources resources,Templates templates) throws ReportException
	{
		logger.info("Initializing report handling system with given configuration objects");
		this.reports   = reports;
		this.resources = resources;
		this.templates = templates;
	}

	/**
	 * This class contains methods to work with the elements configured in reports.xml, resources.xml and templates.xml - Additional functionality needs to be implemented in dedicated classes (e.g. working with specific data XML files, report)
	 * @param reportFile Name of the configuration file
	 * @throws ReportException
	 */
	public ReportHandler(Reports reports) throws ReportException
	{
		logger.info("Initializing report handling system with given reports object");
		mrl = new MultiResourceLoader();
		
		this.reports = reports;
		
		//Reading reportRoot directory from configuration file and add all directories covered by the conventions of the AHTUtils reporting system
		reportRoot = reports.getDir() +"/";
		mrl.addPath(reports.getDir());
		mrl.addPath("src/main/resource/" +reportRoot);
		mrl.addPath("src/main/" +reportRoot);
		
		//Reading resources file from reportRoot if available
		resources = new Resources();
		try
		{
			resources = JaxbUtil.loadJAXB(mrl.searchIs(reports.getResources()), Resources.class);
			logger.info("Read resources configuration from " +reports.getResources());
		}
		catch (FileNotFoundException e)
		{
			logger.warn("Problem loading resources.xml from " +reports.getResources() +" - no images etc. needed?");
		}
		
		//Reading resources file from reportRoot if available
		try
		{
			templates = (Templates)JaxbUtil.loadJAXB(mrl.searchIs(reports.getTemplates()), Templates.class);
			logger.info("Read templates definitions from " +reports.getTemplates());
		}
		catch (FileNotFoundException e)
		{
			logger.warn("Problem loading templates.xml from " +reports.getTemplates() +" - no templates needed?");
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
		for (Report report : reports.getReport())
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
		Jr master = null;
		try
		{
			master = ReportXpath.getMr(reports, id, format);
		}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		
		String reportDir = (String)JXPathContext.newContext(reports).getValue("report[@id='"+ id +"']/@dir");
		String location = "jrxml/" +reportDir +"/" +format +"/mr" +master.getName() +".jrxml";
		JasperDesign design = null;
		try
		{
			design = (JasperDesign)JRXmlLoader.load(mrl.searchIs(location));
		} catch (FileNotFoundException e) {
			throw new ReportException("Requested report design jrxml file for report " +id +" could not be found at " +location +"!");
		} catch (JRException e) {
			throw new ReportException("Internal JasperReports error when trying to load requested report design jrxml file for report " +id +": " +e.getMessage());
		}
		logger.info("lodaded report");
		return design;
	}
	
	/**
	 * Compile a JasperDesign to a JasperReport
	 * @throws ReportException
	 */
	@Deprecated
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
	 * Compile a JasperDesign to a JasperReport
	 * @throws ReportException
	 */
	public JasperReport getCompiledReport(String reportId, String format) throws ReportException
	{
		Jr master = null;
		try {
			master = ReportXpath.getMr(reports, reportId, format);
		} catch (ExlpXpathNotFoundException e1) {
			e1.printStackTrace();
		} catch (ExlpXpathNotUniqueException e1) {
			e1.printStackTrace();
		}
		String reportDir = (String)JXPathContext.newContext(reports).getValue("report[@id='"+ reportId +"']/@dir");
		String location = "jasper/" +reportDir +"/" +format +"/ltr/mr" +master.getName() +".jasper";
		JasperReport report = null;
		try
		{
			report = (JasperReport)JRLoader.loadObject(mrl.searchIs(location));
		} catch (FileNotFoundException e) {
			logger.warn("Requested compiled report jasper file for report " +reportId +" could not be found at " +location +"! - Trying to recompile it from jrxml.");
			JasperDesign design = getMasterReport(reportId, format);
			report = (JasperReport) getCompiledReport(design);
		} catch (JRException e) {
			throw new ReportException("Internal JasperReports error when trying to get requested compiled report design for report " +reportId +": " +e.getMessage());
		}
		return report;
	}
	
	/**
	 * Get a Map of all standard parameters plus the given locale and the included data XML file 
	 * @throws ReportException
	 */
	@Deprecated
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
						String imgLocation = "/resources/" +res.getType() +"/" +res.getValue().getValue();
						logger.info("Including image resource: " +imgLocation);
						image = ImageIO.read(mrl.searchIs(imgLocation));} 
					catch (FileNotFoundException e) {logger.error(e.getMessage());}
					catch (IOException e) {logger.error(e.getMessage());}
					mapReportParameter.put(res.getName(), image);
				}
		}
		for (Object key : mapReportParameter.keySet())
		{
			String keyString = (String) key;
			String valueString = mapReportParameter.get(key).toString();
			logger.info("Report Parameter: " +keyString +" = " +valueString);
		}
		return mapReportParameter;
	}
	
	/**
	 * Get a Map of all standard parameters plus the given locale and the included data XML file 
	 * @throws ReportException
	 */
	public Map<String,Object> getParameterMapJDom(org.jdom2.Document doc, Locale locale)
	{	
		Map<String,Object> mapReportParameter = new Hashtable<String,Object>();
		
		//Set standard parameters
		mapReportParameter.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
		mapReportParameter.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.00");
		mapReportParameter.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
		mapReportParameter.put(JRParameter.REPORT_LOCALE, locale);
		mapReportParameter.put("REPORT_LOCALE", locale);
		
		//Add charts if included in report info block
		
		//Get the root element (report)
		org.jdom2.Element reportElement = doc.getRootElement();
		
		//Get the info element as child of report element
		org.jdom2.Element infoElement   = reportElement.getChild("info", Namespace.getNamespace("http://ahtutils.aht-group.com/report"));
		if (infoElement!=null)
		{
			Info info = JDomUtil.toJaxb(infoElement, Info.class);
			
			OfxChartRenderer ofxRenderer = new OfxChartRenderer();
			for (Media media : info.getMedia())
			{
				Chart chart          = media.getChart();
				JFreeChart jfreeChart = ofxRenderer.render(chart);
				BufferedImage chartImage = jfreeChart.createBufferedImage(320, 240);
				mapReportParameter.put(media.getCode(), chartImage);
			}
		}
	
		//Add the data document
		doc = JDomUtil.unsetNameSpace(doc);
		Document docReport = JDomUtil.toW3CDocument(doc);
		mapReportParameter.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, docReport);
		
		//Add all resources configured in resources.xml
		for (Resource res : resources.getResource())
		{
			logger.info("Adding resource of type " +res.getType() +" with id='" +res.getName() +"' loaded from " +res.getValue().getValue());
			if (res.getType().equals("image"))
				
				{
					BufferedImage image = null;
					try {
						String imgLocation = "/resources/" +res.getType() +"/" +res.getValue().getValue();
						logger.info("Including image resource: " +imgLocation);
						image = ImageIO.read(mrl.searchIs(imgLocation));} 
					catch (FileNotFoundException e) {logger.error(e.getMessage());}
					catch (IOException e) {logger.error(e.getMessage());}
					mapReportParameter.put(res.getName(), image);
				}
		}
		for (Object key : mapReportParameter.keySet())
		{
			String keyString = (String) key;
			String valueString = mapReportParameter.get(key).toString();
			logger.info("Report Parameter: " +keyString +" = " +valueString);
		}
		return mapReportParameter;
	}
	
	/**
	 * Get a Map of all standard parameters plus the given locale and the included data XML file 
	 * @throws ReportException
	 */
	public Map<String,Object> getSubreportsMap(String reportId, String format) throws ReportException
	{	
		Map<String,Object> mapReportParameter = new Hashtable<String,Object>();
		
		Report  report  = (Report)JXPathContext.newContext(reports).getValue("report[id='" +reportId +"']");
		ArrayList<Jr> results = ReportXpath.getSubreports(reports, reportId, format);
		for (Jr jr : results)
		{
					String location = "jrxml/" +report.getDir() +"/" +format +"/sr" +jr.getName() +".jrxml";
					JasperDesign design;
					try {
						design = (JasperDesign)JRXmlLoader.load(mrl.searchIs(location));
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
	
	/**
	 * Get the JasperPrint object for a given JasperReport along with parameter map
	 * @param report
	 * @param mapReportParameter
	 * @return
	 * @throws ReportException
	 */
	public JasperPrint getJasperPrint(JasperReport report, Map<String,Object> mapReportParameter) throws ReportException
	{
		JasperPrint  print = new JasperPrint();
		try {print  = JasperFillManager.fillReport(report, mapReportParameter);}
		catch (JRException e)
		{
			throw new ReportException("Error when trying to create JasperPrint object from compiled JasperReport with given parameter map: " +e.getMessage());
		}
		return print;
	}
	
	/**
	 * Exports the given JasperPrint to an MS Excel xls sheet as ByteArrayOutputStream
	 * @param JasperPrint to be exported
	 * @return
	 * @throws ReportException
	 */
	public ByteArrayOutputStream exportToXls(JasperPrint jPrint) throws ReportException
	{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try
		{	
			logger.info("Exporting report to Excel Sheet");
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, os);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
		//	NOT WORKING IN JASPERREPORTS 5.0.1:
		//	exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.exportReport();	
		}
		catch (JRException e) 
		{
			throw new ReportException("Error exporting JasperPrint to XLS ByteArrayOutputStream: " +e.getMessage());
		}
		return os;
	}
	
	/**
	 * Exports the given JasperPrint to an PDF format as ByteArrayOutputStream
	 * @param JasperPrint to be exported
	 * @return
	 * @throws ReportException
	 */
	public ByteArrayOutputStream exportToPdf(JasperPrint jPrint) throws ReportException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try
		{	
			logger.info("Exporting report to PDF");
			JasperExportManager.exportReportToPdfStream(jPrint, baos);
		}
		catch (JRException e)
		{
			throw new ReportException("Error exporting JasperPrint to PDF ByteArrayOutputStream: " +e.getMessage());
		}
		return baos;
	}	
	
	/**
	 * Method encapsulating the classical JasperReports workflow of JasperDesign -> JasperReport -> JasperPrint -> PDF/XLS export
	 * @param reportId identifier of the requested report
	 * @param doc XML data object
	 * @param format output format defined by Format enum (PDF or Excel XLS)
	 * @param locale Locale to be used for report exporting
	 * @return
	 * @throws ReportException
	 */
	@Deprecated
	public ByteArrayOutputStream create(String reportId, Document doc, Format format, Locale locale) throws ReportException
	{
		logger.info("TEST");
	//	JasperDesign masterDesign = getMasterReport(reportId, format.name());
	//	JasperReport masterReport = getCompiledReport(masterDesign);
		JasperReport masterReport = getCompiledReport(reportId, format.name());
		Map<String, Object> reportParameterMap = getParameterMap(doc, locale);
		reportParameterMap.putAll(getSubreportsMap(reportId, format.name()));
		JasperPrint print = getJasperPrint(masterReport, reportParameterMap);
		return exportToPdf(print);
	}
	
	/**
	 * Method encapsulating the classical JasperReports workflow of JasperDesign -> JasperReport -> JasperPrint -> PDF/XLS export
	 * @param reportId identifier of the requested report
	 * @param doc XML data object
	 * @param format output format defined by Format enum (PDF or Excel XLS)
	 * @param locale Locale to be used for report exporting
	 * @return
	 * @throws ReportException
	 */
	public ByteArrayOutputStream createUsingJDom(String reportId, org.jdom2.Document doc, Format format, Locale locale) throws ReportException
	{
		logger.info("Using JDom data document.");
	//	JasperDesign masterDesign = getMasterReport(reportId, format.name());
	//	JasperReport masterReport = getCompiledReport(masterDesign);
		JasperReport masterReport = getCompiledReport(reportId, format.name());
		Map<String, Object> reportParameterMap = getParameterMapJDom(doc, locale);
		reportParameterMap.putAll(getSubreportsMap(reportId, format.name()));
		JasperPrint print = getJasperPrint(masterReport, reportParameterMap);
		return exportToPdf(print);
	}
	
	public Reports getReports()
	{
		return reports;
	}
}