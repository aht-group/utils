package org.reportCompilerPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.ahtutils.report.ReportUtil;
import net.sf.ahtutils.xml.report.Jr;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.jdom.JDOMException;

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
     * @parameter expression="reports.xml"
     * @required
     */
    private String configFile;
    
    /**
     * Location of the file.
     * @parameter expression="src/main/resources/reports"
     * @required
     */
    private String reportRoot;
	
    public void execute() throws MojoExecutionException
    {	
        getLog().info("Using " +configFile +" for report configuration.");
		
		Reports reports;
		try {reports = (Reports)JaxbUtil.loadJAXB(reportRoot +"/" +configFile, Reports.class);}
		catch (FileNotFoundException e) {throw new MojoExecutionException(e.getMessage());}
		
		//Compiling reports
		getLog().info("Pre-Compiling "+reports.getReport().size()+" Report(s)");
		for(Report report : reports.getReport())
		{
			getLog().info("Report: "+report.getId() +" (" +report.getMedia().size() + " media outputs)");
			for (Media media : report.getMedia())
			{
				getLog().info("Compiling " +media.getJr().size() +" reports for output -" +media.getType() +"-");
				for (Jr jr : media.getJr())
				{
					getLog().info(jr.getName());
					//Compiling for left to right and right to left languages
					
					//Compiling ltr version
					String jrxml  = reportRoot +"/" +"jrxml"  +"/" +report.getDir() +"/" + media.getType() + "/" + jr.getType() + jr.getName() +".jrxml";
					String jasperLtr = reportRoot +"/" +"jasper" +"/" +report.getDir() +"/" + media.getType() + "/ltr/" + jr.getType() + jr.getName() +".jasper";
					getLog().info("Compiling " +jrxml +" to " +jasperLtr);
					try {
						new File(reportRoot +"/" +"jasper"  +"/" +report.getDir() +"/" + media.getType() + "/ltr/").mkdirs();
						JasperCompileManager.compileReportToFile(jrxml, jasperLtr);
					} catch (JRException e) {
						getLog().error(e.getMessage());
					}
					
					//Compiling rtl version
					String jasperRtl = reportRoot +"/" +"jasper" +"/" +report.getDir() +"/" + media.getType() + "/rtl/" + jr.getType() + jr.getName() +".jasper";
					getLog().info("Compiling " +jrxml +" to " +jasperRtl);
					new File(reportRoot +"/" +"jasper"  +"/" +report.getDir() +"/" + media.getType() + "/rtl/").mkdirs();
					InputStream in = null;
					try {
						in = ReportUtil.LeftToRightConversion(jrxml);
					} catch (JDOMException e) {
						getLog().error("Problem converting to right-to-left language.");
					}
					try {
						JasperCompileManager.compileReportToStream(in, new FileOutputStream(jasperRtl));
					} catch (FileNotFoundException e) {
						getLog().error(e.getMessage());
					} catch (JRException e) {
						getLog().error(e.getMessage());
					}
				}
			}
		}
		
    }
}
