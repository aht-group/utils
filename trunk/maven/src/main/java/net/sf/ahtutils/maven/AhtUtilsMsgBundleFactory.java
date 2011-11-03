package net.sf.ahtutils.maven;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.msgbundle.TranslationFactory;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which compiles a set of JasperReports jrxml files to .jasper file. Creates a rtl language and a ltr language version of all reports.
 *
 * @goal msgBundle
 * 
 * @phase initialize
 */
public class AhtUtilsMsgBundleFactory extends AbstractMojo
{
	/**
     * Location of the file.
     * @parameter expression="${project.groupId}"
     * @required
     */
    private String bundlePackage;
    
    /**
     * Location of the file.
     * @parameter expression="${basedir}/target/msg.${project.artifactId}"
     * @required
     */
    private String targetDir;
    
    /**
     * Location of the file.
     * @parameter expression="${basedir}/src/main/resources/msg.${project.artifactId}"
     * @required
     */
    private String reportRoot;
	
    public void execute() throws MojoExecutionException
    {
    	BasicConfigurator.configure();
    	Logger.getRootLogger().setLevel(Level.ERROR);
    	 
    	File fTarget = createTargetDir();
    	
    	File fRoot = new File(reportRoot);
    	if(!fRoot.exists()){throw new MojoExecutionException("msg.bundle directory does not exist: "+fRoot.getAbsolutePath());}
    	
    	getLog().info("Creating MessageBundle "+bundlePackage+".msg_<lang>.txt from "+reportRoot);
    	
    	TranslationFactory tFactory = new TranslationFactory();
		tFactory.setOutEncoding("UTF-8");
		try
		{
			tFactory.rekursiveDirectory(fRoot.getAbsolutePath());
			tFactory.writeMessageResourceBundles("msg",bundlePackage,fTarget.getAbsolutePath());
			for(String s : tFactory.getStats())
			{
				getLog().info(s);
			}
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (AhtUtilsNotFoundException e) {e.printStackTrace();}
    }
    
    private File createTargetDir() throws MojoExecutionException
    {
    	File fTarget = new File(targetDir);
    	
    	if(!fTarget.exists())
    	{
    		if(!fTarget.getParentFile().exists()){throw new MojoExecutionException("Parent directory (of target) does not exist: "+fTarget.getParentFile().getAbsolutePath());}
    		else if(!fTarget.getParentFile().isDirectory()){throw new MojoExecutionException("Parent (of target) is not a directory: "+fTarget.getParentFile().getAbsolutePath());}
    		else
    		{
    			fTarget.mkdir();
    		}
    	}
    	else if(!fTarget.isDirectory())
    	{
    		throw new MojoExecutionException("Target is not a directory: "+fTarget.getAbsolutePath());
    	}
    	
    	return fTarget;
    }
}