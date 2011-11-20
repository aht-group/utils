package net.sf.ahtutils.maven;

import java.util.ArrayList;

import net.sf.ahtutils.report.ReportCompiler;

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
     * @parameter expression="reports.xml"
     * @required
     */
    private String configFile;
    
    /**
     * Location of the file.
     * @parameter expression="src/main/resources/reports.${project.artifactId}"
     * @required
     */
    private String reportRoot;
    
    /**
     * Location of the file.
     * @parameter expression="${project.build.directory}/reports.${project.artifactId}"
     * @required
     */
    private String targetDir;
	
    public void execute() throws MojoExecutionException
    {
    	getLog().info("Using data from " +reportRoot +" for report processing.");
    	getLog().info("Using target: " +targetDir);
    	ArrayList<String> log = ReportCompiler.execute(configFile, reportRoot, targetDir);
    	for (String str : log)
    	{
    		getLog().info(str);
    	}
    }
}