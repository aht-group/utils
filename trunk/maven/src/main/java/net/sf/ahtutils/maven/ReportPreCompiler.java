package net.sf.ahtutils.maven;

import java.text.DecimalFormat;

import net.sf.ahtutils.report.ReportCompiler;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.joda.time.DateTime;
import org.joda.time.Period;

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
     * @parameter expression="${project.basedir}/src/main/resources/reports.${project.artifactId}/reports.xml"
     * @required
     */
    private String configFile;
    
    /**
     * Location of the file.
     * @parameter expression="${project.basedir}/src/main/reports"
     * @required
     */
    private String source;
    
    /**
     * Location of the file.
     * @parameter expression="${project.build.directory}/classes/reports.${project.artifactId}"
     * @required
     */
    private String target;
    
    /**
     * Location of the file.
     * @parameter expression="WARN"
     * @required
     */
    private String log;
	
    public void execute() throws MojoExecutionException
    {
    	BasicConfigurator.configure();
    	org.apache.log4j.Logger.getRootLogger().setLevel(Level.toLevel(log));
    	
    	getLog().info("Using configFile " +configFile);
    	getLog().info("Using jrxml from " +source);
    	getLog().info("Compiling jasper to " +target);
    	
    	DateTime start = new DateTime();
    	int[] counter = ReportCompiler.execute(configFile, source, target);
    	Period duration = new Period(start,new DateTime());
    	
    	DecimalFormat df = new DecimalFormat("#00");
    	df.setDecimalSeparatorAlwaysShown(false);
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("Compiled ").append(counter[0]).append(" reports");
    	sb.append(" (media:"+counter[1]+" jr:"+counter[2]+")");
    	sb.append(" in ").append(df.format(duration.getMinutes())).append(":").append(df.format(duration.getSeconds())).append(" minutes");
    	    	
    	getLog().info(sb.toString());
    }
}