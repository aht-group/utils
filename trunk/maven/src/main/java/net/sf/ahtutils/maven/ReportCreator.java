package net.sf.ahtutils.maven;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.report.ReportUtilTemplate;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Add a report to the report system.
 *
 * @goal addReport
 * 
 * @phase process-sources
 */
public class ReportCreator extends AbstractMojo
{
	/**
     * Location of the file.
     * @parameter expression="src/main/resources/reports.${project.artifactId}/reports.xml"
     * @required
     */
    private String configFile;
    
    /**
     * Location of the file.
     * @parameter expression="src/main/resources/reports/resources.xml"
     * @required
     */
    private String resourcesFile;
    
    /**
     * Id of the report
     * @parameter expression="dummy"
     * @required
     */
    private String reportId;
    
    
    /**
     * Id of the report
     * @parameter expression="${project.artifactId}"
     * @required
     */
    private String artifactId;
	
	public void execute() throws MojoExecutionException
    {	
		Reports reports;
		try {
			reports = (Reports)JaxbUtil.loadJAXB(configFile, Reports.class);
			getLog().info("Using " +configFile +" for report configuration.");
			}
		catch (FileNotFoundException e) 
			{
			getLog().warn("Report File Not Found at " +configFile);
			getLog().info("Creating new configuration at " +configFile +".");
			reports = new Reports();
			reports.setDir(configFile.substring(0, configFile.lastIndexOf("/")));
			}
		getLog().info("Called in artifact " +artifactId +".");
		getLog().info("Current directory: " +System.getProperty("user.dir"));
		ReportUtilTemplate templateManager = null;
		try {
			templateManager = new ReportUtilTemplate(reports.getDir());
		} catch (FileNotFoundException e) {
			getLog().error("Could not initialize template manager: " +e.getMessage());
		}
		
        getLog().info("Adding " +reportId +" report to system.");
        
		Report report = new Report();
		report.setId(reportId);
		report.setLtr(true);
		report.setDir(reportId);
		report.setExample("src/main/test/resources/data/xml/report/" +reportId +".xml");
		reports.getReport().add(report);
		JaxbUtil.save(new File(configFile), reports, true);
		getLog().info("Reports.xml updated.");
		
		String reportFilename = reports.getDir() +"/" +reportId +"/" +"mr" +reportId +".jrxml";
		getLog().info("Creating basic "+reportFilename +".");
		JasperDesign design = templateManager.create();
		//JRXmlWriter cares about writing the in-memory design to an OutputStream
		try {
			JRXmlWriter.writeReport(design, System.out, "UTF-8");
		} catch (JRException e) {
			getLog().error("Could not write basic demo report: " +e.getMessage());
		}
        
    }
	
   public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

}
