package org.reportCompilerPlugin;

import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.Jr;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

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
     * @parameter expression="src/main/resources/reports/reports.xml"
     * @required
     */
    private String configFile;
	
    public void execute() throws MojoExecutionException
    {	
        getLog().info("Using " +configFile +" for report configuration.");
		
		Reports reports;
		try {reports = (Reports)JaxbUtil.loadJAXB(configFile, Reports.class);}
		catch (FileNotFoundException e) {throw new MojoExecutionException(e.getMessage());}

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
					String jrxml  = media.getDir() + "/" + jr.getType() + jr.getName() +".jrxml";
					String jasper = media.getDir() + "/" + jr.getType() + jr.getName() +".jasper";
					getLog().info("Compiling " +jrxml +" to " +jasper);
					try {
						JasperCompileManager.compileReportToFile(jrxml, jasper);
					} catch (JRException e) {
						getLog().error(e.getMessage());
					}
				}
			}
		}
    }
}
