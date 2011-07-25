package org.reportCompilerPlugin;

import java.io.FileNotFoundException;

import net.sf.ahtutils.report.ReportController;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which compiles a set of JasperReports jrxml files to .jasper file. Creates a rtl language and a ltr language version of all reports.
 *
 * @goal compileReports
 * 
 * @phase process-sources
 */
public class ReportPreCompiler extends AbstractMojo
{
	/**
     * Location of the file.
     * @parameter expression="src/test/resources/config/reports.xml"
     * @required
     */
    private String configFile;
	
    public void execute() throws MojoExecutionException
    {	
        //Create Configuration object from configuration file
		getLog().info("Using " +configFile +" for report configuration.");
		
		Reports reports;
		try {reports = (Reports)JaxbUtil.loadJAXB(configFile, Reports.class);}
		catch (FileNotFoundException e) {throw new MojoExecutionException(e.getMessage());}        
		
		//Get the ReportController from ahtutils
	 
		getLog().warn("ReportController deactivated");
//		ReportController reportController = new ReportController(config);

		getLog().info("Pre-Compiling "+reports.getReport().size()+" Reports");
		for(Report report : reports.getReport())
		{
			getLog().info("Report: "+report.getId());
//			reportController.setParameter("en", rId);
			
		}
    }
}
