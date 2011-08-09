package org.reportCompilerPlugin;

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
     * @parameter expression="src/main/resources/reports"
     * @required
     */
    private String reportRoot;
	
    public void execute() throws MojoExecutionException
    {	
    	ReportCompiler.execute(configFile, reportRoot);
    }
}
