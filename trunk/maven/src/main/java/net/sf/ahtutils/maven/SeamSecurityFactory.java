package net.sf.ahtutils.maven;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.controller.factory.java.security.JavaSecurityViewIdentifierFactory;
import net.sf.ahtutils.controller.factory.java.security.JavaSecurityViewRestrictorFactory;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.dir.DirChecker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal create Anootations for ViewIdentifier
 *
 * @goal createSeamSecurity
 */
public class SeamSecurityFactory extends AbstractMojo
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
     * @parameter expression="${project.basedir}/src/main/java"
     * @required
     */
    private String srcDir;
    
    /**
     * Location of the file.
     * @parameter expression="${project.groupId}.${project.artifactId}"
     * @required
     */
    private String packageBase;
    
    /**
     * Location of the file.
     * @parameter expression="null"
     * @required
     */
    private String viewsXml;
    
    /**
     * Location of the file.
     * @parameter expression="null"
     * @required
     */
    private String classPrefix;
    
    /**
     * Location of the file.
     * @parameter expression="null"
     * @required
     */
    private String classAbstractRestrictor;
    
    /**
     * Location of the file.
     * @parameter expression="null"
     * @required
     */
    private String classRestrictor;
	
    public void execute() throws MojoExecutionException
    {
    	BasicConfigurator.configure();
    	org.apache.log4j.Logger.getRootLogger().setLevel(Level.toLevel(log));
    	
    	if(viewsXml.equals("null")){throw new MojoExecutionException("configuration parameter 'viewsXml' not defined!");}
    	if(classPrefix.equals("null")){throw new MojoExecutionException("configuration parameter 'classPrefix' not defined!");}
    	
    	File fTmpDir = new File(targetDir);
    	try
    	{
    		DirChecker.checkParentIsAnDir(new File(targetDir));
    		if(!fTmpDir.exists()){fTmpDir.mkdir();}
    	}
    	catch (ExlpConfigurationException e) {throw new MojoExecutionException(e.getMessage());}
    	
    	executeIdentifierFactory(fTmpDir);
    	executeRestrictorFactory(fTmpDir);
    	
    	try {FileUtils.deleteDirectory(fTmpDir);}
    	catch (IOException e) {throw new MojoExecutionException(e.getMessage());}
    }
    
    private void executeIdentifierFactory(File fTmpDir) throws MojoExecutionException
    {
    	String[] pack = packageBase.split("\\.");
    	StringBuffer sb = new StringBuffer();
    	sb.append(srcDir).append(SystemUtils.FILE_SEPARATOR);
    	for(String s : pack)
    	{
    		sb.append(s).append(SystemUtils.FILE_SEPARATOR);
    	}
    	File fPackage = new File(sb.substring(0,sb.length()-1));
    	
    	getLog().info("packageBase " +packageBase+" ("+fPackage.getAbsolutePath()+")");
    	getLog().info("viewsXml " +viewsXml);
    	    	
    	JavaSecurityViewIdentifierFactory idFactory = new JavaSecurityViewIdentifierFactory(fTmpDir,fPackage,packageBase,classPrefix);
    	try
    	{
			idFactory.processViews(viewsXml);
		}
    	catch (FileNotFoundException e) {throw new MojoExecutionException(e.getMessage());}
    	catch (AhtUtilsConfigurationException e) {throw new MojoExecutionException(e.getMessage());}
    }
    
    private void executeRestrictorFactory(File fTmpDir) throws MojoExecutionException
    {
    	getLog().info("classAbstractRestrictor " +classAbstractRestrictor);
    	getLog().info("classRestrictor " +classRestrictor);
    	
    	String[] pack = classRestrictor.split("\\.");
    	StringBuffer sb = new StringBuffer();
    	sb.append(srcDir).append(SystemUtils.FILE_SEPARATOR);
    	for(String s : pack)
    	{
    		sb.append(s).append(SystemUtils.FILE_SEPARATOR);
    	}
    	File fRestrictorClass = new File(sb.substring(0,sb.length()-1)+".java");
    	getLog().info("fRestrictorClass " +fRestrictorClass.getAbsolutePath());
    	
    	try
    	{
    		JavaSecurityViewRestrictorFactory restrictorFactory = new JavaSecurityViewRestrictorFactory(fTmpDir,fRestrictorClass,classRestrictor,classAbstractRestrictor,packageBase,classPrefix);
			restrictorFactory.processViews(viewsXml);
		}
    	catch (FileNotFoundException e) {throw new MojoExecutionException(e.getMessage());}
    	catch (AhtUtilsConfigurationException e) {throw new MojoExecutionException(e.getMessage());}
    }
}