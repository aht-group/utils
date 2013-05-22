package net.sf.ahtutils.maven;

import java.io.File;
import java.io.IOException;

import net.sf.ahtutils.controller.factory.html.CssGridBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal create Anootations for ViewIdentifier
 *
 * @goal createCssGrid
 */
public class CssGridFactory extends AbstractMojo
{
    /**
     * Logger Level
     * @parameter expression="WARN"
     * @required
     */
    private String log;
    
    /**
     * Location of the file.
     * @parameter expression="${project.build.directory}/security.${project.artifactId}"
     * @required
     */
    private String targetDir;
	
	/**
     * Location of the file.
     * @parameter expression="${project.build.directory}/classes/META-INF/resources/ahtutilsCss"
     * @required
     */
    private String resourceDir;
    	
    public void execute() throws MojoExecutionException
    {
    	BasicConfigurator.configure();
    	org.apache.log4j.Logger.getRootLogger().setLevel(Level.toLevel(log));
    	
    	File fTmpDir = new File(targetDir);
    	if(!fTmpDir.exists()){fTmpDir.mkdirs();}
    	
    	File fResourceDir = new File(resourceDir);
    	if(!fResourceDir.exists()){throw new MojoExecutionException(fResourceDir.getAbsolutePath()+" does not exist");}
    	if(!fResourceDir.isDirectory()){throw new MojoExecutionException(fResourceDir.getAbsolutePath()+" is not a directory");}
    	
    	executeCssBuilder(fTmpDir,fResourceDir);
   
    	try {FileUtils.deleteDirectory(fTmpDir);}
    	catch (IOException e) {throw new MojoExecutionException(e.getMessage());}
    }
    
    private void executeCssBuilder(File fTmpDir,File fResourceDir) throws MojoExecutionException
    {
    	getLog().info("Executing CssBuilder");
    	CssGridBuilder gridFactory = new CssGridBuilder(fTmpDir,fResourceDir);
    	gridFactory.buildCss();
    	
    }
}