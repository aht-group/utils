package net.sf.ahtutils.maven;

import java.io.File;
import java.io.IOException;

import net.sf.ahtutils.controller.factory.js.JsFactory;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.RelativePathFactory.PathSeparator;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Goal create Anootations for ViewIdentifier
 *
 * @goal createJs
 */
public class JsBuilder extends AbstractMojo
{
	final static Logger logger = LoggerFactory.getLogger(JsBuilder.class);
	
    /**
     * Logger Level
     * @parameter expression="WARN"
     * @required
     */
    private String log;
    
	/**
     * Location of the file.
     * @parameter expression="${project.basedir}"
     * @required
     */
    private String projectBaseDir;
    
    /**
     * Location of the file.
     * @parameter expression="${project.build.directory}/classes/META-INF/resources/ahtutilsCss"
     * @required
     */
    private String jsDir;
    
    /**
     * Location of the file.
     * @parameter expression="${project.build.directory}/noName.jsf"
     * @required
     */
    private String targetFile;

    public void execute() throws MojoExecutionException
    {
    	BasicConfigurator.configure();
    	org.apache.log4j.Logger.getRootLogger().setLevel(Level.toLevel(log));
    	
    	File fProject = new File(projectBaseDir);
    	if(!fProject.exists()){throw new MojoExecutionException(fProject.getAbsolutePath()+" does not exist");}
    	if(!fProject.isDirectory()){throw new MojoExecutionException(fProject.getAbsolutePath()+" is not a directory");}
    	
    	File fJsDir = new File(fProject,jsDir);
    	if(!fJsDir.exists()){throw new MojoExecutionException(fJsDir.getAbsolutePath()+" does not exist");}
    	if(!fJsDir.isDirectory()){throw new MojoExecutionException(fJsDir.getAbsolutePath()+" is not a directory");}
    	   	
    	RelativePathFactory rpf = new RelativePathFactory(fProject,PathSeparator.CURRENT);
    	getLog().info("JS Directory: "+rpf.relativate(fJsDir));
    	
    	File fTarget = new File(targetFile);
    	if(!fTarget.getParentFile().exists()){throw new MojoExecutionException(fTarget.getParentFile().getAbsolutePath()+" does not exist");}
    	if(!fTarget.getParentFile().isDirectory()){throw new MojoExecutionException(fTarget.getParentFile().getAbsolutePath()+" is not a directory");}
    	if(fTarget.isDirectory()){throw new MojoExecutionException(fTarget.getAbsolutePath()+" is a directory");}
    	if(fTarget.exists()){fTarget.delete();}
    	
    	getLog().info("Target: "+rpf.relativate(fTarget));
    	
		try
		{
			JsFactory jsFactory = new JsFactory(fJsDir);
			jsFactory.write(fTarget);
		}
		catch (EvaluatorException e) {throw new MojoExecutionException(e.getMessage());}
		catch (IOException e) {throw new MojoExecutionException(e.getMessage());}
    }
}