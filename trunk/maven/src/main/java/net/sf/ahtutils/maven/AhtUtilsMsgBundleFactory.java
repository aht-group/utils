package net.sf.ahtutils.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which compiles a set of JasperReports jrxml files to .jasper file. Creates a rtl language and a ltr language version of all reports.
 *
 * @goal mgbl
 * 
 * @phase process-sources
 */
public class AhtUtilsMsgBundleFactory extends AbstractMojo
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
	
    public void execute() throws MojoExecutionException
    {
    	getLog().info("Using data from " +reportRoot +" for report processing.");
    	
    }
}