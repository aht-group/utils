package net.sf.ahtutils.maven;

import java.io.File;
import java.io.IOException;

import net.sf.ahtutils.controller.factory.html.CssPrototypeBuilder;
import net.sf.exlp.util.io.RelativePathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal create Anootations for ViewIdentifier
 *
 * @goal createCssPrototype
 */
public class CssPrototypeGoal extends AbstractMojo
{
    /**
     * Logger Level
     * @parameter expression="WARN"
     * @required
     */
    private String log;
    
    /**
     * Location of the file.
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private String buildDir;
    
    /**
     * Location of the file.
     * @parameter expression="${project.build.directory}/aupCSS.${project.artifactId}"
     * @required
     */
    private String targetDir;
	
	/**
     * Location of the file.
     * @parameter expression="${project.build.directory}/${project.build.finalName}/resources/css"
     * @required
     */
    private String resourceDir;
    
	/**
     * Location of the file.
     * @parameter expression="aupCustom.css"
     * @required
     */
    private String cssName;
    
    /**
     * Location of the file.
     * @parameter expression="#F00014"
     * @required
     */
    private String colorDark;
    
    /**
     * Location of the file.
     * @parameter expression="#B90000"
     * @required
     */
    private String colorLight;
    	
    public void execute() throws MojoExecutionException
    {    	
    	BasicConfigurator.configure();
    	org.apache.log4j.Logger.getRootLogger().setLevel(Level.toLevel(log));
    	
    	File fTmpDir = new File(targetDir);
    	if(!fTmpDir.exists()){fTmpDir.mkdirs();}
    	
    	File fResourceDir = new File(resourceDir);
    	File fBuildDir = new File(buildDir);
    	
    	if(!fBuildDir.exists()){throw new MojoExecutionException(fBuildDir.getAbsolutePath()+" does not exist");}
    	if(!fResourceDir.exists())
    	{
    		fResourceDir.mkdirs();
    	}
    	
    	if(!fResourceDir.exists()){throw new MojoExecutionException(fResourceDir.getAbsolutePath()+" does not exist");}
    	if(!fResourceDir.isDirectory()){throw new MojoExecutionException(fResourceDir.getAbsolutePath()+" is not a directory");}
    	
    	executeCssBuilder(fTmpDir,fResourceDir);
   
    	RelativePathFactory rpf = new RelativePathFactory(fBuildDir);
    	
    	getLog().info("Executing CssBuilder (Prototype): "+rpf.relativate(fResourceDir)+"/"+cssName);
    	try {FileUtils.deleteDirectory(fTmpDir);}
    	catch (IOException e) {throw new MojoExecutionException(e.getMessage());}
    }
    
    private void executeCssBuilder(File fTmpDir, File fResourceDir) throws MojoExecutionException
    {
    	CssPrototypeBuilder cssBuilder = new CssPrototypeBuilder(fTmpDir,fResourceDir,cssName);
    	cssBuilder.buildCss(colorLight,colorDark);
    }
}